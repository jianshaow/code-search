package de.jdemo.swingui.icons;

import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

/**
 * @author Markus Gebhard
 */
//TODO NOW 08.01.2005 (Markus Gebhard): Moved to Disy_Commons_Swing
public class AggregatedIcon implements Icon {
  private final List decorations = new ArrayList();
  private final Icon baseIcon;

  public AggregatedIcon(Icon baseIcon) {
    this.baseIcon = baseIcon;
  }

  public void paintIcon(Component c, Graphics g, int x, int y) {
    baseIcon.paintIcon(c, g, x, y);
    for (int i = 0; i < decorations.size(); ++i) {
      ((Icon) decorations.get(i)).paintIcon(c, g, x, y);
    }
  }

  public int getIconWidth() {
    int width = baseIcon.getIconWidth();
    for (int i = 0; i < decorations.size(); ++i) {
      if (((Icon) decorations.get(i)).getIconWidth() > width) {
        width = ((Icon) decorations.get(i)).getIconWidth();
      }
    }
    return width;
  }

  public int getIconHeight() {
    int height = baseIcon.getIconHeight();
    for (int i = 0; i < decorations.size(); ++i) {
      if (((Icon) decorations.get(i)).getIconHeight() > height) {
        height = ((Icon) decorations.get(i)).getIconHeight();
      }
    }
    return height;
  }

  public void addDecorationIcon(Icon icon) {
    if (icon == null) {
      return;
    }
    decorations.add(icon);
  }
}