package model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class Term {
	public Symbol label;
	public Term left, right;

	/**
	 * コンストラクタ。 指定されたラベル（演算子または数値）、左部分木、右部分木でノードを構築する。
	 *
	 * @param label ノードのラベル（Symbol 型: 演算子または数値）
	 * @param left  左部分木（null の場合もあり）
	 * @param right 右部分木（null の場合もあり）
	 */
	public Term(Symbol label, Term left, Term right) {
		this.label = label;
		this.left = left;
		this.right = right;
	}

	public BigFraction calc() {
		return calcFraction();
	}

	public BigDecimal calc(int n) {
		return calcFraction().getDecimal(n);
	}

	/**
	 * 整数計算を再帰的に行うメソッド。 現在のノードが演算子の場合は、左部分木と右部分木を再帰的に評価して計算する。
	 * 現在のノードが数値の場合は、その値を整数として返す。
	 *
	 * @return このノードをルートとする部分木の計算結果（整数）
	 */
	public BigInteger calcInteger() {
		return calcFraction().integerPart();
	}

	/**
	 * 分数計算を再帰的に行うメソッド。 現在のノードが演算子の場合は、左部分木と右部分木を再帰的に評価して分数計算を行う。
	 * 現在のノードが数値の場合は、それを基に Fraction オブジェクトを生成して返す。
	 *
	 * @return このノードをルートとする部分木の計算結果（Fraction オブジェクト）
	 */
	private BigFraction calcFraction() {
		return switch (label.symbol()) {
			case "+" -> left.calcFraction().add(right.calcFraction());
			case "-" -> left.calcFraction().sub(right.calcFraction());
			case "*" -> left.calcFraction().mul(right.calcFraction());
			case "/" -> left.calcFraction().div(right.calcFraction());
			default -> BigFraction.parseBigFraction(label.symbol());
		};
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Term other)
			return Objects.equals(label, other.label) && Objects.equals(left, other.left)
					&& Objects.equals(right, other.right);
		return false;
	}

	public int hashCode() {
		return Objects.hash(label, left, right);
	}

	/**
	 * このノードのラベルを文字列として返す。 ポーランド記法を用いて木を表します。
	 *
	 * @return ラベルの文字列表現
	 */
	public String toString() {
		if (label.type() == Type.OPERATOR) {
			return switch (label.symbol()) {
				case "+" -> "add(" + left.toString() + ", " + right.toString() + ")";
				case "-" -> "sub(" + left.toString() + ", " + right.toString() + ")";
				case "*" -> "mul(" + left.toString() + ", " + right.toString() + ")";
				case "/" -> "div(" + left.toString() + ", " + right.toString() + ")";
				default -> throw new IllegalStateException("Unexpected value: " + label.symbol());
			};
		}
		return label.symbol();
	}
}
