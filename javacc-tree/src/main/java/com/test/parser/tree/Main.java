package com.test.parser.tree;

import java.io.StringReader;

public class Main {
	private static final String EXPR = "3+5-(4*6)+24/(5+7)*2";

	public static void main(String[] args) {
		final Calculator t = new Calculator(new StringReader(EXPR));
		try {
			System.out.println("JavaCC Tree Parsing...");
			ASTStart n = t.start();
			System.out.println(EXPR + "=" + n.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
