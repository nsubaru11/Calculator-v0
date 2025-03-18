package main;

import java.util.Arrays;
import java.util.Scanner;

import model.Term;
import parser.Parser;

/**
 * Main.
 */
public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String[] MathFunctions = {"!", "E", "PI", "^", "cos", "ln", "log", "sin", "tan"};
		Arrays.sort(MathFunctions);
		System.out.println(Arrays.toString(MathFunctions));
//		Parser.doParse("1 + 2 + ");
//		Parser.doParse("(1+2+3");
//		Parser.doParse("1 + 2 2");
//		Parser.doParse(")1 + 2");
//		Parser.doParse("1 + + 2");
//		Parser.doParse("1 + 2 2");
		String s = sc.nextLine();
//		Term t = new Term(null, null, null);
		while (!s.equals("")) {
			try {
				Term term = Parser.doParse(s);
				System.out.println("元の数式: " + s);
				System.out.println(term + " = " + term.calc() + " (Fraction)");
				System.out.println(term + " = " + term.calc(0) + " (Integer)");
				System.out.println(term + " = " + term.calc(3) + " (Float)");
			} catch (ArithmeticException e) {
				System.out.println("Division by zero in fractions\n");
			}
			s = sc.nextLine();
		}
		sc.close();
	}

}
