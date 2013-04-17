package de.jdemo.extensions;

import de.jdemo.framework.DemoCaseRunnable;

/**
 * @author Markus Gebhard
 */
public class SwtDemoCaseRunnable extends DemoCaseRunnable {

  public SwtDemoCaseRunnable(final SwtDemoCase demo) {
    super(demo);
  }

  public SwtDemoCase getSwtDemoCase() {
    return (SwtDemoCase) getDemo();
  }

  @Override
  public void run() {
    //SWT Demos must be executed in a separate Thread - never on Event dispatch thread! Otherwise
    //Swing based DemoRunners will freeze while demo is running.
    final Thread thread = new Thread(Thread.currentThread().getThreadGroup(), new Runnable() {
      public void run() {
        runInternal();
      }
    });
    setRunThread(thread);
    thread.start();
  }

  @Override
  protected void runMainDemo() throws Throwable {
    getSwtDemoCase().runDemo();
    exit();
    /*TODO Jul 21, 2004 (Markus Gebhard): Ensure that 'show' was called - as soon as it is clear
    what show methods in SWT are...*/
  }
}