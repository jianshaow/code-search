package de.jdemo.swingui.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Markus Gebhard
 */
public class AllTests {

  /** @deprecated */
  public static Test suite() {
    TestSuite suite = new TestSuite("Test for de.jdemo.swingui.test"); //$NON-NLS-1$
    //$JUnit-BEGIN$
    suite.addTestSuite(DemoRunnerTest.class);
    //$JUnit-END$
    return suite;
  }
}
