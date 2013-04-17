package de.jdemo.junit;

import java.lang.reflect.Method;

import org.junit.internal.runners.TestClass;
import org.junit.internal.runners.TestMethod;

import de.jdemo.annotation.Demo;

public class DemoMethod extends TestMethod {

	private final Method method;

	public DemoMethod(Method method, TestClass testClass) {
		super(method, testClass);
		this.method = method;
	}

	@Override
	public long getTimeout() {
		Demo annotation = method.getAnnotation(Demo.class);
		if (annotation == null) {
			return Demo.DEFAULT_TIMEOUT_MILLIS;
		}
		return annotation.timeout();
	}

}
