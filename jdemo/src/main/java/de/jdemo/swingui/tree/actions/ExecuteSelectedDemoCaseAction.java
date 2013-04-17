package de.jdemo.swingui.tree.actions;

import java.awt.Component;

import de.jdemo.swingui.IDemoExecuter;
import de.jdemo.swingui.icons.JDemoIcons;
import de.jdemo.swingui.tree.IDemoSelectionProvider;
import de.jdemo.swingui.util.SmartAction;

/**
 * @author Markus Gebhard
 */
public class ExecuteSelectedDemoCaseAction extends SmartAction {

  private final IDemoExecuter executer;
  private final IDemoSelectionProvider selectionProvider;

  public ExecuteSelectedDemoCaseAction(IDemoExecuter executer, IDemoSelectionProvider selectionProvider) {
    super("Run", JDemoIcons.getIconResource("run.gif")); //$NON-NLS-2$
    this.executer = executer;
    this.selectionProvider = selectionProvider;
  }

  protected void execute(Component parentComponent) {
    executer.executeDemo(selectionProvider.getSelectedDemoCase(), null);
  }
}