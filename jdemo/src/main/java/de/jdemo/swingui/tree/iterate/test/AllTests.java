package de.jdemo.swingui.tree.iterate.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Markus Gebhard
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for de.jdemo.swingui.tree.iterate.test"); //$NON-NLS-1$
    //$JUnit-BEGIN$
    suite.addTestSuite(NamedDemoCaseIteratorTest.class);
    //$JUnit-END$
    return suite;
  }
}
