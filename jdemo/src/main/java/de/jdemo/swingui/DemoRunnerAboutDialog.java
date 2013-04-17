package de.jdemo.swingui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.jdemo.Version;
import de.jdemo.swingui.util.ButtonPanelBuilder;
import de.jdemo.swingui.util.HorizontalLine;
import de.jdemo.swingui.util.LayoutDirection;
import de.jdemo.swingui.util.SmartAction;
import de.jdemo.swingui.util.WebLinkLabel;
import de.jdemo.util.GuiUtilities;

/**
 * @author Markus Gebhard
 */
public class DemoRunnerAboutDialog {

  private final JDialog dialog;

  public DemoRunnerAboutDialog(final Component parentComponent) {
    dialog = GuiUtilities.createDialog(parentComponent);
    dialog.setTitle("JDemo");
    dialog.setModal(true);

    final JPanel topPanel = new JPanel(new BorderLayout(5, 5));
    topPanel.add(
        new JLabel("<html><center>" + "<b>JDemo - Java Demonstration Framework</b></html>"),
        BorderLayout.NORTH);
    topPanel.add(new HorizontalLine(), BorderLayout.SOUTH);

    final JPanel tablePanel = new JPanel(new GridLayout(0, 2, 5, 5));
    tablePanel.add(new JLabel("Version: "));
    tablePanel.add(new JLabel(Version.getFullVersionNumber()));
    tablePanel.add(new JLabel("Build date: "));
    tablePanel.add(new JLabel(Version.getBuildDate()));
    tablePanel.add(new JLabel("Web site:"));
    tablePanel.add(new WebLinkLabel("http://www.jdemo.de").getContent());

    final JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(tablePanel, BorderLayout.CENTER);
    mainPanel.add(new HorizontalLine(), BorderLayout.SOUTH);

    mainPanel.setBorder(new EmptyBorder(6, 6, 2, 6));

    final ButtonPanelBuilder builder = new ButtonPanelBuilder(LayoutDirection.HORIZONTAL);
    builder.add(new JButton(new SmartAction("OK") {
      @Override
      protected void execute(final Component parent) {
        dialog.dispose();
      }
    }));

    dialog.getContentPane().add(mainPanel, BorderLayout.CENTER);
    dialog.getContentPane().add(builder.createPanel(), BorderLayout.SOUTH);

    dialog.pack();
    GuiUtilities.centerOnScreen(dialog);
  }

  public static void show(final Component parentComponent) {
    new DemoRunnerAboutDialog(parentComponent).getDialog().setVisible(true);
  }

  public JDialog getDialog() {
    return dialog;
  }
}