package de.jdemo.swingui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * @author Markus Gebhard
 */
public class FramePanel {

  private final JPanel panel;
  private final JPanel contentPanel;

  //  private final JComponent component;

  public FramePanel(String title, final JComponent component) {
    //    panel = new JPanel(new BorderLayout(2, 2));
    //    panel.add(new JLabel(title, SwingConstants.LEFT), BorderLayout.NORTH);
    //    panel.add(component, BorderLayout.CENTER);
    //    panel.setBorder(new EmptyBorder(4, 4, 4, 4));

    // this.component = component;
    contentPanel = new JPanel(new GridLayout(1, 0));
    contentPanel.add(component);
    contentPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
    //    component.addFocusListener(new FocusListener() {
    //      public void focusGained(FocusEvent e) {
    //        updateBorder();
    //      }
    //
    //      public void focusLost(FocusEvent e) {
    //        updateBorder();
    //      }
    //    });
    //    updateBorder();

    JLabel label = new JLabel(title, SwingConstants.LEFT);
    label.setBorder(new EmptyBorder(3, 3, 0, 3));

    panel = new JPanel(new BorderLayout(0, 0));
    panel.add(label, BorderLayout.NORTH);
    panel.add(contentPanel, BorderLayout.CENTER);
  }

  //  private void updateBorder() {
  //    System.err.println(component.hasFocus() + " " + component);
  //    if (component.hasFocus()) {
  //      contentPanel.setBorder(new EmptyBorder(3, 3, 3, 3) {
  //        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
  //          g.setColor(SystemColor.activeCaption);
  //          for (int i = 1; i < 3; i++) {
  //            g.drawRect(x + i, y + i, width - 1 - i - i, height - 1 - i - i);
  //          }
  //        }
  //
  //        public boolean isBorderOpaque() {
  //          return true;
  //        }
  //      });
  //    }
  //    else {
  //      contentPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
  //    }
  //  }

  public JComponent getContent() {
    return panel;
  }

}
