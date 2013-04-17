package de.jdemo.swingui.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.jdemo.util.GuiUtilities;

/**
 * @author Markus Gebhard
 */
public class UserDialog {

  private final JDialog dialog;
  private JButton okButton;
  private boolean isCancelled = false;
  private final IUserDialogPage dialogOage;
  private final JTextArea messageLabel;

  public UserDialog(final Component parentComponent, final IUserDialogPage dialogPage) {
    this.dialogOage = dialogPage;
    dialog = GuiUtilities.createDialog(parentComponent);
    dialog.setModal(true);
    dialog.setTitle(dialogPage.getTitle());
    dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

    dialog.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(final WindowEvent e) {
        performCancel();
      }
    });

    final JLabel descriptionLabel = new JLabel(dialogPage.getDescription());
    descriptionLabel.setFont(new Font("Dialog", Font.BOLD, 11)); //$NON-NLS-1$

    //TODO Oct 30, 2004 (Markus Gebhard): Use AutoWrappingLabel when available
    messageLabel = new JTextArea(" ");
    messageLabel.setEditable(false);
    messageLabel.setFont(new Font("Dialog", Font.PLAIN, 11)); //$NON-NLS-1$
    messageLabel.setOpaque(false);

    final JPanel headerPanel = new JPanel(new BorderLayout(5, 5));
    headerPanel.setBackground(Color.WHITE);
    headerPanel.add(descriptionLabel, BorderLayout.NORTH);
    final JLabel smallMessageIconLabel = new JLabel();
    smallMessageIconLabel.setPreferredSize(new Dimension(16, 25));
    headerPanel.add(smallMessageIconLabel, BorderLayout.WEST);
    headerPanel.add(messageLabel, BorderLayout.CENTER);

    final JPanel mainPanel = new JPanel(new GridLayout(1, 0));
    mainPanel.add(dialogPage.createContent());
    mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

    final JPanel buttonPanel = createButtonPanel();

    final JPanel framedMainPanel = new JPanel(new BorderLayout(0, 0));
    framedMainPanel.add(new HorizontalLine(), BorderLayout.NORTH);
    framedMainPanel.add(mainPanel, BorderLayout.CENTER);
    framedMainPanel.add(new HorizontalLine(), BorderLayout.SOUTH);

    final Container contentPane = dialog.getContentPane();
    contentPane.setLayout(new BorderLayout(0, 0));
    contentPane.add(headerPanel, BorderLayout.NORTH);
    contentPane.add(framedMainPanel, BorderLayout.CENTER);
    contentPane.add(buttonPanel, BorderLayout.SOUTH);
    dialog.getRootPane().setDefaultButton(okButton);
    dialog.pack();

    dialogPage.addChangeListener(new ChangeListener() {
      public void stateChanged(final ChangeEvent e) {
        updateMessageLabel();
        updateOkButtonEnabled();
      }
    });

    updateOkButtonEnabled();
    setMessage(dialogPage.getDefaultMessage());
    dialogPage.requestFocus();
  }

  private void updateOkButtonEnabled() {
    okButton.setEnabled(!dialogOage.createCurrentMessage().isError());
  }

  private JPanel createButtonPanel() {
    okButton = new JButton(new SmartAction("OK") {
      @Override
      protected void execute(final Component parentComponent) {
        dialogOage.performOk();
        dialog.dispose();
      }
    });
    final JButton cancelButton = new JButton(new SmartAction("Cancel") {
      @Override
      protected void execute(final Component parentComponent) {
        performCancel();
      }
    });

    final ButtonPanelBuilder builder = new ButtonPanelBuilder(LayoutDirection.HORIZONTAL);
    builder.add(okButton);
    builder.add(cancelButton);
    return builder.createPanel();
  }

  private void updateMessageLabel() {
    setMessage(dialogOage.createCurrentMessage());
  }

  private void setMessage(final Message message) {
    if (message.isError()) {
      messageLabel.setForeground(Color.RED);
    }
    else {
      messageLabel.setForeground(Color.BLACK);
    }
    messageLabel.setText(message.getText());
  }

  public JDialog getDialog() {
    return dialog;
  }

  public void show() {
    GuiUtilities.centerOnScreen(dialog);
    dialog.setVisible(true);
  }

  public boolean isCancelled() {
    return isCancelled;
  }

  private void performCancel() {
    dialogOage.performCancel();
    isCancelled = true;
    dialog.dispose();
  }
}