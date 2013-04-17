package de.jdemo.swingui.util;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Markus Gebhard
 */
public interface ITreeNodeFilter {
  public boolean isAccepted(DefaultMutableTreeNode treeNode) ;

}
