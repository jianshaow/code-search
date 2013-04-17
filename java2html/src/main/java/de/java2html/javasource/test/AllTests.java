package de.java2html.javasource.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Markus Gebhard
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for de.java2html.javasource.test"); //$NON-NLS-1$
    //$JUnit-BEGIN$
    suite.addTestSuite(JavaSourceIteratorTest.class);
    suite.addTestSuite(JavaSourceParserTest.class);
    suite.addTestSuite(JavaSourceTypeTest.class);
    //$JUnit-END$
    return suite;
  }
}
