package de.java2html.properties.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Markus Gebhard
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for de.java2html.converter.options.test");
    //$JUnit-BEGIN$
    suite.addTestSuite(ConversionOptionsPropertiesReaderTest.class);
    suite.addTestSuite(ConversionOptionsPropertiesPersistenceTest.class);
    //$JUnit-END$
    return suite;
  }
}
