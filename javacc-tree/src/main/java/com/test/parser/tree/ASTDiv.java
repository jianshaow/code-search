package com.test.parser.tree;

public class ASTDiv extends SimpleNode implements Operator {
	public ASTDiv(int id) {
		super(id);
	}

	public ASTDiv(Calculator p, int id) {
		super(p, id);
	}

	public int calculate(int left, int right) {
		return left / right;
	}
}
