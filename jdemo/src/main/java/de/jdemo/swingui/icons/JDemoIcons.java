package de.jdemo.swingui.icons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author Markus Gebhard
 */
public class JDemoIcons {
  private static Map iconCache = new HashMap();
  private static Icon errorIcon = new ErrorIcon();
  private static Icon emptyIcon = new EmptyIcon();
  public static final Icon JDEMO = getIconResource(JDemoIcons.class, "jdemo.gif"); //$NON-NLS-1$
  public static final Icon DEMO_CASE = getIconResource(JDemoIcons.class, "democase.gif"); //$NON-NLS-1$
  public static final Icon DEMO_SUITE = getIconResource(JDemoIcons.class, "demosuite.gif"); //$NON-NLS-1$

  public static Icon getIconResource(String name) {
    return getIconResource(JDemoIcons.class, name);
  }

  public static Icon getIconResource(Class clazz, String name) {
    Icon icon = (Icon) iconCache.get(createKey(clazz, name));
    if (icon != null) {
      return icon;
    }
    URL url = clazz.getResource(name);
    if (url == null) {
      System.err.println("Warning: could not load icon '" + name + "'."); //$NON-NLS-1$//$NON-NLS-2$
      icon = errorIcon;
    }
    try {
      icon = new ImageIcon(url);
    }
    catch (Exception e) {
      icon = new ErrorIcon();
    }
    iconCache.put(createKey(clazz, name), icon);
    return icon;
  }

  private static Object createKey(Class clazz, String name) {
    return clazz.getName() + "_" + name; //$NON-NLS-1$
  }

  public static Image getImage(Icon icon) {
    BufferedImage image =
      new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
    icon.paintIcon(null, image.getGraphics(), 0, 0);
    return image;
  }

  public static Icon getEmptyIcon() {
    return emptyIcon;
  }

  public static Icon getErrorIcon() {
    return errorIcon;
  }

  private final static class EmptyIcon implements Icon {
    public int getIconWidth() {
      return 16;
    }
    public int getIconHeight() {
      return 16;
    }
    public void paintIcon(Component c, Graphics g, int x, int y) {
      //nothing to do
    }
  }

  private final static class ErrorIcon implements Icon {
    public int getIconWidth() {
      return 16;
    }
    public int getIconHeight() {
      return 16;
    }
    public void paintIcon(Component c, Graphics g, int x, int y) {
      //Workaround for MenuItem problem: reset color after painting
      Color previousColor = g.getColor();
      g.setColor(Color.red);
      g.fillRect(x, y, 16, 16);
      g.setColor(previousColor);
    }
  }
}