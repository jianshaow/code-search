package de.jdemo.swingui.tree.actions.demo;

import de.jdemo.framework.DemoSuite;
import de.jdemo.framework.IDemo;

/**
 * @author Markus Gebhard
 */
public class AllDemos {

  public static IDemo suite() {
    DemoSuite suite = new DemoSuite("Demo for de.jdemo.swingui.tree.actions.demo"); //$NON-NLS-1$
    //$JDemo-BEGIN$
    suite.addDemo(new DemoSuite(CustomLookAndFeelChooserDialogDemo.class));
    //$JDemo-END$
    return suite;
  }
}
