package de.jdemo.swingui.lookandfeel;

import javax.swing.LookAndFeel;

/**
 * @author Markus Gebhard
 */
public class LookAndFeelUtilities {

  public static boolean isClassAvailable(String className) {
    try {
      Class.forName(className);
    }
    catch (ClassNotFoundException exception) {
      return false;
    }
    catch (NoClassDefFoundError error) {
      return false;
    }
    return true;
  }

  public static boolean isLookAndFeelClass(String className) {
    Class clazz;
    try {
      clazz = Class.forName(className);
    }
    catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    return LookAndFeel.class.isAssignableFrom(clazz);
  }

  public static boolean isAvailableLookAndFeelClass(String className) {
    return isClassAvailable(className) && isLookAndFeelClass(className);
  }

}