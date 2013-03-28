package com.test.parser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class Main {
	private static final String EXPR = "3+5-(4*6)+24/(5+7)*2";

	public static void main(String[] args) throws RecognitionException {
		ANTLRStringStream input = new ANTLRStringStream(EXPR);
		CalculatorLexer lexer = new CalculatorLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CalculatorParser parser = new CalculatorParser(tokens);
		System.out.println("ANTLR Parsing...");
		System.out.println(EXPR + "=" + parser.parse());
	}
}