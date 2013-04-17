package de.jdemo.swingui;

import de.jdemo.framework.IDemoCase;

/**
 * @author Markus Gebhard
 */
public interface IDemoExecuter {

  public void executeDemo(IDemoCase demoCase, String lookAndFeelClassName);

}