package de.java2html.commandline.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Markus Gebhard
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for de.java2html.commandline.test");
    //$JUnit-BEGIN$
    suite.addTestSuite(Java2HtmlCommandlineTest.class);
    suite.addTestSuite(CommandlineArgumentsTest.class);
    //$JUnit-END$
    return suite;
  }
}