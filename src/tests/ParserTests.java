package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import model.Symbol;
import model.Type;
import parser.Parser;
import parser.Reader;

public class ParserTests {

	@Nested
	public static class BigFractionTests {
		@Test
		void testDoParse() {

			Parser.doParse("1");

			Parser.doParse("1 + 2 + 3");
			Parser.doParse("1 - 2 - 3");
			Parser.doParse("1 - -2 - -3");
			Parser.doParse("1 + -2 + -3");

			Parser.doParse("1 * 2 * 3");
			Parser.doParse("1 * -2 * -3");
			Parser.doParse("1 / 2 / 3");
			Parser.doParse("1 / -2 / -3");

			Parser.doParse("(1 + (2 + 3))");
			Parser.doParse("1 - (2 - 3)");
			Parser.doParse("1 - ((-2) - (-3))");
			Parser.doParse("1 + (-2) + (-3)");

			Parser.doParse("1 * (2 * 3)");
			Parser.doParse("1 * -(2 * -3)");
			Parser.doParse("(1 / 2) / 3");
			Parser.doParse("1 / -(2 / -3)");

		}
	}

	@Nested
	public static class testTerm {

		@Test
		void testCalc() {
			assertEquals(Parser.doParse("1 + 2 + 3").calcInteger(), BigInteger.valueOf(6)); // 通常足し算
			assertEquals(Parser.doParse("1 - 2 - 3").calcInteger(), BigInteger.valueOf(-4)); // 通常引き算
			assertEquals(Parser.doParse("1 - -2 - -3").calcInteger(), BigInteger.valueOf(6)); // 負の値の引き算
			assertEquals(Parser.doParse("1 + -2 + -3").calcInteger(), BigInteger.valueOf(-4)); // 負の値の足し算

			assertEquals(Parser.doParse("1 * 2 * 3").calcInteger(), BigInteger.valueOf(6)); // 通常掛け算
			assertEquals(Parser.doParse("1 * -2 * -3").calcInteger(), BigInteger.valueOf(6)); // 負の値の掛け算
			assertEquals(Parser.doParse("12 / 6 / 2").calcInteger(), BigInteger.valueOf(1)); // 通常割り算
			assertEquals(Parser.doParse("-12 / -6 / -2").calcInteger(), BigInteger.valueOf(-1)); // 負の値の割り算

			assertEquals(Parser.doParse("(1 + (2 + 3))").calcInteger(), BigInteger.valueOf(6)); // 括弧付き足し算
			assertEquals(Parser.doParse("1 - (2 - 3)").calcInteger(), BigInteger.valueOf(2)); // 括弧付き引き算
			assertEquals(Parser.doParse("1 - ((-2) - (-3))").calcInteger(), BigInteger.valueOf(0)); // 括弧付き引き算(負の値)
			assertEquals(Parser.doParse("1 + (-2) + (-3)").calcInteger(), BigInteger.valueOf(-4)); // 括弧付き負の値の足し算

			assertEquals(Parser.doParse("1 * (2 * 3)").calcInteger(), BigInteger.valueOf(6)); // 括弧付き掛け算
			assertEquals(Parser.doParse("1 * -(2 * -3)").calcInteger(), BigInteger.valueOf(6)); // 負の括弧付き掛け算
			assertEquals(Parser.doParse("12 / (6 / 2)").calcInteger(), BigInteger.valueOf(4)); // 括弧付き割り算
			assertEquals(Parser.doParse("-12 / -(6 / -2)").calcInteger(), BigInteger.valueOf(-4)); // 括弧付き負の値の割り算

		}

	}

	@Nested
	public static class testReader {

		@Test
		void test() {
			Reader reader = new Reader("-sin(1 + 2) * 3");
			assertEquals(new Symbol("-", Type.OPERATOR), reader.peek());
			assertEquals(new Symbol("-", Type.OPERATOR), reader.read());

			assertEquals(new Symbol("s", Type.MATHFUNCTION), reader.peek());
			assertEquals(new Symbol("sin", Type.MATHFUNCTION), reader.read());

			assertEquals(new Symbol("(", Type.PARENTHESIS), reader.peek());
			assertEquals(new Symbol("(", Type.PARENTHESIS), reader.read());

			assertEquals(new Symbol("1", Type.NUMBER), reader.peek());
			assertEquals(new Symbol("1", Type.NUMBER), reader.read());

			assertEquals(new Symbol("+", Type.OPERATOR), reader.peek());
			assertEquals(new Symbol("+", Type.OPERATOR), reader.read());

			assertEquals(new Symbol("2", Type.NUMBER), reader.peek());
			assertEquals(new Symbol("2", Type.NUMBER), reader.read());

			assertEquals(new Symbol(")", Type.PARENTHESIS), reader.peek());
			assertEquals(new Symbol(")", Type.PARENTHESIS), reader.read());

			assertEquals(new Symbol("*", Type.OPERATOR), reader.peek());
			assertEquals(new Symbol("*", Type.OPERATOR), reader.read());

			assertEquals(new Symbol("3", Type.NUMBER), reader.peek());
			assertEquals(new Symbol("3", Type.NUMBER), reader.read());

			assertEquals(null, reader.read());
			assertEquals(null, reader.peek());
		}
	}
}
