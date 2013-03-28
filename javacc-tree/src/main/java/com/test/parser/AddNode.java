package com.test.parser;

public class AddNode implements TreeNode {

	private TreeNode parent;

	private TreeNode left;

	private TreeNode right;

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public TreeNode getLeft() {
		return left;
	}

	public void setLeft(TreeNode left) {
		this.left = left;
	}

	public TreeNode getRight() {
		return right;
	}

	public void setRight(TreeNode right) {
		this.right = right;
	}

	public int getValue() {
		return this.left.getValue() + this.right.getValue();
	}

	@Override
	public String toString() {
		return "(" + "'+'," + this.left + "," + this.right + ")";
	}
}
