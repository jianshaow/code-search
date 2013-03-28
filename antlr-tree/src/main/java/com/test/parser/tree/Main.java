package com.test.parser.tree;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

public class Main {
	private static final String EXPR = "3+5-(4*6)+24/(5+7)*2";

	public static void main(String[] args) throws RecognitionException {
		ANTLRStringStream input = new ANTLRStringStream(EXPR);
		CalculatorLexer lexer = new CalculatorLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CalculatorParser parser = new CalculatorParser(tokens);
		CommonTree t = (CommonTree) parser.calc().getTree();
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
		CalculatorTreeWalker walker = new CalculatorTreeWalker(nodes);
		System.out.println("ANTLR Tree Parsing...");
		System.out.println(EXPR + "=" + walker.expr());
	}
}
