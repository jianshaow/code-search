package de.jdemo.swingui.tree;

import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import de.jdemo.framework.IDemo;
import de.jdemo.framework.IDemoCase;
import de.jdemo.framework.IDemoSuite;
import de.jdemo.framework.IDemoVisitor;
import de.jdemo.framework.util.DemoUtilities;
import de.jdemo.swingui.view.DemoUi;

/**
 * @author Markus Gebhard
 */
public class DemoTreeCellRenderer extends DefaultTreeCellRenderer {

  @Override
  public Component getTreeCellRendererComponent(
      final JTree tree,
      final Object value,
      final boolean selected,
      final boolean expanded,
      final boolean leaf,
      final int row,
      final boolean hasFocus) {

    super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

    if (!(value instanceof DefaultMutableTreeNode)) {
      throw new IllegalStateException();
    }
    final Object object = ((DefaultMutableTreeNode) value).getUserObject();
    if (!(object instanceof IDemo)) {
      return this;
    }

    final IDemo demo = ((IDemo) object);
    setText(DemoUtilities.getDisplayName(demo));

    setIcon(DemoUi.getIcon(demo));
    demo.accept(new IDemoVisitor() {
      public void visitDemoCase(final IDemoCase demoCase) {
        setFont(getFont().deriveFont(Font.BOLD));
        setToolTipText(demoCase.getIdentifier().toString());
      }

      public void visitDemoSuite(final IDemoSuite demoSuite) {
        setFont(getFont().deriveFont(Font.PLAIN));
        setToolTipText(null);
      }
    });

    return this;
  }

  public boolean isInsideTextField(Object value, final Rectangle nodeBounds, final Point point) {
    if (!nodeBounds.contains(point)) {
      //      System.out.println("outside rectangle");
      return false;
    }

    if (value instanceof DefaultMutableTreeNode) {
      value = ((DefaultMutableTreeNode) value).getUserObject();
    }

    if (value instanceof IDemo) {
      final IDemo node = (IDemo) value;

      final Icon icon = DemoUi.getIcon(node);
      if (icon != null) {
        //        System.out.println("with icon: "+
        //        "icon.getIconWidth()="+icon.getIconWidth()+
        //        "point.x="+point.x+
        //        "nodeBounds.x="+nodeBounds.x+
        //        " =>"+(icon.getIconWidth()<point.x-nodeBounds.x));
        return icon.getIconWidth() < point.x - nodeBounds.x;
      }
      else {
        //        System.out.println("without icon");
        return true;
      }
    }

    //    System.out.println("No info value = "+value.getClass());
    return true;
  }
}