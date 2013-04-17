package de.java2html.demo;

import de.java2html.gui.Java2HtmlOptionsPanel;
import de.jdemo.extensions.SwingDemoCase;

/**
 * @author Markus Gebhard
 */
public class Java2HtmlOptionsPanelDemo extends SwingDemoCase {

  public void demo() {
    show(new Java2HtmlOptionsPanel().getContent());
  }
}