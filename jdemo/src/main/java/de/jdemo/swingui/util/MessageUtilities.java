package de.jdemo.swingui.util;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * @author Markus Gebhard
 */
public class MessageUtilities {

  public static void showErrorMessageDialog(Component parentComponent, String title, String messageText) {
    JOptionPane.showMessageDialog(parentComponent, messageText, title, JOptionPane.ERROR_MESSAGE);
  }

}
