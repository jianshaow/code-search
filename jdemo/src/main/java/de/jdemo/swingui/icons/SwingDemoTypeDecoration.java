package de.jdemo.swingui.icons;

import javax.swing.Icon;

import de.jdemo.extensions.AwtDemoCase;
import de.jdemo.extensions.ISwtDemoCase;
import de.jdemo.extensions.SwingDemoCase;
import de.jdemo.framework.DemoCase;
import de.jdemo.framework.IDemoCase;
import de.jdemo.framework.PlainDemoCase;
import de.jdemo.framework.util.ErrorDemoCase;

/**
 * @author Markus Gebhard
 */
public class SwingDemoTypeDecoration {
  private final static SwingDemoTypeDecoration JFC = new SwingDemoTypeDecoration(JDemoIcons
      .getIconResource("decoration_jfc.gif")); //$NON-NLS-1$
  private final static SwingDemoTypeDecoration AWT = new SwingDemoTypeDecoration(JDemoIcons
      .getIconResource("decoration_awt.gif")); //$NON-NLS-1$
  private final static SwingDemoTypeDecoration STD = new SwingDemoTypeDecoration(JDemoIcons
      .getIconResource("decoration_std.gif")); //$NON-NLS-1$
  private final static SwingDemoTypeDecoration SWT = new SwingDemoTypeDecoration(JDemoIcons
      .getIconResource("decoration_swt.gif")); //$NON-NLS-1$
  private final static SwingDemoTypeDecoration ERROR = new SwingDemoTypeDecoration(JDemoIcons
      .getIconResource("decoration_error.gif")); //$NON-NLS-1$
  private final static SwingDemoTypeDecoration PLAIN = new SwingDemoTypeDecoration(JDemoIcons.getEmptyIcon());

  public static SwingDemoTypeDecoration getFor(IDemoCase demo) {
    Class demoClass = demo.getClass();
    if (ErrorDemoCase.class.isAssignableFrom(demoClass)) {
      return ERROR;
    }
    if (PlainDemoCase.class.isAssignableFrom(demoClass)) {
      return PLAIN;
    }
    if (SwingDemoCase.class.isAssignableFrom(demoClass)) {
      return JFC;
    }
    else if (AwtDemoCase.class.isAssignableFrom(demoClass)) {
      return AWT;
    }
    else if (ISwtDemoCase.class.isAssignableFrom(demoClass)) {
      return SWT;
    }
    else if (DemoCase.class.isAssignableFrom(demoClass)) {
      return STD;
    }
    else {
      return STD;
    }
  }

  private Icon icon;

  public SwingDemoTypeDecoration(Icon icon) {
    this.icon = icon;
  }

  public Icon getIcon() {
    return icon;
  }
}