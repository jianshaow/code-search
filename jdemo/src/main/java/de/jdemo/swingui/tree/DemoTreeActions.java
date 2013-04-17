package de.jdemo.swingui.tree;

import java.awt.Component;

import javax.swing.Action;
import javax.swing.JPopupMenu;

import de.jdemo.extensions.SwingDemoCase;
import de.jdemo.runner.IDemoShowSourceCodeHandler;
import de.jdemo.swingui.DemoRunnerPanel;
import de.jdemo.swingui.IDemoExecuter;
import de.jdemo.swingui.actions.CopyDemoIdentifierAction;
import de.jdemo.swingui.actions.ShowSourceCodeAction;
import de.jdemo.swingui.icons.JDemoIcons;
import de.jdemo.swingui.model.DemoSelectionModel;
import de.jdemo.swingui.tree.actions.ExecuteSelectedDemoCaseAction;
import de.jdemo.swingui.tree.actions.ExecuteWithLookAndFeelMenu;
import de.jdemo.swingui.util.SmartAction;

/**
 * @author Markus Gebhard
 */
public class DemoTreeActions {
  private JPopupMenu standardPopupMenu;
  private JPopupMenu demoCasePopupMenu;
  private Action removeSelectedDemoAction;
  private ExecuteSelectedDemoCaseAction executeSelectedDemoCaseAction;
  private CopyDemoIdentifierAction copySelectedDemoCaseAction;
  private Action showSelectedDemoCaseSourceCodeAction;
  private Action removeDuplicatesAction;
  private IDemoTree tree;
  //  private Action renameSelectedDemoAction;
  //  private Action createNewFolderAction;
  private ExecuteWithLookAndFeelMenu executeWithLookAndFeelMenu;
  private IDemoExecuter executer;
  private IDemoShowSourceCodeHandler showSourceCodeHandler;
  private final DemoSelectionModel demoSelectionModel;

  public DemoTreeActions(
      IDemoTree tree,
      IDemoExecuter executer,
      IDemoShowSourceCodeHandler showSourceCodeHandler,
      DemoSelectionModel demoSelectionModel) {
    this.tree = tree;
    this.executer = executer;
    this.showSourceCodeHandler = showSourceCodeHandler;
    this.demoSelectionModel = demoSelectionModel;
    executeWithLookAndFeelMenu = new ExecuteWithLookAndFeelMenu(tree, executer);
  }

  public synchronized JPopupMenu getDemoCasePopupMenu() {
    DemoRunnerPanel.initializeDemoRunnerLookAndFeel();
    executeWithLookAndFeelMenu.updateLookAndFeelEntries();
    if (demoCasePopupMenu == null) {
      demoCasePopupMenu = new JPopupMenu();
      demoCasePopupMenu.add(getExecuteSelectedDemoCaseAction());
      demoCasePopupMenu.add(executeWithLookAndFeelMenu);
      demoCasePopupMenu.addSeparator();
      demoCasePopupMenu.add(getCopySelectedDemoCaseIdentifierAction());
      demoCasePopupMenu.add(getShowSelectedDemoCaseSourceCodeAction());
      demoCasePopupMenu.addSeparator();
      demoCasePopupMenu.add(getRemoveSelectedDemoAction());
      demoCasePopupMenu.add(getRemoveDuplicatesAction());
      //      demoCasePopupMenu.addSeparator();
      //      demoCasePopupMenu.add(getRenameSelectedDemoAction());
    }
    return demoCasePopupMenu;
  }

  public synchronized JPopupMenu getStandardPopupMenu() {
    DemoRunnerPanel.initializeDemoRunnerLookAndFeel();
    if (standardPopupMenu == null) {
      standardPopupMenu = new JPopupMenu();
      //      standardPopupMenu.add(getCreateNewFolderAction());
      standardPopupMenu.add(getRemoveSelectedDemoAction());
      standardPopupMenu.add(getRemoveDuplicatesAction());
      //      standardPopupMenu.addSeparator();
      //      standardPopupMenu.add(getRenameSelectedDemoAction());
    }
    return standardPopupMenu;
  }

  public synchronized Action getRemoveSelectedDemoAction() {
    if (removeSelectedDemoAction == null) {
      removeSelectedDemoAction = new SmartAction("Remove", JDemoIcons.getIconResource("remove.gif")) {//$NON-NLS-2$
        protected void execute(Component parentComponent) {
          tree.removeSelectedDemo();
        }
      };
    }
    return removeSelectedDemoAction;
  }

  public synchronized Action getRemoveDuplicatesAction() {
    if (removeDuplicatesAction == null) {
      removeDuplicatesAction = new SmartAction("Remove duplicates", JDemoIcons
          .getIconResource("remove_duplicates.gif")) {//$NON-NLS-2$ //$NON-NLS-1$
        protected void execute(Component parentComponent) {
          tree.removeDuplicates();
          //This action can only be applied once.
          getRemoveDuplicatesAction().setEnabled(false);
        }
      };
    }
    return removeDuplicatesAction;
  }

  public synchronized Action getShowSelectedDemoCaseSourceCodeAction() {
    if (showSelectedDemoCaseSourceCodeAction == null) {
      showSelectedDemoCaseSourceCodeAction = new ShowSourceCodeAction(showSourceCodeHandler, demoSelectionModel);
    }
    return showSelectedDemoCaseSourceCodeAction;
  }

  public synchronized Action getCopySelectedDemoCaseIdentifierAction() {
    if (copySelectedDemoCaseAction == null) {
      copySelectedDemoCaseAction = new CopyDemoIdentifierAction(demoSelectionModel);
    }
    return copySelectedDemoCaseAction;
  }

  public synchronized ExecuteSelectedDemoCaseAction getExecuteSelectedDemoCaseAction() {
    if (executeSelectedDemoCaseAction == null) {
      executeSelectedDemoCaseAction = new ExecuteSelectedDemoCaseAction(executer, tree);
    }
    return executeSelectedDemoCaseAction;
  }

  public void updateEnabled() {
    boolean demoCaseSelected = tree.getSelectedDemoCase() != null;
    getExecuteSelectedDemoCaseAction().setEnabled(demoCaseSelected);
    executeWithLookAndFeelMenu.setEnabled(demoCaseSelected
        && SwingDemoCase.class.isAssignableFrom(tree.getSelectedDemoCase().getClass()));

    boolean demoSelected = tree.getSelectedDemoCase() != null;
    getRemoveSelectedDemoAction().setEnabled(demoSelected);
    //    getRenameSelectedDemoAction().setEnabled(demoSelected);

    //    boolean demoSuiteSelected = tree.getSelectedDemo() instanceof INamedDemoSuite;
    //getCreateNewFolderAction().setEnabled(demoSuiteSelected);
  }
}