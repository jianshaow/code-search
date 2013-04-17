package de.jdemo.swingui.suite;

import de.jdemo.framework.DemoSuite;
import de.jdemo.framework.IDemo;

/**
 * @author Markus Gebhard
 */
public class AllSwingRunnerDemos {

  public static IDemo suite() {
    DemoSuite suite = new DemoSuite("Demo for de.jdemo.swingui.suite"); //$NON-NLS-1$
    suite.addDemo(de.jdemo.swingui.demo.AllDemos.suite());
    suite.addDemo(de.jdemo.swingui.showsource.demo.AllDemos.suite());
    suite.addDemo(de.jdemo.swingui.tree.actions.demo.AllDemos.suite());
    suite.addDemo(de.jdemo.swingui.find.demo.AllDemos.suite());
    suite.addDemo(de.jdemo.swingui.annotation.demo.AllDemos.suite());
    //$JDemo-BEGIN$

    //$JDemo-END$
    return suite;
  }
}