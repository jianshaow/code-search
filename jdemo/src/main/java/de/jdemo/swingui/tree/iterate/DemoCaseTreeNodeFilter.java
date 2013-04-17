package de.jdemo.swingui.tree.iterate;

import javax.swing.tree.DefaultMutableTreeNode;

import de.jdemo.framework.IDemoCase;
import de.jdemo.swingui.util.ITreeNodeFilter;

public final class DemoCaseTreeNodeFilter implements ITreeNodeFilter {
  public boolean isAccepted(DefaultMutableTreeNode treeNode) {
    return treeNode.getUserObject() instanceof IDemoCase;
  }
}