package de.java2html.options.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Markus Gebhard
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for de.java2html.options.test");
    //$JUnit-BEGIN$
    suite.addTestSuite(JavaSourceStyleEntryTest.class);
    suite.addTestSuite(JavaSourceStyleTableTest.class);
    suite.addTestSuite(HorizontalAlignmentTest.class);
    suite.addTestSuite(JavaSourceConversionOptionsTest.class);
    //$JUnit-END$
    return suite;
  }
}
