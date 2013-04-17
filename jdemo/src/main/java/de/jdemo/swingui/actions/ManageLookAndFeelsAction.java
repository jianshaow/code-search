package de.jdemo.swingui.actions;

import java.awt.Component;

import de.jdemo.swingui.DemoRunnerPanel;
import de.jdemo.swingui.icons.JDemoIcons;
import de.jdemo.swingui.lookandfeel.LookAndFeelManagerDialogPage;
import de.jdemo.swingui.util.SmartAction;

/**
 * @author Markus Gebhard
 */
public class ManageLookAndFeelsAction extends SmartAction {

  public ManageLookAndFeelsAction() {
    super("Manage Look&&Feels...");
    setIcon(JDemoIcons.getIconResource("glasses.gif")); //$NON-NLS-1$
  }

  protected void execute(Component parentComponent) {
    DemoRunnerPanel.initializeDemoRunnerLookAndFeel();
    LookAndFeelManagerDialogPage.show(parentComponent);
  }
}