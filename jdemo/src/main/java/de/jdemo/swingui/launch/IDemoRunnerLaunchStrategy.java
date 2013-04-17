package de.jdemo.swingui.launch;

import de.jdemo.swingui.DemoRunnerApplication;

/**
 * @author Markus Gebhard
 */
public interface IDemoRunnerLaunchStrategy {

  public DemoRunnerApplication createDemoRunnerApplication() throws LaunchException;

}