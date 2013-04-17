package de.jdemo.swingui.actions;

import javax.swing.Icon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.jdemo.framework.DemoIdentifier;
import de.jdemo.framework.IDemo;
import de.jdemo.framework.IDemoCase;
import de.jdemo.framework.IDemoSuite;
import de.jdemo.framework.IDemoVisitor;
import de.jdemo.swingui.model.DemoSelectionModel;
import de.jdemo.swingui.util.SmartAction;

public abstract class AbstractDemoCaseAction extends SmartAction {

  private final DemoSelectionModel selectionModel;
  private final boolean available;

  public AbstractDemoCaseAction(String label, Icon icon, DemoSelectionModel selectionModel) {
    this(label, icon, selectionModel, true);
  }

  public AbstractDemoCaseAction(String label, Icon icon, DemoSelectionModel selectionModel, boolean available) {
    super(label, icon);
    this.available = available;
    this.selectionModel = selectionModel;

    selectionModel.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        updateEnabled();
      }
    });
    updateEnabled();
  }

  private void updateEnabled() {
    setEnabled(available && isDemoCaseSelected());
  }

  private boolean isDemoCaseSelected() {
    return getSelectedDemoIdentifier() != null;
  }

  protected final DemoIdentifier getSelectedDemoIdentifier() {
    IDemo selectedDemo = selectionModel.getSelectedDemo();
    if (selectedDemo == null) {
      return null;
    }
    final DemoIdentifier[] result = new DemoIdentifier[1];
    selectedDemo.accept(new IDemoVisitor() {
      public void visitDemoSuite(IDemoSuite demoSuite) {
        result[0] = null;
      }

      public void visitDemoCase(IDemoCase demoCase) {
        result[0] = demoCase.getIdentifier();
      }
    });
    return result[0];
  }
}
