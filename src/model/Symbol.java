package model;

public record Symbol(String symbol, Type type) {

	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj instanceof Symbol other) return symbol.equals(other.symbol) && type == other.type;
		return false;
	}

	public String toString() {
		return "{Symbol: " + symbol + ", Type: " + type + "}";
	}
}
