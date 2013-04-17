package de.jdemo.swingui.util.test;

import java.awt.Component;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import de.jdemo.swingui.util.SmartAction;
import junit.framework.TestCase;

/**
 * @author Markus Gebhard
 */
//TODO NOW 30.12.2004 (Markus Gebhard): Move to Disy_Commons_Swing
public class SmartActionTest extends TestCase {

  private SmartAction action;

  protected void setUp() throws Exception {
    action = new SmartAction("test") { //$NON-NLS-1$
      protected void execute(Component parentComponent) {
          //does nothing
  }
    };
  }

  public void testCreation() {
    assertEquals("test", action.getValue(Action.NAME)); //$NON-NLS-1$
    assertEquals("test", action.getName()); //$NON-NLS-1$
    assertTrue(action.isEnabled());
    assertNull(action.getValue(Action.MNEMONIC_KEY));
  }

  public void testSetMnemonic() {
    action.setMnemonic(KeyEvent.VK_A);
    assertEquals(new Integer(KeyEvent.VK_A), action.getValue(Action.MNEMONIC_KEY));
  }

  public void testSetMnemonicCharacter() {
    assertSetMnemonicCharacter(KeyEvent.VK_A, 'a');
    assertSetMnemonicCharacter(KeyEvent.VK_U, 'U');
    assertSetMnemonicCharacter(KeyEvent.VK_1, '1');
  }

  public void testImplementationPreconditions() {
    //if they fail we have to change the mapping from char to KeyEvent.VK_...
    assertEquals(KeyEvent.VK_0, '0');
    assertEquals(KeyEvent.VK_9, '9');
    assertEquals(KeyEvent.VK_A, 'A');
    assertEquals(KeyEvent.VK_Z, 'Z');
  }

  private void assertSetMnemonicCharacter(int expectedKeyCode, char character) {
    action.setMnemonic(character);
    assertEquals(new Integer(expectedKeyCode), action.getValue(Action.MNEMONIC_KEY));
  }

  public void testSetMnemonicInsideName() {
    assertSetNameSetsMnemonic("name", KeyEvent.VK_N, "&name"); //$NON-NLS-1$ //$NON-NLS-2$
    assertSetNameSetsMnemonic("name", KeyEvent.VK_A, "n&ame"); //$NON-NLS-1$ //$NON-NLS-2$
    assertSetNameSetsMnemonic("name", KeyEvent.VK_M, "na&me"); //$NON-NLS-1$ //$NON-NLS-2$
    assertSetNameSetsMnemonic("name", KeyEvent.VK_E, "nam&e"); //$NON-NLS-1$ //$NON-NLS-2$
  }

  public void testSetNameWithoutMnemonicInsideName() {
    assertSetNameWithoutMnemonic("n&ame", "n&&ame"); //$NON-NLS-1$ //$NON-NLS-2$
    assertSetNameWithoutMnemonic("&name", "&&name"); //$NON-NLS-1$ //$NON-NLS-2$
    assertSetNameWithoutMnemonic("nam&&e", "nam&&&&e"); //$NON-NLS-1$ //$NON-NLS-2$
    assertSetNameWithoutMnemonic("name&", "name&&"); //$NON-NLS-1$ //$NON-NLS-2$
  }

  public void testSetMnemonicInsideNameWithEscapedAmpersands() {
    assertSetNameSetsMnemonic("nam&e", KeyEvent.VK_E, "nam&&&e"); //$NON-NLS-1$ //$NON-NLS-2$
  }

  private void assertSetNameWithoutMnemonic(String expectedName, String nameWithoutMnemonic) {
    action.setName(nameWithoutMnemonic);
    assertEquals(expectedName, action.getName());
    assertNull(action.getValue(Action.MNEMONIC_KEY));
  }

  private void assertSetNameSetsMnemonic(String expectedName, int expectedKeyCode, String nameWithMnemonic) {
    action.setName(nameWithMnemonic);
    assertEquals(new Integer(expectedKeyCode), action.getValue(Action.MNEMONIC_KEY));
    assertEquals(expectedName, action.getName());
  }
}