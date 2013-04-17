package de.jave.image.suite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Markus Gebhard
 */
public class AllImageProcessingTests {

  public static void main(String[] args) {
    junit.textui.TestRunner.run(AllImageProcessingTests.suite());
  }

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for de.jave.image.suite"); //$NON-NLS-1$
    suite.addTest(de.jave.image.ant.test.AllTests.suite());
    suite.addTest(de.jave.image.test.AllTests.suite());
    //$JUnit-BEGIN$

    //$JUnit-END$
    return suite;
  }
}
