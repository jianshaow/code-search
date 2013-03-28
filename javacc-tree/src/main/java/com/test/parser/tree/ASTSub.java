package com.test.parser.tree;

public class ASTSub extends SimpleNode implements Operator {
	public ASTSub(int id) {
		super(id);
	}

	public ASTSub(Calculator p, int id) {
		super(p, id);
	}

	public int calculate(int left, int right) {
		return left - right;
	}
}
