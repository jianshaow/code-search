package de.jdemo.swingui.launch.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Markus Gebhard
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for de.jdemo.swingui.launch.test"); //$NON-NLS-1$
    //$JUnit-BEGIN$
    suite.addTestSuite(DemoRunnerCommandLineParserTest.class);
    suite.addTestSuite(DemoRunnerLauchStrategiesTest.class);
    //$JUnit-END$
    return suite;
  }
}
