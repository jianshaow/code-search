package de.jdemo.swingui.tree.actions;

import javax.swing.JMenu;
import javax.swing.UIManager.LookAndFeelInfo;

import de.jdemo.swingui.IDemoExecuter;
import de.jdemo.swingui.actions.ManageLookAndFeelsAction;
import de.jdemo.swingui.icons.JDemoIcons;
import de.jdemo.swingui.lookandfeel.LookAndFeelRegistry;
import de.jdemo.swingui.lookandfeel.LookAndFeelUtilities;
import de.jdemo.swingui.tree.IDemoSelectionProvider;

/**
 * @author Markus Gebhard
 */
public class ExecuteWithLookAndFeelMenu extends JMenu {

  private IDemoSelectionProvider selectionProvider;
  private IDemoExecuter executer;
  private LookAndFeelRegistry lookAndFeelRegistry;

  public ExecuteWithLookAndFeelMenu(IDemoSelectionProvider selectionProvider, IDemoExecuter executer) {
    super("Run with Look&Feel");
    this.selectionProvider = selectionProvider;
    this.executer = executer;
    lookAndFeelRegistry = LookAndFeelRegistry.getInstance();
    setIcon(JDemoIcons.getIconResource("glasses.gif")); //$NON-NLS-1$
    updateLookAndFeelEntries();
  }

  public void updateLookAndFeelEntries() {
    removeAll();
    addLookAndFeelActions(lookAndFeelRegistry.getSystemDefaultLookAndFeels());
    addLookAndFeelActions(lookAndFeelRegistry.getCustomLookAndFeels());
    add(new ExecuteWithCustomLookAndFeelAction(lookAndFeelRegistry, selectionProvider, executer));
    add(new ManageLookAndFeelsAction());
  }

  private void addLookAndFeelActions(LookAndFeelInfo[] lookAndFeels) {
    for (int i = 0; i < lookAndFeels.length; i++) {
      add(createExecuteWithLookAndFeelAction(lookAndFeels[i]));
    }
    if (lookAndFeels.length > 0) {
      addSeparator();
    }
  }

  private ExecuteWithLookAndFeelAction createExecuteWithLookAndFeelAction(LookAndFeelInfo lookAndFeelInfo) {
    boolean isDefault = lookAndFeelInfo == lookAndFeelRegistry.getDefaultLookAndFeel();

    ExecuteWithLookAndFeelAction action = new ExecuteWithLookAndFeelAction(
        selectionProvider,
        executer,
        lookAndFeelInfo,
        isDefault);
    action.setEnabled(LookAndFeelUtilities.isAvailableLookAndFeelClass(lookAndFeelInfo.getClassName()));
    return action;
  }
}