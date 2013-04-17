package de.jdemo.swingui.demo;

import de.jdemo.framework.DemoSuite;
import de.jdemo.framework.IDemo;

/**
 * @author Markus Gebhard
 */
public class AllDemos {

  public static void main(String[] args) {
    de.jdemo.swingui.DemoRunner.run(AllDemos.class);
  }

  public static IDemo suite() {
    DemoSuite suite = new DemoSuite("Demo for de.jdemo.swingui.demo"); //$NON-NLS-1$
    //$JDemo-BEGIN$
    suite.addDemo(new DemoSuite(DemoRunnerAboutDialogDemo.class));
    //$JDemo-END$
    return suite;
  }
}
