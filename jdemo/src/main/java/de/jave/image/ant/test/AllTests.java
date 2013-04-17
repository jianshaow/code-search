package de.jave.image.ant.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Markus Gebhard
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for de.jdemo.capture.anttasks.test"); //$NON-NLS-1$
    //$JUnit-BEGIN$
    suite.addTestSuite(RescaleOptionsTest.class);
    suite.addTestSuite(PositionedImageFileTest.class);
    suite.addTestSuite(ImageCompositionTaskTest.class);
    suite.addTestSuite(ImageProcessingTaskTest.class);
    //$JUnit-END$
    return suite;
  }
}