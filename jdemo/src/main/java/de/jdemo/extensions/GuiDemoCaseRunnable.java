package de.jdemo.extensions;

import java.awt.Window;

import de.jdemo.framework.DemoCaseRunnable;

/**
 * @author Markus Gebhard
 */
public class GuiDemoCaseRunnable extends DemoCaseRunnable {

  public GuiDemoCaseRunnable(final GuiDemoCase demoCase) {
    super(demoCase);
  }

  @Override
  protected void runMainDemo() throws Throwable {
    ((GuiDemoCase) getDemo()).runDemo();
    final Window demoWindow = ((GuiDemoCase) getDemo()).getRegisteredDemoWindow();
    if (demoWindow != null && !demoWindow.isVisible()) {
      exit();
    }
    checkShowWasCalled();
  }
}