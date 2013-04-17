package de.jdemo.swingui.demo;

import de.jdemo.extensions.SwingDemoCase;
import de.jdemo.swingui.DemoRunnerAboutDialog;

/**
 * @author Markus Gebhard
 */
public class DemoRunnerAboutDialogDemo extends SwingDemoCase {
 
  public void demo() {
    show(new DemoRunnerAboutDialog(createParentComponent()).getDialog());
  }

}