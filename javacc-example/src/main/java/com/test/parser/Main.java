package com.test.parser;

import java.io.StringReader;

public class Main {
	private static final String EXPR = "3+5-(4*6)+24/(5+7)*2";

	public static void main(String[] args) throws ParseException {
		final Calculator calculator = new Calculator(new StringReader(EXPR));
		System.out.println("JavaCC Parsing...");
		System.out.println(EXPR + "=" + calculator.calculate());
	}
}
