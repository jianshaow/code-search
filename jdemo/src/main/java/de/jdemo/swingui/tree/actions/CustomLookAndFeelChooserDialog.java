package de.jdemo.swingui.tree.actions;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.jdemo.swingui.lookandfeel.LookAndFeelUtilities;
import de.jdemo.swingui.util.AbstractUserDialogPage;
import de.jdemo.swingui.util.Message;

/**
 * @author Markus Gebhard
 */
public class CustomLookAndFeelChooserDialog extends AbstractUserDialogPage {

  private static final Message DEFAULT_MESSAGE = new Message("Specify the new Look&Feel.");
  private JTextField tfClassName;
  private JTextField tfName;
  private JCheckBox cbRemember;
  private final boolean mustRemember;

  public CustomLookAndFeelChooserDialog(final boolean mustRemember) {
    this.mustRemember = mustRemember;
  }

  public JComponent createContent() {
    tfClassName = new JTextField(30);
    tfClassName.setFont(new Font("Monospaced", Font.PLAIN, 11)); //$NON-NLS-1$
    tfName = new JTextField(20);
    final JLabel classNameLabel = new JLabel("Class name:");
    final JLabel nameLabel = new JLabel("Name:");
    cbRemember = new JCheckBox("Remember", true);

    if (mustRemember) {
      cbRemember.setEnabled(false);
    }

    final DocumentListener documentListener = new DocumentListener() {
      public void changedUpdate(final DocumentEvent e) {
        fireChangeEvent();
      }

      public void insertUpdate(final DocumentEvent e) {
        fireChangeEvent();
      }

      public void removeUpdate(final DocumentEvent e) {
        fireChangeEvent();
      }
    };
    tfClassName.getDocument().addDocumentListener(documentListener);
    tfName.getDocument().addDocumentListener(documentListener);

    cbRemember.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent e) {
        tfName.setEnabled(cbRemember.isSelected());
        nameLabel.setEnabled(cbRemember.isSelected());
        fireChangeEvent();
      }
    });

    final JPanel panel1 = new JPanel(new BorderLayout(5, 5));
    panel1.add(classNameLabel, BorderLayout.WEST);
    panel1.add(tfClassName, BorderLayout.CENTER);

    final JPanel panel2 = new JPanel(new BorderLayout(5, 5));
    panel2.add(nameLabel, BorderLayout.WEST);
    panel2.add(tfName, BorderLayout.CENTER);

    final JPanel panel3 = new JPanel(new BorderLayout(5, 5));
    panel3.add(cbRemember, BorderLayout.WEST);

    final JPanel mainPanel = new JPanel(new GridLayout(0, 1, 5, 5));
    mainPanel.add(panel1);
    mainPanel.add(panel2);
    mainPanel.add(panel3);
    return mainPanel;
  }

  public CustomLookAndFeelOptions getSelectedOptions() {
    final CustomLookAndFeelOptions selectedOptions = new CustomLookAndFeelOptions(
        getSelectedClassName(),
        cbRemember.isSelected(),
        getSelectedName());
    return selectedOptions;
  }

  public void performOk() {
    //nothing to do
  }

  public void performCancel() {
    //nothing to do
  }

  public Message createCurrentMessage() {
    final String className = getSelectedClassName();
    if (className.length() == 0) {
      return new Message("The specified class name is empty.", true);
      //" Please specify the name of Look&Feel class.", true);
    }
    if (!LookAndFeelUtilities.isClassAvailable(className)) {
      return new Message("The specified class can not be found on the classpath.", true);
    }
    if (!LookAndFeelUtilities.isLookAndFeelClass(className)) {
      return new Message("The specified class does not extend the Look&Feel base class.", true);
    }
    if (cbRemember.isSelected() && getSelectedName().length() == 0) {
      //name may not be empty
      return new Message("You must specify a name in order to remember the Look&Feel.", true);
    }
    return getDefaultMessage();
  }

  private String getSelectedName() {
    return tfName.getText().trim();
  }

  private String getSelectedClassName() {
    return tfClassName.getText().trim();
  }

  public Message getDefaultMessage() {
    return DEFAULT_MESSAGE;
  }

  public String getDescription() {
    return getTitle();
  }

  public String getTitle() {
    return "Custom Look&Feel";
  }

  public void requestFocus() {
    tfClassName.requestFocus();
  }
}