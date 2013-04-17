package de.jdemo.swingui;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.jdemo.Version;
import de.jdemo.framework.IDemo;
import de.jdemo.framework.state.IDemoStateChangeEvent;
import de.jdemo.framework.state.IDemoStateChangeListener;
import de.jdemo.runner.IDemoRunnerExitListener;
import de.jdemo.runner.IDemoShowSourceCodeHandler;
import de.jdemo.runner.path.ISourcePath;
import de.jdemo.swingui.actions.DemoRunnerMenuBar;
import de.jdemo.swingui.icons.JDemoIcons;
import de.jdemo.swingui.model.DemoSelectionModel;
import de.jdemo.swingui.preferences.JDemoPreferences;
import de.jdemo.swingui.preferences.WindowConfigurationPreferences;
import de.jdemo.swingui.showsource.Java2HtmlShowSourceCodeHandler;

/**
 * @author Markus Gebhard
 */
public class DemoRunnerApplication {
  private final List<IDemoRunnerExitListener> exitListeners = new ArrayList<IDemoRunnerExitListener>();

  private final JFrame frame;
  private File currentDirectory;
  private final DemoRunnerPanel panel;
  private boolean systemExitsOnClose = true;

  public DemoRunnerApplication() {
    this((IDemo) null, null);
  }

  public DemoRunnerApplication(final IDemo demo) {
    this(demo, null);
  }

  public DemoRunnerApplication(final IDemo demo, IDemoShowSourceCodeHandler showSourceCodeHandler) {
    frame = new JFrame("JDemo " + Version.getFullVersionNumber());
    if (showSourceCodeHandler == null) {
      showSourceCodeHandler = Java2HtmlShowSourceCodeHandler.createIfAvailable(frame);
    }

    final DemoSelectionModel selectionModel = new DemoSelectionModel();
    panel = new DemoRunnerPanel(demo, selectionModel, showSourceCodeHandler);

    panel.addDemoStateChangeListener(new IDemoStateChangeListener() {
      public void demoStateChanged(final IDemoStateChangeEvent event) {
        if (event.getNewState().isTerminated()) {
          frame.toFront();
        }
      }
    });

    frame.setIconImage(JDemoIcons.getImage(JDemoIcons.JDEMO));
    frame.getContentPane().setLayout(new GridLayout(1, 0));
    frame.getContentPane().add(panel.getContent());//, BorderLayout.CENTER);
    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(final WindowEvent e) {
        performExit();
      }
    });
    final WindowConfigurationPreferences preferences = JDemoPreferences
        .getInstance().getWindowConfigurationPreferences();
    frame.setSize(preferences.getWindowSize());
    frame.setLocation(preferences.getWindowLocation());
    panel.setVerticalDividerLocation(preferences.getVerticalDividerLocation());
    panel.setHorizontalDividerLocation(preferences.getHorizontalDividerLocation());
    initMenuBar(selectionModel);

    preferences.addAnnotationPanelVisibilityChangeListener(new ChangeListener() {
      public void stateChanged(final ChangeEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            frame.setSize(preferences.getWindowSize().width, frame.getHeight());
            panel.updateAnnotationPanelVisibility();
            frame.validate();
          }
        });
      }
    });
  }

  private void initMenuBar(final DemoSelectionModel selectionModel) {
    frame.setJMenuBar(new DemoRunnerMenuBar(this, selectionModel));
  }

  public void performExit() {
    try {
      final WindowConfigurationPreferences preferences = JDemoPreferences
          .getInstance().getWindowConfigurationPreferences();
      preferences.setWindowSize(frame.getSize());
      preferences.setWindowLocation(frame.getLocation());
      preferences.setVerticalDividerLocation(panel.getVerticalDividerLocation());
      preferences.setHorizontalDividerLocation(panel.getHorizontalDividerLocation());
      preferences.flush();
    }
    catch (final Exception e) {
      System.err.println("Error writing preferences."); //$NON-NLS-1$
      e.printStackTrace();
    }
    frame.dispose();
    panel.cancelAllDemos();
    fireDemoRunnerExited();
    if (systemExitsOnClose) {
      System.exit(0);
    }
  }

  private void fireDemoRunnerExited() {
    for (int i = 0; i < exitListeners.size(); ++i) {
      (exitListeners.get(i)).demoRunnerExited();
    }
  }

  public void show() {
    frame.setVisible(true);
  }

  public void addDemoRunnerExitListener(final IDemoRunnerExitListener listener) {
    exitListeners.add(listener);
  }

  public JFrame getFrame() {
    return frame;
  }

  public void setCurrentDirectory(final File directory) {
    this.currentDirectory = directory;
  }

  public File getCurrentDirectory() {
    return currentDirectory;
  }

  public void setSourcePath(final ISourcePath sourcePath) {
    if (panel.getShowSourceCodeHandler() != null) {
      panel.getShowSourceCodeHandler().setSourcePath(sourcePath);
    }
  }

  public DemoRunnerPanel getDemoRunnerPanel() {
    return panel;
  }

  public void setSystemExitsOnClose(final boolean systemExitsOnClose) {
    this.systemExitsOnClose = systemExitsOnClose;
  }

  public void setDividerLocation(final int location) {
    panel.setVerticalDividerLocation(location);
  }
}