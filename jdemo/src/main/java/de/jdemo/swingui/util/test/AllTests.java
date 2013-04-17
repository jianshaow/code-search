package de.jdemo.swingui.util.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Markus Gebhard
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for de.jdemo.swingui.util.test"); //$NON-NLS-1$
    //$JUnit-BEGIN$
    suite.addTestSuite(SmartActionTest.class);
    suite.addTestSuite(DefaultMutableTreeNodeIteratorTest.class);
    //$JUnit-END$
    return suite;
  }
}