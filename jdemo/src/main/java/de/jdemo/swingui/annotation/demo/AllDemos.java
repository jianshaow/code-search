package de.jdemo.swingui.annotation.demo;

import de.jdemo.framework.DemoSuite;
import de.jdemo.framework.IDemo;

/**
 * @author Markus Gebhard
 */
public class AllDemos {

  public static IDemo suite() {
    DemoSuite suite = new DemoSuite("Demo for de.jdemo.swingui.annotation.demo"); //$NON-NLS-1$
    //$JDemo-BEGIN$
    suite.addDemo(new DemoSuite(DemoAnnotationPanelDemo.class));
    //$JDemo-END$
    return suite;
  }
}
