package de.jdemo.swingui.lookandfeel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.jdemo.swingui.tree.actions.CustomLookAndFeelChooserDialog;
import de.jdemo.swingui.tree.actions.CustomLookAndFeelOptions;
import de.jdemo.swingui.util.AbstractUserDialogPage;
import de.jdemo.swingui.util.ButtonPanelBuilder;
import de.jdemo.swingui.util.LayoutDirection;
import de.jdemo.swingui.util.Message;
import de.jdemo.swingui.util.SmartAction;
import de.jdemo.swingui.util.UserDialog;

/**
 * @author Markus Gebhard
 */
public class LookAndFeelManagerDialogPage extends AbstractUserDialogPage {

  private static final Message DEFAULT_MESSAGE = new Message("Manage available Look&Feels.");
  private final LookAndFeelManagerDialogModel model;

  public static void show(final Component parentComponent) {
    final UserDialog dialog = new UserDialog(parentComponent, new LookAndFeelManagerDialogPage(
        LookAndFeelRegistry.getInstance()));
    dialog.show();
  }

  public LookAndFeelManagerDialogPage(final LookAndFeelRegistry registry) {
    model = new LookAndFeelManagerDialogModel(registry);
  }

  public String getTitle() {
    return "Manage Look&Feels";
  }

  public String getDescription() {
    return getTitle();
  }

  public void performOk() {
    model.saveInRegistry();
  }

  public void performCancel() {
    //nothing to do
  }

  public JComponent createContent() {
    final JTable table = new JTable(new LookAndFeelTableModel(model));
    table.getColumnModel().getColumn(0).setPreferredWidth(100);
    table.getColumnModel().getColumn(1).setPreferredWidth(80);
    table.getColumnModel().getColumn(2).setPreferredWidth(200);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    final SmartAction removeAction = new SmartAction("Remove") {
      @Override
      protected void execute(final Component parentComponent) {
        model.removeLookAndFeelAt(table.getSelectedRow());
      }
    };
    final SmartAction addAction = new SmartAction("Add...") {
      @Override
      protected void execute(final Component parentComponent) {
        final CustomLookAndFeelChooserDialog customLookAndFeelChooserDialog = new CustomLookAndFeelChooserDialog(
            true);
        final UserDialog userDialog = new UserDialog(parentComponent, customLookAndFeelChooserDialog);
        userDialog.show();
        if (!userDialog.isCancelled()) {
          final CustomLookAndFeelOptions selectedOptions = customLookAndFeelChooserDialog.getSelectedOptions();
          final String className = selectedOptions.getLookAndFeelClassName();
          final String name = selectedOptions.getName();
          model.addCustomLookAndFeel(new LookAndFeelInfo(name, className));
        }
      }
    };
    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(final ListSelectionEvent e) {
        updateRemoveActionEnabled(table, removeAction);
      }
    });
    final JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(300, 140));

    final ButtonPanelBuilder builder = new ButtonPanelBuilder(LayoutDirection.VERTICAL);
    builder.add(new JButton(addAction));
    builder.add(new JButton(removeAction));
    final JPanel buttonPanel = builder.createPanel();
    buttonPanel.setBorder(null);

    final JPanel tablePanel = new JPanel(new BorderLayout(5, 5));
    tablePanel.add(new JLabel("Installed Look&Feels:"), BorderLayout.NORTH);
    tablePanel.add(scrollPane, BorderLayout.CENTER);
    tablePanel.add(buttonPanel, BorderLayout.EAST);

    updateRemoveActionEnabled(table, removeAction);

    final JPanel defaultPanel = new JPanel(new BorderLayout(5, 5));
    defaultPanel.add(new JLabel("Default:"), BorderLayout.WEST);
    defaultPanel.add(new DefaultLookAndFeelComboBox(model).getContent(), BorderLayout.CENTER);

    final JPanel panel = new JPanel(new BorderLayout(6, 6));
    panel.add(tablePanel, BorderLayout.CENTER);
    panel.add(defaultPanel, BorderLayout.SOUTH);
    return panel;
  }

  private void updateRemoveActionEnabled(final JTable table, final SmartAction removeAction) {
    removeAction.setEnabled(!table.getSelectionModel().isSelectionEmpty()
        && !model.isSystemLookAndFeelIndex(table.getSelectedRow()));
  }

  public Message getDefaultMessage() {
    return DEFAULT_MESSAGE;
  }

  public Message createCurrentMessage() {
    return getDefaultMessage();
  }

  public void requestFocus() {
    //nothing to do
  }
}