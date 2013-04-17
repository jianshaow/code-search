package de.jdemo.swingui.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * @author Markus Gebhard
 */
public class HorizontalLine extends JComponent {

  private Color highlight;
  private Color shadow;

  public Dimension getPreferredSize() {
    return new Dimension(10, 2);
  }

  protected void paintComponent(Graphics g) {
    g.setColor(getShadowColor());
    g.drawLine(0, 0, getSize().width, 0);
    g.setColor(getHighlightColor());
    g.drawLine(0, 1, getSize().width, 1);
  }

  public Color getHighlightColor() {
    return highlight != null ? highlight : getBackground().brighter();
  }

  public Color getShadowColor() {
    return shadow != null ? shadow : getBackground().darker();
  }
}