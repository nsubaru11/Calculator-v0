package main;

import java.util.Scanner;

import model.Term;
import parser.Parser;

/**
 * Main.
 */
public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		while (!s.isEmpty()) {
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
