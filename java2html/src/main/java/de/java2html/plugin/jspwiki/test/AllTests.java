package de.java2html.plugin.jspwiki.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Markus Gebhard
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for de.java2html.plugin.jspwiki.test");
    //$JUnit-BEGIN$
    suite.addTestSuite(Java2HtmlPluginTest.class);
    suite.addTestSuite(PluginParameterCheckerTest.class);
    //$JUnit-END$
    return suite;
  }
}
