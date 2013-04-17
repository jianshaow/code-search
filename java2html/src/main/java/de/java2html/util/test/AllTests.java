package de.java2html.util.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Markus Gebhard
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for de.java2html.util.test"); //$NON-NLS-1$
    //$JUnit-BEGIN$
    suite.addTestSuite(LinkedPropertiesTest.class);
    suite.addTestSuite(RGBTest.class);
    suite.addTestSuite(IoUtilitiesTest.class);
    //$JUnit-END$
    return suite;
  }
}