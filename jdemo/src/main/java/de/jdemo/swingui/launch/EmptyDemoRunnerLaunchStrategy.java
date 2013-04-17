package de.jdemo.swingui.launch;

import de.jdemo.swingui.DemoRunnerApplication;


/**
 * @author Markus Gebhard
 */
public class EmptyDemoRunnerLaunchStrategy implements IDemoRunnerLaunchStrategy {

  public DemoRunnerApplication createDemoRunnerApplication() {
    return new DemoRunnerApplication();
  }
}