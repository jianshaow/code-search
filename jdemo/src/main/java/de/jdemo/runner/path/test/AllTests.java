package de.jdemo.runner.path.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Markus Gebhard
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for de.jdemo.swingui.showsource.path.test"); //$NON-NLS-1$
    //$JUnit-BEGIN$
    suite.addTestSuite(SourcePathFactoryTest.class);
    suite.addTestSuite(DirectorySourcePathElementTest.class);
    suite.addTestSuite(ZipSourcePathElementTest.class);
    suite.addTestSuite(JarSourcePathElementTest.class);
    suite.addTestSuite(SourcePathTest.class);
    //$JUnit-END$
    return suite;
  }
}
