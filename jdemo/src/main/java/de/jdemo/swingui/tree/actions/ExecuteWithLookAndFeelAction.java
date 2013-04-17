package de.jdemo.swingui.tree.actions;

import java.awt.Component;

import javax.swing.UIManager.LookAndFeelInfo;

import de.jdemo.framework.IDemoCase;
import de.jdemo.swingui.IDemoExecuter;
import de.jdemo.swingui.tree.IDemoSelectionProvider;
import de.jdemo.swingui.util.SmartAction;

/**
 * @author Markus Gebhard
 */
public class ExecuteWithLookAndFeelAction extends SmartAction {

  private LookAndFeelInfo lookAndFeel;
  private IDemoExecuter executer;
  private IDemoSelectionProvider selectionProvider;

  public ExecuteWithLookAndFeelAction(
      IDemoSelectionProvider selectionProvider,
      IDemoExecuter executer,
      LookAndFeelInfo lookAndFeel,
      boolean isDefault) {
    super(isDefault ? lookAndFeel.getName() + " (default)" : lookAndFeel.getName());
    this.selectionProvider = selectionProvider;
    this.executer = executer;
    this.lookAndFeel = lookAndFeel;
  }

  protected void execute(Component parentComponent) {
    IDemoCase demo = selectionProvider.getSelectedDemoCase();
    executer.executeDemo(demo, lookAndFeel.getClassName());
  }
}