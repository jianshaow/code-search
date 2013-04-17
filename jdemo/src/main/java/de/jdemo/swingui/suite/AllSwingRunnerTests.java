package de.jdemo.swingui.suite;

import junit.framework.Test;
import junit.framework.TestSuite;

import de.jdemo.junit.Demo2TestConverter;

/**
 * @author Markus Gebhard
 */
public class AllSwingRunnerTests {

  public static void main(final String[] args) {
    junit.textui.TestRunner.run(AllSwingRunnerTests.suite());
  }

  public static Test suite() {
    final TestSuite suite = new TestSuite("Test for de.jdemo.swingui.suite"); //$NON-NLS-1$
    suite.addTest(Demo2TestConverter.createTest(AllSwingRunnerDemos.suite()));
    suite.addTest(de.jdemo.swingui.launch.test.AllTests.suite());
    suite.addTest(de.jdemo.swingui.test.AllTests.suite());
    suite.addTest(de.jdemo.swingui.util.test.AllTests.suite());
    suite.addTest(de.jdemo.runner.path.test.AllTests.suite());
    suite.addTest(de.jdemo.swingui.tree.iterate.test.AllTests.suite());
    //$JUnit-BEGIN$

    //$JUnit-END$
    return suite;
  }
}