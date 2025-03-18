package parser;

public class ExpressionParseException extends RuntimeException {

	private static final long serialVersionUID = -2712091950976720972L;

	public ExpressionParseException(String message) {
		super(message);
	}

	public ExpressionParseException(String idealStr, String expression) {
		super(String.format("Expected %s, but found end of expression: \"%s\"", idealStr, expression));
	}

	public ExpressionParseException(String idealStr, String realStr, String expression, int errorIndex) {
		super(String.format("Expected %s, but found '%s' in expression \"%s\" at index %d", idealStr, realStr, expression, errorIndex));
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
