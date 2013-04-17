package de.jdemo.swingui.launch;

import de.jdemo.framework.DemoIdentifier;
import de.jdemo.framework.IDemoCase;
import de.jdemo.framework.exceptions.DemoClassNotFoundException;
import de.jdemo.framework.util.DemoUtilities;
import de.jdemo.swingui.DemoRunnerApplication;

/**
 * @author Markus Gebhard
 */
public class DemoIdentifierLaunchStrategy implements IDemoRunnerLaunchStrategy {
  private DemoIdentifier demoIdentifier;

  public DemoIdentifierLaunchStrategy(DemoIdentifier demoIdentifier) {
    this.demoIdentifier = demoIdentifier;
  }

  public DemoRunnerApplication createDemoRunnerApplication() throws LaunchException {
    try {
      IDemoCase demoCase = DemoUtilities.createDemo(demoIdentifier);
      return new DemoRunnerApplication(demoCase);
    }
    catch (DemoClassNotFoundException e) {
      throw new LaunchException("Demo class not found: "+demoIdentifier.getClassName());
    }
  }
}