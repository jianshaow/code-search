package com.test.parser;

public class IntLeaf implements TreeNode {

	private TreeNode parent;

	private int value;

	public TreeNode getLeft() {
		throw new UnsupportedOperationException("Leaf no children");
	}

	public TreeNode getParent() {
		return parent;
	}

	public TreeNode getRight() {
		throw new UnsupportedOperationException("Leaf no children");
	}

	public int getValue() {
		return value;
	}

	public void setLeft(TreeNode left) {
		throw new UnsupportedOperationException("Leaf no children");
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public void setRight(TreeNode right) {
		throw new UnsupportedOperationException("Leaf no children");
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Integer.toString(this.value);
	}
}
