package parser;
import model.Symbol;
import model.Type;

public class Reader {
	private int pos, len;
	private final char[] expression;

	public Reader(String expression) {
		pos = 0;
		len = expression.length();
		this.expression = expression.toCharArray();
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
			StringBuffer sb = new StringBuffer(str(c));
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

		case ' ': break;

		default:
			if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')) {
				StringBuffer sb2 = new StringBuffer(str(c));
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

		case ' ': break;

		default:
			symbol = new Symbol(str(c), Type.MATHFUNCTION);
			break;
		}

		return symbol;
	}

	public int getPosition() {
		return pos;
	}

	private static String str(char c) {
		return Character.toString(c);
	}

}