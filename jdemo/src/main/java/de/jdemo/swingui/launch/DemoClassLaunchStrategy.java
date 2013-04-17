package de.jdemo.swingui.launch;

import java.lang.reflect.Method;

import de.jdemo.framework.DemoSuite;
import de.jdemo.framework.IDemo;
import de.jdemo.framework.util.DemoUtilities;
import de.jdemo.swingui.DemoRunnerApplication;

/**
 * @author Markus Gebhard
 */
public class DemoClassLaunchStrategy implements IDemoRunnerLaunchStrategy {
  private String className;

  public DemoClassLaunchStrategy(String className) {
    this.className = className;
  }

  public DemoRunnerApplication createDemoRunnerApplication() throws LaunchException {
    try {
      Class theClass = Class.forName(className);
      Method m;
      try {
        m = theClass.getDeclaredMethod(DemoUtilities.SUITE_METHOD_NAME, new Class[0]);
      }
      catch (NoSuchMethodException e1) {
        return new DemoRunnerApplication(new DemoSuite(theClass));
      }
      Object object = m.invoke(null, new Object[0]);
      IDemo demo = (IDemo) object;
      return new DemoRunnerApplication(demo);
    }
    catch (ClassNotFoundException e) {
      throw new LaunchException("Demo class " + className + " not found on classpath.");
    }
    catch (Exception e) {
      throw new LaunchException("Error creating demo from " + className, e);
    }
  }
}