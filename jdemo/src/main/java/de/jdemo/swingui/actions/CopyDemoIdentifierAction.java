package de.jdemo.swingui.actions;

import java.awt.Component;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import de.jdemo.swingui.icons.JDemoIcons;
import de.jdemo.swingui.model.DemoSelectionModel;

public class CopyDemoIdentifierAction extends AbstractDemoCaseAction {

  public CopyDemoIdentifierAction(DemoSelectionModel selectionModel) {
    super("Copy demo identifier", JDemoIcons.getIconResource("copy.gif"), selectionModel); //$NON-NLS-2$
    setAcceleratorKey(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
  }

  protected void execute(Component parentComponent) {
    StringSelection selection = new StringSelection(getSelectedDemoIdentifier().toString());
    Clipboard clipboard = parentComponent.getToolkit().getSystemClipboard();
    clipboard.setContents(selection, new ClipboardOwner() {
      public void lostOwnership(Clipboard theClipboard, Transferable contents) {
        //nothing to do
      }
    });
  }
}