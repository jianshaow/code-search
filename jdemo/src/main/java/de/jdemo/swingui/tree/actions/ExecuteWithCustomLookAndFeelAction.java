package de.jdemo.swingui.tree.actions;

import java.awt.Component;

import javax.swing.UIManager.LookAndFeelInfo;

import de.jdemo.framework.IDemoCase;
import de.jdemo.swingui.IDemoExecuter;
import de.jdemo.swingui.lookandfeel.LookAndFeelRegistry;
import de.jdemo.swingui.tree.IDemoSelectionProvider;
import de.jdemo.swingui.util.SmartAction;
import de.jdemo.swingui.util.UserDialog;

/**
 * @author Markus Gebhard
 */
public class ExecuteWithCustomLookAndFeelAction extends SmartAction {

  private IDemoExecuter executer;
  private IDemoSelectionProvider selectionProvider;
  private LookAndFeelRegistry lookAndFeelRegistry;

  public ExecuteWithCustomLookAndFeelAction(
      LookAndFeelRegistry lookAndFeelRegistry,
      IDemoSelectionProvider selectionProvider,
      IDemoExecuter executer) {
    super("Custom Look&&Feel...");
    this.lookAndFeelRegistry = lookAndFeelRegistry;
    this.selectionProvider = selectionProvider;
    this.executer = executer;
  }

  protected void execute(Component parentComponent) {
    CustomLookAndFeelChooserDialog dialog = new CustomLookAndFeelChooserDialog(false);
    UserDialog userDialog = new UserDialog(parentComponent, dialog);
    userDialog.show();
    if (userDialog.isCancelled()) {
      return;
    }
    CustomLookAndFeelOptions options = dialog.getSelectedOptions();
    if (options.isSaveLookAndFeel()) {
      LookAndFeelInfo lookAndFeelInfo = new LookAndFeelInfo(options.getName(), options.getLookAndFeelClassName());
      lookAndFeelRegistry.addCustomLookAndFeel(lookAndFeelInfo);
    }

    IDemoCase demo = selectionProvider.getSelectedDemoCase();
    executer.executeDemo(demo, options.getLookAndFeelClassName());
  }
}