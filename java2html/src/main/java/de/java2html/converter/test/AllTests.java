package de.java2html.converter.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Markus Gebhard
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for de.java2html.converter.test");
    //$JUnit-BEGIN$
    suite.addTestSuite(JavaSourceConverterProviderTest.class);
    suite.addTestSuite(JavaSource2RtfConverterTest.class);
    suite.addTestSuite(JavaSource2HTMLConverterTest.class);
    suite.addTestSuite(JavaSource2TeXConverterTest.class);
    suite.addTestSuite(JavaSource2XmlConverterTest.class);
    suite.addTestSuite(JavaSource2XhtmlConverterTest.class);
    suite.addTestSuite(JavaSource2Xhtml11ConverterTest.class);
    //$JUnit-END$
    return suite;
  }
}