package parser;

import model.Symbol;
import model.Term;
import model.Type;

/**
 * 数式を文字列形式で入力し、抽象構文木（AST）として表現するためのパーサークラス。 入力を解析し、演算子、数値、括弧、その他の要素を木構造に分解します。
 */
public class Parser {

	private static String EXPRESSION;

	/**
	 * 与えられた数式を解析し、抽象構文木（AST）として表現する。
	 *
	 * @param expression 数式の文字列
	 * @return 数式を表現するTermオブジェクト（ASTのルート）
	 * @throws ExpressionParseException 式が不正な場合
	 */
	public static Term doParse(final String expression) {
		Reader reader = new Reader(expression);
		EXPRESSION = expression;
		return parsePolynomial(reader);
	}

	/**
	 * 数式を多項式として解析し、ASTを生成する。 各単項式を解析し、それらを演算子("+", "-")で結合する。
	 *
	 * @param reader 入力を管理するReaderオブジェクト
	 * @return 数式を表現するTermオブジェクト
	 * @throws ExpressionParseException 式が不正な場合
	 */
	private static Term parsePolynomial(Reader reader) {
		Term term = null;
		Symbol operator = null;
		while (true) {
			Term monomial = parseMonomial(reader);
			term = getNextTerm(operator, term, monomial);
			operator = reader.peek();
			if (operator == null) break;
			if (operator.symbol().equals(")")) break;
			operator = reader.read();
		}
		return term;
	}

	/**
	 * 単項式を解析し、ASTを生成する。 数値、括弧、負の数、および演算子("*", "/")を処理する。
	 *
	 * @param reader 入力を管理するReaderオブジェクト
	 * @return 単項式を表現するTermオブジェクト
	 * @throws ExpressionParseException 式が不正な場合
	 */
	private static Term parseMonomial(Reader reader) {
		Term term = null;
		Symbol operator = null;
		while (true) {
			Symbol newSymbol = reader.read();
			if (newSymbol == null)
				throw new ExpressionParseException("a number or parenthesis", EXPRESSION);
			term = switch (newSymbol.type()) {
				case NUMBER -> getNextTerm(operator, term, new Term(newSymbol, null, null));
				case OPERATOR -> getNextTerm(operator, term, parseNegatedTerm(newSymbol, reader));
				case PARENTHESIS -> getNextTerm(operator, term, perseParenthesizedTerm(newSymbol, reader));
				case MATHFUNCTION -> throw new ExpressionParseException("comming sooon");
			};

			operator = reader.peek();
			if (operator == null) break;

			String symbol = operator.symbol();
			switch (operator.type()) {
				case OPERATOR:
					if (symbol.equals("+") || symbol.equals("-")) return term;
					operator = reader.read();
					break;
				case PARENTHESIS:
					if (symbol.equals(")")) return term;
					operator = new Symbol("*", Type.OPERATOR);
					break;
				default:
					throw new ExpressionParseException("operator or parenthesis", symbol, EXPRESSION, reader.getPosition() + 1);
			}
		}
		return term;
	}

	/**
	 * 括弧を処理し、その中身を再帰的に解析する。
	 *
	 * @param newSymbol 現在のシンボル: '(' or ')'
	 * @param reader    入力を管理するReaderオブジェクト
	 * @return 括弧内の内容を表現するTermオブジェクト
	 * @throws ExpressionParseException 式が不正な場合
	 */
	private static Term perseParenthesizedTerm(Symbol newSymbol, Reader reader) {
		if (newSymbol.symbol().equals("(")) {
			Term term = parsePolynomial(reader);
			Symbol parenthesis = reader.peek();
			if (parenthesis == null) throw new ExpressionParseException("')'", EXPRESSION);
			reader.read();
			return term;
		}
		throw new ExpressionParseException("'('", newSymbol.symbol(), EXPRESSION, reader.getPosition() - 1);
	}

	/**
	 * 負の数や負の係数を処理する。 単独の負の数、または括弧付きの負数を解析する。
	 *
	 * @param newSymbol 現在のシンボル（負号）
	 * @param reader    入力を管理するReaderオブジェクト
	 * @return 負数を表現するTermオブジェクト
	 * @throws ExpressionParseException 式が不正な場合
	 */
	private static Term parseNegatedTerm(Symbol newSymbol, Reader reader) {
		if (newSymbol.symbol().equals("-")) {
			newSymbol = reader.read();
			if (newSymbol != null) {
				if (newSymbol.type() == Type.NUMBER) {
					newSymbol = new Symbol("-" + newSymbol.symbol(), Type.NUMBER);
					return new Term(newSymbol, null, null);
				} else if (newSymbol.type() == Type.PARENTHESIS) {
					Symbol mul = new Symbol("*", Type.OPERATOR);
					Term left = new Term(new Symbol("-1", Type.NUMBER), null, null);
					return new Term(mul, left, perseParenthesizedTerm(newSymbol, reader));
				} else {
					throw new ExpressionParseException("a number or parenthesis", newSymbol.symbol(), EXPRESSION,
							reader.getPosition());
				}
			} else {
				throw new ExpressionParseException("a number or parenthesis", EXPRESSION);
			}
		}
		throw new ExpressionParseException("a number or parenthesis", newSymbol.symbol(), EXPRESSION,
				reader.getPosition() - 1);
	}

	/**
	 * 与えられた演算子で2つのTermオブジェクトを結合する。 演算子が存在しない場合、新しいTermとしてそのまま返す。
	 *
	 * @param operator 現在の演算子
	 * @param prevTerm 左項のTermオブジェクト
	 * @param newTerm  右項のTermオブジェクト
	 * @return 演算結果を表現するTermオブジェクト
	 */
	private static Term getNextTerm(Symbol operator, Term prevTerm, Term newTerm) {
		if (operator != null) {
			return new Term(operator, prevTerm, newTerm);
		} else {
			return newTerm;
		}
	}

}
