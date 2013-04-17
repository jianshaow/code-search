package de.java2html.suite;

import junit.framework.Test;
import junit.framework.TestSuite;

import de.jdemo.junit.Demo2TestConverter;

/**
 * @author Markus Gebhard
 */
public class AllJava2HtmlTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for de.java2html.test"); //$NON-NLS-1$
    suite.addTest(Demo2TestConverter.createTest(AllJava2HtmlDemos.suite()));
    suite.addTest(de.java2html.converter.test.AllTests.suite());
    suite.addTest(de.java2html.plugin.jspwiki.test.AllTests.suite());
    suite.addTest(de.java2html.properties.test.AllTests.suite());
    suite.addTest(de.java2html.util.test.AllTests.suite());
    suite.addTest(de.java2html.options.test.AllTests.suite());
    suite.addTest(de.java2html.javasource.test.AllTests.suite());
    suite.addTest(de.java2html.commandline.test.AllTests.suite());
    suite.addTest(de.java2html.test.AllTests.suite());
    //$JUnit-BEGIN$
    //$JUnit-END$
    return suite;
  }
}