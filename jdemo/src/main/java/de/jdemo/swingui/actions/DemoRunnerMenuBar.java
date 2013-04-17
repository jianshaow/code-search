package de.jdemo.swingui.actions;

import java.awt.Component;

import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import de.jdemo.swingui.DemoRunnerApplication;
import de.jdemo.swingui.model.DemoSelectionModel;
import de.jdemo.swingui.preferences.JDemoPreferences;
import de.jdemo.swingui.preferences.WindowConfigurationPreferences;
import de.jdemo.swingui.util.SmartAction;

/**
 * @author Markus Gebhard
 */
public class DemoRunnerMenuBar extends JMenuBar {
  private final DemoRunnerApplication runner;
  private final DemoSelectionModel selectionModel;

  public DemoRunnerMenuBar(DemoRunnerApplication runner, DemoSelectionModel selectionModel) {
    this.runner = runner;
    this.selectionModel = selectionModel;
    add(createFileMenu());
    add(createEditMenu());
    add(createViewMenu());
    add(createHelpMenu());
  }

  protected DemoRunnerApplication getRunner() {
    return runner;
  }

  private JMenu createFileMenu() {
    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic('F');
    fileMenu.add(new ManageLookAndFeelsAction());
    fileMenu.addSeparator();
    fileMenu.add(createExitAction());
    return fileMenu;
  }

  private JMenu createEditMenu() {
    JMenu editMenu = new JMenu("Edit");
    editMenu.setMnemonic('E');
    editMenu.add(new CopyDemoIdentifierAction(selectionModel));
    editMenu
        .add(new ShowSourceCodeAction(runner.getDemoRunnerPanel().getShowSourceCodeHandler(), selectionModel));
    editMenu.addSeparator();
    editMenu.add(createIncrementalFindAction());
    return editMenu;
  }

  private JMenu createViewMenu() {
    JMenu helpMenu = new JMenu("View");
    helpMenu.setMnemonic('V');
    JCheckBoxMenuItem checkBox = new JCheckBoxMenuItem(new SmartAction("Annotation panel") {
      protected void execute(Component parentComponent) {
        WindowConfigurationPreferences preferences = JDemoPreferences
            .getInstance().getWindowConfigurationPreferences();
        preferences.setAnnotationPanelVisible(!preferences.isAnnotationPanelVisible());
      }
    });
    checkBox.setSelected(JDemoPreferences
        .getInstance().getWindowConfigurationPreferences().isAnnotationPanelVisible());
    helpMenu.add(checkBox);
    return helpMenu;
  }

  private JMenu createHelpMenu() {
    JMenu helpMenu = new JMenu("Help");
    helpMenu.setMnemonic('H');
    helpMenu.add(createAboutAction());
    return helpMenu;
  }

  private Action createExitAction() {
    SmartAction action = new SmartAction("E&xit") {
      protected void execute(Component parentComponent) {
        getRunner().performExit();
      }
    };
    return action;
  }

  private Action createIncrementalFindAction() {
    return new SmartAction("Incremental find") {
      protected void execute(Component parentComponent) {
        getRunner().getDemoRunnerPanel().getIncrementalFind().startIncrementalFind();
      }
    };
  }

  private Action createAboutAction() {
    return new AboutAction();
  }
}