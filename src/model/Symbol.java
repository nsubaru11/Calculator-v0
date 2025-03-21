package model;

public final class Symbol {
	public final String symbol;
	public final Type type;

	public Symbol(String s, Type t) {
		symbol = s;
		type = t;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Symbol other)
			return symbol.equals(other.symbol) && type == other.type;
		return false;
	}

	public int hashCode() {
		return symbol.hashCode() * 31 + type.hashCode();
	}

	public String toString() {
		return "{Symbol: " + symbol + ", Type: " + type + "}";
	}
}
