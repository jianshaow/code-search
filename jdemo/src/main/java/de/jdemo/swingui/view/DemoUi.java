package de.jdemo.swingui.view;

import javax.swing.Icon;

import de.jdemo.framework.IDemo;
import de.jdemo.framework.IDemoCase;
import de.jdemo.framework.IDemoSuite;
import de.jdemo.framework.IDemoVisitor;
import de.jdemo.swingui.icons.AggregatedIcon;
import de.jdemo.swingui.icons.JDemoIcons;
import de.jdemo.swingui.icons.SwingDemoTypeDecoration;

/**
 * @author Markus Gebhard
 */
public class DemoUi {

  private static class IconHolder {
    public Icon icon;
  }

  public static Icon getIcon(IDemo namedDemo) {
    final IconHolder iconHolder = new IconHolder();
    namedDemo.accept(new IDemoVisitor() {
      public void visitDemoCase(IDemoCase demoCase) {
        AggregatedIcon icon = new AggregatedIcon(JDemoIcons.DEMO_CASE);
        icon.addDecorationIcon(SwingDemoTypeDecoration.getFor(demoCase).getIcon());
        iconHolder.icon = icon;
      }

      public void visitDemoSuite(IDemoSuite demoSuite) {
        iconHolder.icon = JDemoIcons.DEMO_SUITE;
      }
    });

    return iconHolder.icon;
  }
}