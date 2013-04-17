package de.jdemo.swingui.actions;

import java.awt.Component;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import de.jdemo.framework.DemoIdentifier;
import de.jdemo.runner.DemoSourceCodeNotFoundException;
import de.jdemo.runner.IDemoShowSourceCodeHandler;
import de.jdemo.swingui.icons.JDemoIcons;
import de.jdemo.swingui.model.DemoSelectionModel;
import de.jdemo.swingui.util.MessageUtilities;

/**
 * @author Markus Gebhard
 */
public class ShowSourceCodeAction extends AbstractDemoCaseAction {
  private final IDemoShowSourceCodeHandler handler;

  public ShowSourceCodeAction(IDemoShowSourceCodeHandler handler, DemoSelectionModel selectionModel) {
    super("Show source code", JDemoIcons.getIconResource("goto.gif"), selectionModel, handler != null); //$NON-NLS-2$
    setAcceleratorKey(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
    this.handler = handler;
  }

  protected void execute(Component parentComponent) {
    DemoIdentifier identifier = getSelectedDemoIdentifier();
    try {
      handler.showDemoSourceCode(identifier);
    }
    catch (DemoSourceCodeNotFoundException e) {
      MessageUtilities.showErrorMessageDialog(
          parentComponent,
          "JDemo - Demo source code",
          "The source code of the demo was not found on the source path.");
    }
  }
}