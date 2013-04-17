package de.jdemo.swingui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.UIManager.LookAndFeelInfo;

import de.jdemo.extensions.SwingDemoCase;
import de.jdemo.extensions.SwingTextPanel;
import de.jdemo.framework.DemoCase;
import de.jdemo.framework.DemoIdentifier;
import de.jdemo.framework.IDemo;
import de.jdemo.framework.IDemoCase;
import de.jdemo.framework.IDemoCaseRunnable;
import de.jdemo.framework.ITextLauncher;
import de.jdemo.framework.exceptions.DemoClassNotFoundException;
import de.jdemo.framework.state.DemoState;
import de.jdemo.framework.state.IDemoStateChangeEvent;
import de.jdemo.framework.state.IDemoStateChangeListener;
import de.jdemo.framework.state.IDemoStateVisitor;
import de.jdemo.framework.util.DemoUtilities;
import de.jdemo.runner.IDemoShowSourceCodeHandler;
import de.jdemo.swingui.annotation.DemoAnnotationPanel;
import de.jdemo.swingui.find.IncrementalFind;
import de.jdemo.swingui.list.DemoExecutionList;
import de.jdemo.swingui.lookandfeel.LookAndFeelRegistry;
import de.jdemo.swingui.lookandfeel.LookAndFeelUtilities;
import de.jdemo.swingui.model.DemoSelectionModel;
import de.jdemo.swingui.preferences.JDemoPreferences;
import de.jdemo.swingui.tree.DemoTree;
import de.jdemo.swingui.util.IStatusIndicator;
import de.jdemo.util.FileLauncher;
import de.jdemo.util.GuiUtilities;
import de.jdemo.util.IFileLauncher;

/**
 * @author Markus Gebhard
 */
public class DemoRunnerPanel implements IDemoExecuter {

  private final List<IDemoStateChangeListener> demoStateChangeListeners = new ArrayList<IDemoStateChangeListener>();
  private final JComponent content;
  private JTextField tfStatus;
  private DemoTree demoTree;
  private DemoExecutionList executionList;
  private final IDemoShowSourceCodeHandler showSourceCodeHandler;
  private IncrementalFind incrementalFind;
  private final JSplitPane verticalSplitPane;
  private final JSplitPane horizontalSplitPane;
  private final IStatusIndicator statusIndicator;
  private final DemoSelectionModel selectionModel;
  private final JComponent treeComponent;
  private final JComponent descriptionComponent;

  public DemoRunnerPanel(
      final IDemo rootDemo,
      final DemoSelectionModel selectionModel,
      final IDemoShowSourceCodeHandler showSourceCodeHandler) {
    initializeDemoRunnerLookAndFeel();
    this.selectionModel = selectionModel;
    this.showSourceCodeHandler = showSourceCodeHandler;

    statusIndicator = new IStatusIndicator() {
      public void setStatus(final String statusText) {
        tfStatus.setText(statusText);
      }
    };

    treeComponent = createTreeComponent(rootDemo);
    final JComponent listComponent = createListComponent();
    descriptionComponent = cerateDescriptionComponent();

    verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, null, listComponent);
    verticalSplitPane.setBorder(null);
    verticalSplitPane.setContinuousLayout(true);
    verticalSplitPane.setResizeWeight(1.0);
    verticalSplitPane.setDividerLocation(treeComponent.getPreferredSize().height);

    horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, null, null);
    horizontalSplitPane.setBorder(null);
    horizontalSplitPane.setContinuousLayout(true);
    horizontalSplitPane.setOneTouchExpandable(true);

    updateAnnotationPanelVisibility();

    demoTree.selectFirstDemoCaseIfAny();
    setStatus(demoTree.getDemoCaseCount() + " DemoCase(s) loaded.");

    initIncrementalFind();

    this.content = verticalSplitPane;
  }

  public void updateAnnotationPanelVisibility() {
    final int dividerLocation = verticalSplitPane.getDividerLocation();
    if (JDemoPreferences.getInstance().getWindowConfigurationPreferences().isAnnotationPanelVisible()) {
      horizontalSplitPane.setLeftComponent(treeComponent);
      horizontalSplitPane.setRightComponent(descriptionComponent);
      verticalSplitPane.setTopComponent(horizontalSplitPane);
    }
    else {
      verticalSplitPane.setTopComponent(treeComponent);
    }
    verticalSplitPane.setDividerLocation(dividerLocation);
  }

  public static void initializeDemoRunnerLookAndFeel() {
    GuiUtilities.setNativeLookAndFeel();
  }

  public IncrementalFind getIncrementalFind() {
    return incrementalFind;
  }

  private void initIncrementalFind() {
    incrementalFind = getTree().attachIncrementalFind(new IncrementalDemoSearchable(this));
  }

  public JComponent getContent() {
    return content;
  }

  private JComponent createTreeComponent(final IDemo rootDemo) {
    demoTree = new DemoTree(rootDemo, this, showSourceCodeHandler, statusIndicator, selectionModel);
    final JScrollPane panel = new JScrollPane(demoTree.getComponent());
    return new FramePanel("Available demos:", panel).getContent();
  }

  private JComponent createListComponent() {
    tfStatus = new JTextField(""); //$NON-NLS-1$
    tfStatus.setEditable(false);
    tfStatus.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
    executionList = new DemoExecutionList(this, selectionModel);

    final FramePanel listPanel = new FramePanel("Demo execution states:", executionList.getComponent());

    final JPanel p = new JPanel();
    p.setLayout(new BorderLayout());
    p.add(tfStatus, BorderLayout.SOUTH);
    p.add(listPanel.getContent(), BorderLayout.CENTER);
    return p;
  }

  private JComponent cerateDescriptionComponent() {
    return new DemoAnnotationPanel(selectionModel).getContent();
  }

  public void cancelAllDemos() {
    executionList.cancelAllDemos();
  }

  public void setStatus(final String text) {
    statusIndicator.setStatus(text);
  }

  public static void showInfoDialog(final Component parentComponent) {
    DemoRunnerPanel.initializeDemoRunnerLookAndFeel();
    DemoRunnerAboutDialog.show(parentComponent);
  }

  public IDemoShowSourceCodeHandler getShowSourceCodeHandler() {
    return showSourceCodeHandler;
  }

  public DemoTree getTree() {
    return demoTree;
  }

  public static void showErrorDialog(
      final Component parentComponent,
      String title,
      String message,
      final Exception e) {
    if (title == null) {
      title = "Error";
    }

    if (e != null) {
      message = message + "\n" + e;
    }
    JOptionPane.showMessageDialog(parentComponent, message, title, JOptionPane.ERROR_MESSAGE);
  }

  public void executeDemo(final DemoIdentifier identifier, final String lookAndFeelClassName) {
    try {
      executeDemo(DemoUtilities.createDemo(identifier), lookAndFeelClassName);
    }
    catch (final DemoClassNotFoundException e) {
      showErrorDialog(content, "JDemo", "Demo class for '" + identifier + "' not found.", null);
    }
  }

  public void executeDemo(final IDemoCase demo, String lookAndFeelClassName) {
    if (demo instanceof SwingDemoCase) {
      if (lookAndFeelClassName == null) {
        LookAndFeelInfo defaultLookAndFeel = LookAndFeelRegistry.getInstance().getDefaultLookAndFeel();
        //Do not use the default if it is not available
        if (defaultLookAndFeel != null
            && !LookAndFeelUtilities.isAvailableLookAndFeelClass(defaultLookAndFeel.getClassName())) {
          defaultLookAndFeel = null;
        }
        lookAndFeelClassName = defaultLookAndFeel == null ? null : defaultLookAndFeel.getClassName();
      }
      ((SwingDemoCase) demo).setConfiguredLookAndFeelClassName(lookAndFeelClassName);
    }

    final IDemoCaseRunnable runner = demo.createRunnable(true);
    final IDemoCase runningDemo = runner.getDemo();
    if (runningDemo instanceof DemoCase) {
      final DemoCase demoCase = (DemoCase) runningDemo;
      demoCase.setFileLauncher(new IFileLauncher() {
        public void launch(final File file) throws Exception {
          WaitDialog.showWaitDialog(content, "Launching file...");
          new FileLauncher().launch(file);
        }
      });
      demoCase.setTextLauncher(new ITextLauncher() {
        public void launch(final CharSequence text) {
          final JComponent textComponent = new SwingTextPanel(text).getContent();
          final JDialog dialog = GuiUtilities.createDialog(content);
          dialog.setModal(true);
          dialog.setTitle("Text output");
          dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
          dialog.getContentPane().setLayout(new BorderLayout());
          dialog.getContentPane().add(textComponent, BorderLayout.CENTER);
          dialog.pack();
          GuiUtilities.centerOnScreen(dialog);
          dialog.setVisible(true);
        }
      });
    }

    runner.addDemoStateChangeListener(new IDemoStateChangeListener() {
      public void demoStateChanged(final IDemoStateChangeEvent event) {
        final String demoName = DemoUtilities.getDisplayName(demo);
        final DemoState state = event.getNewState();
        state.accept(new IDemoStateVisitor() {
          public void visitFinished(final DemoState state) {
            setStatus("Demo '" + demoName + "' finished.");
          }

          public void visitRunning(final DemoState state) {
            setStatus("Demo '" + demoName + "' running...");
          }

          public void visitInitial(final DemoState state) {
            //nothing to do
          }

          public void visitCrashed(final DemoState state) {
            setStatus("Demo '" + demoName + "' crashed.");
          }

          public void visitStarting(final DemoState state) {
            setStatus("Starting demo '" + demoName + "'..");
          }
        });
        fireDemoStateChangedEvent(event);
      }
    });

    executionList.addDemo(runner);
    SwingUtilities.invokeLater(runner);
  }

  public void addDemoStateChangeListener(final IDemoStateChangeListener listener) {
    demoStateChangeListeners.add(listener);
  }

  public void removeDemoStateChangeListener(final IDemoStateChangeListener listener) {
    demoStateChangeListeners.remove(listener);
  }

  protected void fireDemoStateChangedEvent(final IDemoStateChangeEvent event) {
    final ArrayList clonedListeners = new ArrayList(demoStateChangeListeners);
    for (int i = 0; i < clonedListeners.size(); i++) {
      final IDemoStateChangeListener listener = (IDemoStateChangeListener) clonedListeners.get(i);
      listener.demoStateChanged(event);
    }
  }

  public void requestFocus() {
    getTree().getComponent().requestFocus();
  }

  public void setVerticalDividerLocation(final int location) {
    verticalSplitPane.setDividerLocation(location);
  }

  public int getVerticalDividerLocation() {
    return verticalSplitPane.getDividerLocation();
  }

  public void setHorizontalDividerLocation(final int location) {
    horizontalSplitPane.setDividerLocation(location);
  }

  public int getHorizontalDividerLocation() {
    return horizontalSplitPane.getDividerLocation();
  }
}