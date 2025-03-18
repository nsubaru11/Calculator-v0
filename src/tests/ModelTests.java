package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import model.BigFraction;
import model.Symbol;
import model.Term;
import model.Type;

public class ModelTests {

	@Nested
	public static class BigFractionTests {

		@Test
		void testValueOf() {
			assertEquals(new BigFraction(2, 3), BigFraction.valueOf(2, 3));
			assertEquals(new BigFraction(2), BigFraction.valueOf(2));
			assertEquals(new BigFraction(BigInteger.TWO, BigInteger.ONE),
					BigFraction.valueOf(BigInteger.TWO, BigInteger.ONE));
			assertEquals(new BigFraction(BigInteger.TWO), BigFraction.valueOf(BigInteger.TWO));
			assertEquals(new BigFraction(2.5), BigFraction.valueOf(2.5));
			assertEquals(new BigFraction(2.5, 3.5), BigFraction.valueOf(2.5, 3.5));
			assertEquals(new BigFraction(BigDecimal.TEN, BigDecimal.ONE),
					BigFraction.valueOf(BigDecimal.TEN, BigDecimal.ONE));
			assertEquals(new BigFraction(BigDecimal.TEN), BigFraction.valueOf(BigDecimal.TEN));
			assertEquals(new BigFraction(BigFraction.ONEHALF), BigFraction.ONEHALF);
			assertThrows(ArithmeticException.class, () -> new BigFraction(1, 0));
			assertThrows(ArithmeticException.class, () -> new BigFraction(1.0, 0.0));
		}

		@Test
		void testGeter() {
			BigFraction a = new BigFraction(10);
			assertEquals(BigInteger.TEN, a.numerator());
			assertEquals(BigInteger.ONE, a.denominator());
			a = a.negate();
			assertEquals(BigInteger.TEN.negate(), a.numerator());
			assertEquals(BigInteger.ONE, a.denominator());
		}

		@Test
		void testAddition() {
			BigFraction a = new BigFraction(1, 2);
			assertEquals(new BigFraction(3, 2), a.add(1));
			assertEquals(new BigFraction(7, 2), a.add(BigInteger.valueOf(3)));
			assertEquals(new BigFraction(5, 4), a.add(0.75));
			assertEquals(new BigFraction(5, 4), a.add(BigDecimal.valueOf(0.75)));
			assertEquals(new BigFraction(1, 8), a.add(new BigFraction(-3, 8)));
		}

		@Test
		void testSubtraction() {
			BigFraction a = new BigFraction(1, 2);
			assertEquals(new BigFraction(-1, 2), a.sub(1));
			assertEquals(new BigFraction(-5, 2), a.sub(BigInteger.valueOf(3)));
			assertEquals(new BigFraction(-1, 4), a.sub(0.75));
			assertEquals(new BigFraction(-1, 4), a.sub(BigDecimal.valueOf(0.75)));
			assertEquals(new BigFraction(7, 8), a.sub(new BigFraction(-3, 8)));
		}

		@Test
		void testMultiplication() {
			BigFraction a = new BigFraction(1, 2);
			assertEquals(new BigFraction(1, 2), a.mul(1));
			assertEquals(new BigFraction(3, 2), a.mul(BigInteger.valueOf(3)));
			assertEquals(new BigFraction(3, 8), a.mul(0.75));
			assertEquals(new BigFraction(3, 8), a.mul(BigDecimal.valueOf(0.75)));
			assertEquals(new BigFraction(-3, 16), a.mul(new BigFraction(-3, 8)));
		}

		@Test
		void testDivision() {
			BigFraction a = new BigFraction(1, 2);
			assertEquals(new BigFraction(1, 2), a.div(1));
			assertEquals(new BigFraction(1, 6), a.div(BigInteger.valueOf(3)));
			assertEquals(new BigFraction(2, 3), a.div(0.75));
			assertEquals(new BigFraction(2, 3), a.div(BigDecimal.valueOf(0.75)));
			assertEquals(new BigFraction(-4, 3), a.div(new BigFraction(-3, 8)));
		}

		@Test
		void testPower() {
			BigFraction a = new BigFraction(2, 3);
			assertEquals(new BigFraction(65536, 43046721), a.pow(16));
			assertEquals(new BigFraction(1), a.pow(0));
			assertEquals(new BigFraction(43046721, 65536), a.pow(-16));
		}

		@Test
		void testCompareTo() {
			BigFraction a = new BigFraction(3, 4);
			BigFraction b = new BigFraction(6, 8);
			BigFraction c = new BigFraction(3, 2);
			assertEquals(-1, a.compareTo(c));
			assertEquals(0, a.compareTo(b));
			assertEquals(1, c.compareTo(a));
		}

		@Test
		void testEquals() {
			BigFraction a = new BigFraction(3, 4);
			BigFraction b = new BigFraction(6, 8);
			assertEquals(true, a.equals(b));
			assertEquals(true, a.equals(a));
//			assertEquals(false, a.equals(0));
			assertEquals(false, a.equals(null));
		}

		@Test
		void testHashCode() {
			BigFraction a = new BigFraction(3, 4);
			BigFraction b = new BigFraction(6, 8);
			BigFraction c = new BigFraction(0.75);
			assertEquals(a.hashCode(), b.hashCode());
			assertEquals(a.hashCode(), c.hashCode());
		}

		@Test
		void testCompares() {
			BigFraction a = new BigFraction(0);
			assertEquals(true, a.isZero());
			assertEquals(false, a.isOne());
			a = a.pow(0);
			assertEquals(false, a.isZero());
			assertEquals(true, a.isOne());
			assertEquals(true, a.isInteger());
			assertEquals(false, a.isProper());
			assertEquals(true, a.isImproper());
			a = a.div(3);
			assertEquals(false, a.isInteger());
			assertEquals(true, a.isProper());
			assertEquals(false, a.isImproper());
		}

		@Test
		void testConversion() {
			BigFraction a = new BigFraction(2, 3);
			BigFraction b = new BigFraction(3, 2);
			assertEquals(a.inverse(), b);
			assertEquals(a.negate(), a.mul(-1));
			assertEquals(b.fractionPart(), BigFraction.ONEHALF);
			assertEquals(b.integerPart(), BigInteger.ONE);
		}

		@Test
		void testFloor() {
			BigFraction a = new BigFraction(4, 3);
			assertEquals(BigInteger.valueOf(1), a.floor());
			assertEquals(BigInteger.valueOf(-2), a.negate().floor());
			a = new BigFraction(1);
			assertEquals(BigInteger.valueOf(1), a.floor());
			assertEquals(BigInteger.valueOf(-1), a.negate().floor());
			a = new BigFraction(0);
			assertEquals(BigInteger.valueOf(0), a.floor());
			assertEquals(BigInteger.valueOf(0), a.negate().floor());
		}

		@Test
		void testCeil() {
			BigFraction a = new BigFraction(4, 3);
			assertEquals(BigInteger.valueOf(2), a.ceil());
			assertEquals(BigInteger.valueOf(-1), a.negate().ceil());
			a = new BigFraction(1);
			assertEquals(BigInteger.valueOf(1), a.ceil());
			assertEquals(BigInteger.valueOf(-1), a.negate().ceil());
			a = new BigFraction(0);
			assertEquals(BigInteger.valueOf(0), a.ceil());
			assertEquals(BigInteger.valueOf(0), a.negate().ceil());
		}

		@Test
		void testRound() {
			BigFraction a = new BigFraction(5, 3);
			assertEquals(BigInteger.TWO, a.round());
		}

		@Test
		void testGetDecimal() {
			BigFraction a = new BigFraction(5, 3);
			assertEquals(BigDecimal.valueOf(1.667), a.getDecimal(3));
			assertEquals(BigDecimal.valueOf(-1.667), a.negate().getDecimal(3));
		}

		@Test
		void testToString() {
			BigFraction a = new BigFraction(6, 8);
			assertEquals("3/4", a.toString());
			assertEquals("-3/4", a.negate().toString());
			a = a.mul(8).div(3);
			assertEquals("2", a.toString());
		}

		@Test
		void testToMixedString() {
			BigFraction a = new BigFraction(9, 8);
			assertEquals("1 1/8", a.toMixedString());
			assertEquals("-1 1/8", a.negate().toMixedString());
			a = a.mul(8).div(3);
			assertEquals("3", a.toMixedString());
		}

		@Test
		void testToDecimalString() {
			BigFraction a = new BigFraction(2, 3);
			assertEquals("0.6667", a.toDecimalString(4));
		}

		@Test
		void testToPercent() {
			BigFraction a = new BigFraction(0.72);
			assertEquals("72.0%", a.toPercent(1));
		}

		@Test
		void testMin() {
			BigFraction a = new BigFraction(3, 4);
			BigFraction b = new BigFraction(5, 7);
			assertEquals(b, a.min(b));
			assertEquals(a.negate(), a.negate().min(b.negate()));
		}

		@Test
		void testMax() {
			BigFraction a = new BigFraction(3, 4);
			BigFraction b = new BigFraction(5, 7);
			assertEquals(a, a.max(b));
			assertEquals(b.negate(), a.negate().max(b.negate()));
		}

		@Test
		void testParseBigFraction() {
			assertEquals(new BigFraction(2), BigFraction.parseBigFraction("2"));
			assertEquals(new BigFraction(2, 3), BigFraction.parseBigFraction("2 / 3"));
			assertEquals(new BigFraction(2.5, 3.5), BigFraction.parseBigFraction("2.5 / 3.5"));
			assertEquals(new BigFraction(2.5), BigFraction.parseBigFraction("2.5"));
			assertThrows(ArithmeticException.class, () -> BigFraction.parseBigFraction("1/0"));
			assertThrows(IllegalArgumentException.class, () -> BigFraction.parseBigFraction(""));
			assertThrows(IllegalArgumentException.class, () -> BigFraction.parseBigFraction("/"));
			assertThrows(IllegalArgumentException.class, () -> BigFraction.parseBigFraction("/2"));
			assertThrows(IllegalArgumentException.class, () -> BigFraction.parseBigFraction("2/"));
			assertThrows(IllegalArgumentException.class, () -> BigFraction.parseBigFraction(null));
		}

		@Test
		void testAbs() {
			BigFraction a = new BigFraction(10, -9);
			assertEquals(new BigFraction(10, 9), a.abs());
			assertEquals(new BigFraction(0), a.sub(a).abs());
		}

		@Test
		void testCopy() {
			BigFraction a = new BigFraction(10, -9);
			assertEquals(new BigFraction(-10, 9), a.copy());
		}

		@Test
		void testIntValue() {
			BigFraction a = new BigFraction(3, 2);
			assertEquals(1, a.intValue());
		}

		@Test
		void testLongValue() {
			BigFraction a = new BigFraction(3, 2);
			assertEquals(1, a.longValue());
		}

		@Test
		void testFloatValue() {
			BigFraction a = new BigFraction(3, 2);
			assertEquals(3 / 2f, a.floatValue());
		}

		@Test
		void testDoubleValue() {
			BigFraction a = new BigFraction(3, 2);
			assertEquals(3 / 2d, a.doubleValue());
		}

	}

	@Nested
	public static class SymbolTests {

		@Test
		void testSymbolCreation() {
			Symbol operator = new Symbol("+", Type.OPERATOR);
			assertEquals(true, operator.equals(operator));
			assertEquals(false, operator.equals(null));
			assertEquals(true, operator.equals(new Symbol("+", Type.OPERATOR)));
			assertEquals(false, operator.equals(new Symbol("-", Type.OPERATOR)));
			assertEquals(false, operator.equals(new Symbol("1", Type.NUMBER)));
			assertEquals(false, operator.equals(new Symbol("+", Type.NUMBER)));
		}

		@Test
		void testHashCode() {
			Symbol a = new Symbol("1", Type.NUMBER);
			Symbol b = new Symbol("1", Type.NUMBER);
			Symbol c = new Symbol("2", Type.NUMBER);
			assertEquals(a.hashCode(), b.hashCode());
			assertNotEquals(a.hashCode(), c.hashCode());
		}

		@Test
		void testToString() {
			Symbol a = new Symbol("1", Type.NUMBER);
			assertEquals("{Symbol: 1, Type: NUMBER}", a.toString());
		}

	}

	@Nested
	public static class TermTests {

		@Test
		void testAddition() {
			Term left = new Term(new Symbol("1/3", Type.NUMBER), null, null);
			Term right = new Term(new Symbol("1/6", Type.NUMBER), null, null);
			Term expr = new Term(new Symbol("+", Type.OPERATOR), left, right);
			assertEquals(new BigFraction(1, 2), expr.calc());
		}

		@Test
		void testMultiplication() {
			Term left = new Term(new Symbol("2/3", Type.NUMBER), null, null);
			Term right = new Term(new Symbol("3/4", Type.NUMBER), null, null);
			Term expr = new Term(new Symbol("*", Type.OPERATOR), left, right);
			assertEquals(new BigFraction(1, 2), expr.calc());
		}

		@Test
		void testDivision() {
			Term left = new Term(new Symbol("3/4", Type.NUMBER), null, null);
			Term right = new Term(new Symbol("3/2", Type.NUMBER), null, null);
			Term expr = new Term(new Symbol("/", Type.OPERATOR), left, right);
			assertEquals(new BigFraction(1, 2), expr.calc());
		}

		@Test
		void testCalc() {
			Term left = new Term(new Symbol("5", Type.NUMBER), null, null);
			Term right = new Term(new Symbol("3", Type.NUMBER), null, null);
			Term expr = new Term(new Symbol("/", Type.OPERATOR), left, right);
			assertEquals(new BigFraction(5, 3), expr.calc());
			assertEquals(BigDecimal.valueOf(1.67), expr.calc(2));
			assertEquals(BigInteger.valueOf(1), expr.calcInteger());
		}

		@Test
		void testHashCode() {
			Term left = new Term(new Symbol("2", Type.NUMBER), null, null);
			Term right = new Term(new Symbol("3", Type.NUMBER), null, null);
			Term expr = new Term(new Symbol("+", Type.OPERATOR), left, right);
			Term left2 = new Term(new Symbol("2", Type.NUMBER), null, null);
			Term right2 = new Term(new Symbol("3", Type.NUMBER), null, null);
			Term expr2 = new Term(new Symbol("+", Type.OPERATOR), left2, right2);
			assertEquals(expr.hashCode(), expr2.hashCode());
		}

		@Test
		void testToString() {
			Term left = new Term(new Symbol("2", Type.NUMBER), null, null);
			Term right = new Term(new Symbol("3", Type.NUMBER), null, null);
			Term expr = new Term(new Symbol("+", Type.OPERATOR), left, right);
			assertEquals("add(2, 3)", expr.toString());
			expr = new Term(new Symbol("-", Type.OPERATOR), left, right);
			assertEquals("sub(2, 3)", expr.toString());
			expr = new Term(new Symbol("*", Type.OPERATOR), left, right);
			assertEquals("mul(2, 3)", expr.toString());
			expr = new Term(new Symbol("/", Type.OPERATOR), left, right);
			assertEquals("div(2, 3)", expr.toString());
		}
	}

	@Nested
	public static class TypeTests {

		@Test
		void testTypeEnum() {
			assertEquals(Type.NUMBER, Type.valueOf("NUMBER"));
			assertEquals(Type.OPERATOR, Type.valueOf("OPERATOR"));
			assertEquals(Type.MATHFUNCTION, Type.valueOf("MATHFUNCTION"));
			assertEquals(Type.PARENTHESIS, Type.valueOf("PARENTHESIS"));
		}
	}
}
