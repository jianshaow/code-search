package de.jave.image.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Markus Gebhard
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for de.jave.image.test"); //$NON-NLS-1$
    //$JUnit-BEGIN$
    suite.addTestSuite(ImageIoUtilitiesTest.class);
    suite.addTestSuite(RescaleQualityHintTest.class);
    //$JUnit-END$
    return suite;
  }
}