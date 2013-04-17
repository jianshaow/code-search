package de.jdemo.swingui.util;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;

import de.jdemo.util.GuiUtilities;

/**
 * @author Markus Gebhard
 */
//TODO NOW 30.12.2004 (Markus Gebhard): Move to Disy_Commons_Swing
public abstract class SmartAction extends AbstractAction {

  private Component explicitParentComponent;

  public SmartAction() {
    this(null, null);
  }

  public SmartAction(final String name) {
    this(name, null);
  }

  public SmartAction(final String name, final Icon icon) {
    if (name != null) {
      setName(name);
    }
    setIcon(icon);
  }

  /** For Tree/Table/...CellEditors the parentComponent cannot be determined from the ActionEvent.
   * So you can set an explicit parentComponent here. */
  public void setExplicitParentComponent(final Component explicitParentComponent) {
    this.explicitParentComponent = explicitParentComponent;
  }

  /**
   * Sets the name of the action - may include the mnemonic character but must
   * not contain line delimiters. Mnemonics are indicated by an '&' that causes
   * the next character to be the mnemonic. When the user presses a key
   * sequence that matches the mnemonic, a selection event occurs. On most
   * platforms, the mnemonic appears underlined but may be emphasised in a
   * platform specific manner. The mnemonic indicator character '&' can be
   * escaped by doubling it in the string, causing a single '&' to be
   * displayed.
   */
  public void setName(String name) {
    int index = -1;
    boolean found = false;
    do {
      ++index;
      index = name.indexOf('&', index);
      if (index != -1 && index + 1 < name.length()) {
        if (name.charAt(index + 1) == '&') {
          name = name.substring(0, index) + name.substring(index + 1);
        }
        else {
          found = true;
          break;
        }
      }
    }
    while (index != -1 && index + 1 < name.length());
    if (found) {
      final char mnemonic = name.charAt(index + 1);
      setMnemonic(mnemonic);
      name = name.substring(0, index) + name.substring(index + 1);
    }
    putValue(Action.NAME, name);
  }

  public final void actionPerformed(final ActionEvent e) {
    final Component parentComponent = explicitParentComponent == null
        ? GuiUtilities.getWindowForComponent(e)
        : explicitParentComponent;
    execute(parentComponent);
  }

  /** Called from the action when being invoked by the user. The given parentComponeent can
   * be used as parent for any dialogs shown in this method.
   * 
   * @param parentComponent A parent component from which the action was invoked. Can be used
   * as parent for new dialogs.
   */
  protected abstract void execute(Component parentComponent);

  public void setMnemonic(final int keyCode) {
    putValue(MNEMONIC_KEY, new Integer(keyCode));
  }

  public void setMnemonic(final char character) {
    final char ch = Character.toUpperCase(character);
    if (!isLetter(ch) && !isDigit(ch)) {
      throw new IllegalArgumentException("Unsupported mnemonic character'" + character + "'."); //$NON-NLS-1$ //$NON-NLS-2$
    }
    setMnemonic((int) ch);
  }

  public void setAcceleratorKey(final KeyStroke keyStroke) {
    putValue(ACCELERATOR_KEY, keyStroke);
  }

  private static boolean isDigit(final char ch) {
    return ch >= '0' && ch <= '9';
  }

  private static boolean isLetter(final char ch) {
    return ch >= 'A' && ch <= 'Z';
  }

  public String getName() {
    return (String) getValue(Action.NAME);
  }

  public void setIcon(final Icon icon) {
    putValue(SMALL_ICON, icon);
  }
}