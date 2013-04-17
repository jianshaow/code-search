package de.jdemo.swingui.actions;

import java.awt.Component;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import de.jdemo.swingui.DemoRunnerPanel;
import de.jdemo.swingui.util.SmartAction;

public class AboutAction extends SmartAction {

  public AboutAction() {
    super("&About JDemo");
    setAcceleratorKey(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
  }

  protected void execute(Component parentComponent) {
    DemoRunnerPanel.showInfoDialog(parentComponent);
  }
}