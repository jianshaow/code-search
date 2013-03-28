package com.jmsgrp.sample;

import java.io.Serializable;
import java.util.Date;

public class JavaSample extends Test<Object> implements TestI, Serializable {

	private static final long serialVersionUID = 3743740988419440705L;

	static {
		System.out.println();
	}

	enum EnumTest {
		X, Y, Z
	}

	private int prvField;
	String pkgField;
	public Long pubField;

	public JavaSample() throws Exception {
		new A();
	}

	private void prvMethod() {

	}

	int pkgMethod(String param1, final int param2) {
		this.prvMethod();
		return 0;
	}

	public Date pubMethod() throws Exception {
		return new Date();
	}

	public int getPrvField() {
		return prvField;
	}

	public void setPrvField(int prvField) {
		this.prvField = prvField;
	}

	class InnerClass<T> {
		private T tField;

		public T getTField() {
			return tField;
		}

		public void setTField(T field) {
			tField = field;
		}
	}
}