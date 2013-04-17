package de.jdemo.swingui.tree.actions;


/**
 * @author Markus Gebhard
 */
public class CustomLookAndFeelOptions {

  private String name;
  private boolean isSave;
  private String className;

  public CustomLookAndFeelOptions(String className, boolean isSave, String name) {
    this.className = className;
    this.isSave = isSave;
    this.name = name;
  }

  public String getLookAndFeelClassName() {
    return className;
  }

  public boolean isSaveLookAndFeel() {
    return isSave;
  }

  public String getName() {
    return name;
  }
}