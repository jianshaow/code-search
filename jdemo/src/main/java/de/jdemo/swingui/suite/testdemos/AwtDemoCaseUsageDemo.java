package de.jdemo.swingui.suite.testdemos;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.jdemo.extensions.AwtDemoCase;

/**
 * This demo class provides simple example implementations for AWT demo cases.
 * Subclasses of {@link de.jdemo.extensions.AwtDemoCase} are graphical demos using the AWT.
 * @author Markus Gebhard
 */
public class AwtDemoCaseUsageDemo extends AwtDemoCase {

  /** Main method for starting the runner - can be omitted, because you can specify this demo case
   * (or a demo suite containing this demo) when starting the demo runner. */
  public static void main(String[] args) {
    de.jdemo.swingui.DemoRunner.run(AwtDemoCaseUsageDemo.class);
  }

  /** Demo showing a simple Component object using the {@link AwtDemoCase#show(Component)} method. */
  public void demoComponent() {
    show(createExampleComponent());
  }

  private Component createExampleComponent() {
    Panel panel = new Panel();
    panel.setLayout(new BorderLayout());
    panel.add(new Label("Component as demo"), BorderLayout.NORTH); //$NON-NLS-1$
    String[] listItems = new String[] { "One", "Two", "Three" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    List list = new List();
    for (int i = 0; i < listItems.length; ++i) {
      list.add(listItems[i]);
    }
    panel.add(list, BorderLayout.CENTER);
    return panel;
  }

  /** Demo showing a simple Frame object using the {@link AwtDemoCase#show(Window)} method. 
   * Note that you do not have to care about attaching window listeners etc. */
  public void demoFrame() {
    show(createExampleFrame());
  }

  private Frame createExampleFrame() {
    final Frame frame = new Frame("Frame as demo"); //$NON-NLS-1$
    frame.setLayout(new BorderLayout());
    frame.add(new Label("Frame as demo"), BorderLayout.NORTH); //$NON-NLS-1$

    String[] listItems = new String[] { "One", "Two", "Three" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    List list = new List();
    for (int i = 0; i < listItems.length; ++i) {
      list.add(listItems[i]);
    }
    frame.add(list, BorderLayout.CENTER);

    Button button = new Button("Ok"); //$NON-NLS-1$
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
      }
    });
    frame.add(button, BorderLayout.SOUTH);

    return frame;
  }

  /** Demo showing a simple Dialog. The AwtDemoCase implementation provides a parent frame. */
  public void demoDialogModalWithParent() {
    Frame parent = createFrame();
    Dialog dialog = createExampleDialog(parent);
    dialog.setModal(true);
    show(dialog);
  }

  /** Demo showing a simple Dialog. The AwtDemoCase implementation provides a parent frame. */
  public void demoDialogNonModalWithParent() {
    Frame parent = createFrame();
    Dialog dialog = createExampleDialog(parent);
    dialog.setModal(false);
    show(dialog);
  }

  private Dialog createExampleDialog(Frame parent) {
    final Dialog dialog = new Dialog(parent);
    dialog.setModal(false);
    Button button = new Button("Ok"); //$NON-NLS-1$
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dialog.dispose();
      }
    });
    dialog.setLayout(new BorderLayout());
    dialog.add(button, BorderLayout.SOUTH);
    return dialog;
  }

  /** Demo showing a simple Window object. The AwtDemoCase implementation provides a parent frame. */
  public void demoWindowWithParent() {
    Frame parent = createFrame();
    Window window = createExampleWindow(parent);
    show(window);
  }

  private Window createExampleWindow(Frame parent) {
    final Window window = new Window(parent);
    window.setLayout(new BorderLayout());
    window.add(new Label("Window as Demo"), BorderLayout.NORTH); //$NON-NLS-1$
    Button button = new Button("Ok"); //$NON-NLS-1$
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        window.dispose();
      }
    });
    window.add(button, BorderLayout.SOUTH);
    return window;
  }

  /** This demo method is intended to show how the demo runner behaves when there is an exception
   * inside a demo. */
  public void demoCrashInsideTheDemo(){
    throw new RuntimeException("RuntimeException inside AWT demo."); //$NON-NLS-1$
  }
}