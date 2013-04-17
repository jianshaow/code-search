package de.java2html.suite;

import de.jdemo.framework.DemoSuite;
import de.jdemo.framework.IDemo;

/**
 * @author Markus Gebhard
 */
public class AllJava2HtmlDemos {

  public static IDemo suite() {
    DemoSuite suite = new DemoSuite("Demo for de.java2html.suite"); //$NON-NLS-1$
    suite.addDemo(de.java2html.demo.AllDemos.suite());
    suite.addDemo(de.java2html.properties.demo.AllDemos.suite());
    //$JDemo-BEGIN$

    //$JDemo-END$
    return suite;
  }
}
