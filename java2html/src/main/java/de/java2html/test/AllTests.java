package de.java2html.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Markus Gebhard
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for de.java2html.test"); //$NON-NLS-1$
    //$JUnit-BEGIN$
    suite.addTestSuite(Java2HtmlTest.class);
    //$JUnit-END$
    return suite;
  }
}
