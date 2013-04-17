package de.jdemo.swingui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

import de.jdemo.util.GuiUtilities;

/**
 * @author Markus Gebhard
 */
public class WaitDialog {

  public static void showWaitDialog(Component parentComponent, String message) {
    JPanel panel = new JPanel(new BorderLayout(6, 6));
    panel.add(new JLabel(message), BorderLayout.CENTER);
    JProgressBar progressBar = new JProgressBar();
    progressBar.setIndeterminate(true);
    panel.add(progressBar, BorderLayout.SOUTH);
    panel.setBorder(new EtchedBorder());

    final JWindow window = GuiUtilities.createWindow(parentComponent);
    window.getContentPane().setLayout(new BorderLayout());
    window.getContentPane().add(panel, BorderLayout.CENTER);
    window.pack();
    GuiUtilities.centerOnScreen(window);
    window.setVisible(true);

    Timer timer = new Timer(3000, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        window.dispose();
      }
    });
    timer.setRepeats(false);
    timer.start();
  }
}