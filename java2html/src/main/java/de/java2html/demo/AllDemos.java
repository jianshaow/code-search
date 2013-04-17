package de.java2html.demo;

import de.jdemo.framework.DemoSuite;
import de.jdemo.framework.IDemo;

/**
 * @author Markus Gebhard
 */
public class AllDemos {

  public static IDemo suite() {
    DemoSuite suite = new DemoSuite("Demo for de.java2html.demo"); //$NON-NLS-1$
    //$JDemo-BEGIN$
    suite.addDemo(new DemoSuite(ConvertToHtmlDemo.class));
    suite.addDemo(new DemoSuite(Java2HtmlOptionsPanelDemo.class));
    //$JDemo-END$
    return suite;
  }
}
