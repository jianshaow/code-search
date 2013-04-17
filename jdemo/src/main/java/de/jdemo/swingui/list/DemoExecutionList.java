package de.jdemo.swingui.list;

import java.awt.Point;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.jdemo.extensions.SwingDemoCase;
import de.jdemo.framework.DemoIdentifier;
import de.jdemo.framework.IDemo;
import de.jdemo.framework.IDemoCase;
import de.jdemo.framework.IDemoCaseRunnable;
import de.jdemo.framework.state.DemoState;
import de.jdemo.framework.state.IDemoStateChangeEvent;
import de.jdemo.framework.state.IDemoStateChangeListener;
import de.jdemo.swingui.DemoRunnerPanel;
import de.jdemo.swingui.model.DemoSelectionModel;

/**
 * @author Markus Gebhard
 */
public class DemoExecutionList implements IDemoExecutionList {
  private final DemoExecutionListActions actions;
  private final DemoRunnerPanel runner;
  private final JList list;
  private JComponent component;

  //TODO Apr 15, 2004 (Markus Gebhard): runner wird nur zur Anzeige des Status benötigt => ISP
  public DemoExecutionList(final DemoRunnerPanel runner, final DemoSelectionModel selectionModel) {
    this.runner = runner;
    list = new JList(new DefaultListModel());
    actions = new DemoExecutionListActions(selectionModel, this, runner.getShowSourceCodeHandler());
    list.add(actions.getPopupMenu());
    list.setCellRenderer(new DemoExecutionListCellRenderer());
    list.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(final MouseEvent event) {
        list.requestFocus();
        final int index = list.getUI().locationToIndex(list, event.getPoint());
        if (index >= 0 && index < list.getModel().getSize()) {
          list.setSelectedIndex(index);
        }
      }

      @Override
      public void mouseReleased(final MouseEvent event) {
        if (event.isMetaDown()) {
          showPopup(event.getPoint());
        }
      }
    });
    list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(final ListSelectionEvent e) {
        listSelectionChanged();
        final IDemoCaseRunnable selectedDemoRunner = getSelectedDemoRunner();
        selectionModel.setSelectedDemo(selectedDemoRunner == null ? null : selectedDemoRunner.getDemo());
        updateActionsEnabled();
      }
    });
    list.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(final FocusEvent e) {
        selectionModel.setSelectedDemo(getSelectedDemo());
      }
    });

    updateActionsEnabled();
  }

  private void updateActionsEnabled() {
    actions.updateEnabled();
  }

  private void listSelectionChanged() {
    //TODO Nov 1, 2004 (Markus Gebhard): Attach StatusIndicator as Listener to the SelectionModel
    final IDemo selectedDemo = getSelectedDemo();
    if (selectedDemo == null) {
      getRunner().setStatus(""); //$NON-NLS-1$
    }
    else {
      getRunner().setStatus(selectedDemo.toString());
    }
  }

  private DemoRunnerPanel getRunner() {
    return runner;
  }

  public void rerunSelectedDemo() {
    final IDemoCaseRunnable demoRunner = getSelectedDemoRunner();
    if (demoRunner == null) {
      return;
    }
    String lookAndFeelClassName = null;
    final IDemoCase demoCase = demoRunner.getDemo();
    if (demoCase instanceof SwingDemoCase) {
      lookAndFeelClassName = ((SwingDemoCase) demoCase).getConfiguredLookAndFeelClassName();
    }
    runner.executeDemo(demoCase, lookAndFeelClassName);
  }

  protected void showPopup(final Point point) {
    actions.getPopupMenu().show(list, point.x, point.y);
  }

  public synchronized JComponent getComponent() {
    if (component == null) {
      component = new JScrollPane(list);
    }
    return component;
  }

  private DefaultListModel getListModel() {
    return (DefaultListModel) list.getModel();
  }

  public void removeAllTerminated() {
    final DefaultListModel model = getListModel();
    for (int i = model.getSize() - 1; i >= 0; --i) {
      if (((IDemoCaseRunnable) model.get(i)).getState().isTerminated()) {
        model.remove(i);
      }
    }
  }

  public IDemo getSelectedDemo() {
    final IDemoCaseRunnable runner = getSelectedDemoRunner();
    if (runner == null) {
      return null;
    }
    return runner.getDemo();
  }

  public IDemoCase getSelectedDemoCase() {
    final IDemo demo = getSelectedDemo();
    if (demo != null && demo instanceof IDemoCase) {
      return (IDemoCase) demo;
    }
    else {
      return null;
    }
  }

  public DemoIdentifier getSelectedDemoIdentifier() {
    final IDemoCase demoCase = getSelectedDemoCase();
    return (demoCase == null) ? null : demoCase.getIdentifier();
  }

  public IDemoCaseRunnable getSelectedDemoRunner() {
    return ((IDemoCaseRunnable) list.getSelectedValue());
  }

  public void cancelAllDemos() {
    final DefaultListModel model = getListModel();
    for (int i = 0; i < model.getSize(); ++i) {
      final IDemoCaseRunnable runner = (IDemoCaseRunnable) model.get(i);
      if (!runner.getState().equals(DemoState.FINISHED) && !runner.getState().equals(DemoState.CRASHED)) {
        runner.cancel();
      }
    }
  }

  public void cancelSelectedDemo() {
    final IDemoCaseRunnable demoRunner = getSelectedDemoRunner();
    if (demoRunner == null) {
      return;
    }
    demoRunner.cancel();
  }

  public void addDemo(final IDemoCaseRunnable runner) {
    getListModel().addElement(runner);
    runner.addDemoStateChangeListener(new IDemoStateChangeListener() {
      public void demoStateChanged(final IDemoStateChangeEvent event) {
        updateActionsEnabled();
        list.repaint();
      }
    });
    list.ensureIndexIsVisible(getListModel().getSize() - 1);
  }

  public int getSize() {
    return list.getModel().getSize();
  }

  public boolean isEmpty() {
    return getSize() == 0;
  }
}