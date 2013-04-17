package de.jdemo.swingui.util;

import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

/**
 * @author Markus Gebhard
 */
public interface IUserDialogPage {

  public String getTitle();

  public String getDescription();

  public void performOk();

  public void performCancel();

  public JComponent createContent();

  public Message getDefaultMessage();

  public Message createCurrentMessage();

  public void addChangeListener(ChangeListener listener);

  public void removeChangeListener(ChangeListener listener);

  public void requestFocus();


}
