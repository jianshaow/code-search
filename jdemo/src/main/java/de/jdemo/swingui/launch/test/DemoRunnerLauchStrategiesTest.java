package de.jdemo.swingui.launch.test;

import de.jdemo.framework.DemoIdentifier;
import de.jdemo.runner.path.test.TestDataTestCase;
import de.jdemo.swingui.launch.DemoClassLaunchStrategy;
import de.jdemo.swingui.launch.DemoIdentifierLaunchStrategy;
import de.jdemo.swingui.launch.EmptyDemoRunnerLaunchStrategy;
import de.jdemo.swingui.launch.IDemoRunnerLaunchStrategy;
import de.jdemo.swingui.launch.LaunchException;
import de.jdemo.swingui.suite.testdemos.HelloWorldDemo;

/**
 * @author Markus Gebhard
 */
public class DemoRunnerLauchStrategiesTest extends TestDataTestCase {

  public void testEmptyLaunchStrategy() throws LaunchException {
    IDemoRunnerLaunchStrategy strategy = new EmptyDemoRunnerLaunchStrategy();
    assertNotNull(strategy.createDemoRunnerApplication());
  }

  public void testDemoIdentifierLaunchStrategy() throws LaunchException {
    IDemoRunnerLaunchStrategy strategy = new DemoIdentifierLaunchStrategy(new DemoIdentifier(HelloWorldDemo.class
        .getName(), "demoHelloWorld")); //$NON-NLS-1$
    assertNotNull(strategy.createDemoRunnerApplication());
  }

  public void testDemoClassNameLaunchStrategy() throws LaunchException {
    IDemoRunnerLaunchStrategy strategy = new DemoClassLaunchStrategy(HelloWorldDemo.class.getName());
    assertNotNull(strategy.createDemoRunnerApplication());
  }
}