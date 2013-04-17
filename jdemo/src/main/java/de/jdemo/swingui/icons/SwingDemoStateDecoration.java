package de.jdemo.swingui.icons;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;

import de.jdemo.framework.state.DemoState;

/**
 * @author Markus Gebhard
 */
public class SwingDemoStateDecoration {
  private static Map map;

  static {
    map = new HashMap();
    map.put(
      DemoState.INITIAL,
      new SwingDemoStateDecoration(JDemoIcons.getIconResource("decoration_initial.gif"), new Color(128, 128, 128))); //$NON-NLS-1$

    map.put(
      DemoState.CRASHED,
      new SwingDemoStateDecoration(JDemoIcons.getIconResource("decoration_crashed.gif"), new Color(255, 0, 0))); //$NON-NLS-1$

    map.put(
      DemoState.STARTING,
      new SwingDemoStateDecoration(JDemoIcons.getIconResource("decoration_starting.gif"), new Color(0, 255, 0))); //$NON-NLS-1$

    map.put(
      DemoState.RUNNING,
      new SwingDemoStateDecoration(JDemoIcons.getIconResource("decoration_running.gif"), new Color(0, 128, 0))); //$NON-NLS-1$

    map.put(
      DemoState.FINISHED,
      new SwingDemoStateDecoration(JDemoIcons.getIconResource("decoration_finished.gif"), new Color(128, 128, 128))); //$NON-NLS-1$
  }

  public static SwingDemoStateDecoration getFor(DemoState state) {
    if (map.containsKey(state)) {
      return (SwingDemoStateDecoration) map.get(state);
    } else {
      throw new UnsupportedOperationException(
        "No decoration specified for DemoState '" + state + "'."); //$NON-NLS-1$ //$NON-NLS-2$
    }
  }

  private Color color;
  private Icon icon;

  public SwingDemoStateDecoration(Icon icon, Color color) {
    this.icon = icon;
    this.color = color;
  }

  public Icon getIcon() {
    return icon;
  }

  public Color getColor() {
    return color;
  }
}