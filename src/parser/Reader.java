package parser;

import model.Symbol;
import model.Type;

public class Reader {
	private final char[] expression;
	private final int len;
	private int pos;

	public Reader(String expression) {
		pos = 0;
		len = expression.length();
		this.expression = expression.toCharArray();
	}

	private static String str(char c) {
		return Character.toString(c);
	}

	public Symbol read() {
		if (pos >= len) return null;
		// 空白スペースの読み込み
		char c = expression[pos++];
		while (pos < len && c == ' ') {
			c = expression[pos++];
		}
		// 次のSymbolの読み込み
		Symbol symbol = null;
		switch (c) {
			case '-', '+', '*', '/':
				symbol = new Symbol(str(c), Type.OPERATOR);
				break;

			case '(', ')':
				symbol = new Symbol(str(c), Type.PARENTHESIS);
				break;

			case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
				StringBuilder sb = new StringBuilder(str(c));
				while (pos < len) {
					c = expression[pos];
					if (Character.isDigit(c) || c == '.') {
						sb.append(c);
						pos++;
					} else {
						break;
					}
				}
				symbol = new Symbol(sb.toString(), Type.NUMBER);
				break;

			case ' ':
				break;

			default:
				if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')) {
					StringBuilder sb2 = new StringBuilder(str(c));
					while (pos < len) {
						c = expression[pos];
						if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')) {
							sb2.append(c);
							pos++;
						} else {
							break;
						}
					}
					symbol = new Symbol(sb2.toString(), Type.MATHFUNCTION);
				}
				break;
		}
		return symbol;
	}

	public Symbol peek() {
		if (pos >= len) return null;

		int pos2 = pos;
		char c = expression[pos2++];
		while (pos2 < len && c == ' ') {
			c = expression[pos2++];
		}

		Symbol symbol = null;
		switch (c) {
			case '-', '+', '*', '/':
				symbol = new Symbol(str(c), Type.OPERATOR);
				break;

			case '(', ')':
				symbol = new Symbol(str(c), Type.PARENTHESIS);
				break;

			case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
				symbol = new Symbol(str(c), Type.NUMBER);
				break;

			case ' ':
				break;

			default:
				symbol = new Symbol(str(c), Type.MATHFUNCTION);
				break;
		}

		return symbol;
	}

	public int getPosition() {
		return pos;
	}

}