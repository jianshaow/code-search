package de.jdemo.swingui.tree.actions.demo;

import de.jdemo.extensions.SwingDemoCase;
import de.jdemo.swingui.tree.actions.CustomLookAndFeelChooserDialog;
import de.jdemo.swingui.util.UserDialog;

/**
 * @author Markus Gebhard
 */
public class CustomLookAndFeelChooserDialogDemo extends SwingDemoCase {

  public void demo() {
    UserDialog userDialog = new UserDialog(createParentComponent(), new CustomLookAndFeelChooserDialog(false));
    show(userDialog.getDialog());
  }

  public void demoMustRemember() {
    UserDialog userDialog = new UserDialog(createParentComponent(), new CustomLookAndFeelChooserDialog(true));
    show(userDialog.getDialog());
  }

}