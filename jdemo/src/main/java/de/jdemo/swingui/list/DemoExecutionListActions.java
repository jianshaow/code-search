package de.jdemo.swingui.list;

import java.awt.Component;
import java.awt.Window;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import de.jdemo.extensions.GuiDemoCase;
import de.jdemo.framework.IDemoCaseRunnable;
import de.jdemo.framework.state.DemoState;
import de.jdemo.runner.IDemoShowSourceCodeHandler;
import de.jdemo.swingui.actions.CopyDemoIdentifierAction;
import de.jdemo.swingui.actions.ShowSourceCodeAction;
import de.jdemo.swingui.icons.JDemoIcons;
import de.jdemo.swingui.model.DemoSelectionModel;
import de.jdemo.swingui.util.SmartAction;

/**
 * @author Markus Gebhard
 */
public class DemoExecutionListActions {
  private final IDemoExecutionList list;
  private Action bringToFrontAction;
  private Action copyDemoIdAction;
  private Action reRunAction;
  private Action cancelAction;
  private Action showSourceCodeAction;
  private Action removeAllTerminatedAction;
  private JPopupMenu menu;
  private IDemoShowSourceCodeHandler showSourceCodeHandler;
  private final DemoSelectionModel selectionModel;

  public DemoExecutionListActions(
      DemoSelectionModel selectionModel,
      IDemoExecutionList list,
      IDemoShowSourceCodeHandler showSourceCodeHandler) {
    this.selectionModel = selectionModel;
    this.list = list;
    this.showSourceCodeHandler = showSourceCodeHandler;
  }

  public synchronized JPopupMenu getPopupMenu() {
    if (menu == null) {
      menu = new JPopupMenu();
      menu.add(getBringToFrontAction());
      menu.add(getCancelSelectedDemoAction());
      menu.add(getRerunSelectedDemoAction());
      menu.addSeparator();
      menu.add(getCopySelectedDemoIdentifierAction());
      menu.add(getShowSourceCodeAction());
      menu.addSeparator();
      menu.add(getRemoveAllTerminatedAction());
    }
    return menu;
  }

  public synchronized Action getShowSourceCodeAction() {
    if (showSourceCodeAction == null) {
      showSourceCodeAction = new ShowSourceCodeAction(showSourceCodeHandler, selectionModel);
    }
    return showSourceCodeAction;
  }

  public synchronized Action getCopySelectedDemoIdentifierAction() {
    if (copyDemoIdAction == null) {
      copyDemoIdAction = new CopyDemoIdentifierAction(selectionModel);
    }
    return copyDemoIdAction;
  }

  public synchronized Action getRemoveAllTerminatedAction() {
    if (removeAllTerminatedAction == null) {
      removeAllTerminatedAction = new SmartAction("Remove all terminated", JDemoIcons
          .getIconResource("removeall.gif")) { //$NON-NLS-1$
        protected void execute(Component parentComponent) {
          list.removeAllTerminated();
        }
      };
    }
    return removeAllTerminatedAction;
  }

  private synchronized Action getCancelSelectedDemoAction() {
    if (cancelAction == null) {
      cancelAction = new SmartAction("Cancel", JDemoIcons.getIconResource("cancel.gif")) {//$NON-NLS-2$
        protected void execute(Component parentComponent) {
          list.cancelSelectedDemo();
        }
      };
    }
    return cancelAction;
  }

  private synchronized Action getRerunSelectedDemoAction() {
    if (reRunAction == null) {
      reRunAction = new SmartAction("Rerun", JDemoIcons.getIconResource("run.gif")) {//$NON-NLS-2$
        protected void execute(Component parentComponent) {
          list.rerunSelectedDemo();
        }
      };
    }
    return reRunAction;
  }

  private synchronized Action getBringToFrontAction() {
    if (bringToFrontAction == null) {
      bringToFrontAction = new SmartAction("Bring to Front", JDemoIcons.getIconResource("bring_to_front.gif")) { //$NON-NLS-2$
        protected void execute(Component parentComponent) {
          GuiDemoCase demoCase = (GuiDemoCase) list.getSelectedDemoRunner().getDemo();
          Window demoWindow = demoCase.getRegisteredDemoWindow();
          if (demoWindow == null) {
            JOptionPane
                .showMessageDialog(
                    parentComponent,
                    "The selected demo does not have a registered demo window. Make sure that the demo implementation follows the guidelines for gui demos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
          }
          demoWindow.toFront();
        }
      };
    }
    return bringToFrontAction;
  }

  public void updateEnabled() {
    IDemoCaseRunnable selectedRunner = list.getSelectedDemoRunner();
    getCancelSelectedDemoAction().setEnabled(
        selectedRunner != null && selectedRunner.getState().equals(DemoState.RUNNING));
    getRerunSelectedDemoAction().setEnabled(selectedRunner != null);
    getRemoveAllTerminatedAction().setEnabled(!list.isEmpty());
    getBringToFrontAction().setEnabled(isBringToFrontAvailable(selectedRunner));
  }

  private boolean isBringToFrontAvailable(IDemoCaseRunnable selectedRunner) {
    return selectedRunner != null
        && selectedRunner.getState() == DemoState.RUNNING
        && GuiDemoCase.class.isAssignableFrom(selectedRunner.getDemo().getClass());
  }
}