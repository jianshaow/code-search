package com.test;

import java.io.File;

/**
 * 我的例子类,<p>
 * my sample.
 * 
 * @author wujianshao
 * 
 */
public class Sample {
	private String stringField;

	public int intField;

	Long longObjField;

	protected float floatField;

	public String getStringField() {
		return stringField;
	}

	public void setStringField(String stringField) {
		this.stringField = stringField;
	}

	public void voidMethod() {
		final File file = new File("");
		new java.math.BigInteger(new byte[] {});
	}

	static class InnerClass1 {

	}

	private class InnerClass2 {

	}

}

class PackageClass {

}