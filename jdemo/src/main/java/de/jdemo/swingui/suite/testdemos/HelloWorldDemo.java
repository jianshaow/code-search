package de.jdemo.swingui.suite.testdemos;

public class HelloWorldDemo extends de.jdemo.framework.DemoCase {

  /** Main method for starting the runner - can be omitted, because you can specify this demo case
   * (or a demo suite containing this demo) when starting the demo runner. */
  public static void main(String[] args) {
    de.jdemo.swingui.DemoRunner.run(HelloWorldDemo.class);
  }

  /* The most simple democase on earth */
  public void demoHelloWorld() {
    System.out.println("Hello World"); //$NON-NLS-1$
  }
}