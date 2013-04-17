package de.jdemo.extensions;

import de.jdemo.framework.AbstractDemoCase;
import de.jdemo.framework.IDemoCaseRunnable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Abstract superclass for all demos demonstrating SWT components.
 * 
 * @author Markus Gebhard
 */
public abstract class SwtDemoCase extends AbstractDemoCase implements ISwtDemoCase {
  private Shell shell;
  private Display display;

  @Override
  public IDemoCaseRunnable createRunnable(final boolean allowExternalLaunches) {
    final SwtDemoCaseRunnable runner = new SwtDemoCaseRunnable((SwtDemoCase) this.clone());
    return runner;
  }

  @Override
  public void executeTearDown() throws Exception {
    super.executeTearDown();
    disposeDisplay();
  }

  @Override
  protected Object clone() {
    //Ensure the display is not cloned. Would lead to invalid thread access
    //when creating a shell on cloned DemoCase
    final SwtDemoCase clone = (SwtDemoCase) super.clone();
    clone.display = null;
    clone.shell = null;
    return clone;
  }

  private void disposeDisplay() {
    if (display != null && !display.isDisposed()) {
      display.syncExec(new Runnable() {
        public void run() {
          display.close();
        }
      });
    }
  }

  @Override
  public void cancel() {
    super.cancel();
    disposeDisplay();
    exit();
  }

  protected Display getDisplay() {
    if (display == null) {
      display = new Display();
    }
    return display;
  }

  /**
   * Creates a new {@link Shell}object that can be used for adding widgets.
   * The shell will automatically be made visible when returning from the
   * <code>demo...</code> method. Not that calling this method more often than once will
   * result in a {@link de.jdemo.framework.exceptions.DemoExecutionFailedError}.
   */
  protected Shell createShell() {
    assertNull("Only one shell can be created - calling this method multiple times is not allowed.", shell);
    shell = new Shell(getDisplay());
    shell.setLayout(new FillLayout(SWT.VERTICAL));
    return shell;
  }

  private void show(final Shell shell) {
    shell.pack();
    shell.open();

    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    display.dispose();
  }

  @Override
  public void runDemo() throws Throwable {
    super.runDemo();
    if (shell != null) {
      show(shell);
    }
  }
}