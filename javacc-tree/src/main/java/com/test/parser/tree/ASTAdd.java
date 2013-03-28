package com.test.parser.tree;

public class ASTAdd extends SimpleNode implements Operator {
	public ASTAdd(int id) {
		super(id);
	}

	public ASTAdd(Calculator p, int id) {
		super(p, id);
	}

	public int calculate(int left, int right) {
		return left + right;
	}
}
