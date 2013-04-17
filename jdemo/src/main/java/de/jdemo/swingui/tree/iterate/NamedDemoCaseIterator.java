package de.jdemo.swingui.tree.iterate;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import de.jdemo.framework.IDemoCase;
import de.jdemo.swingui.util.DefaultMutableTreeNodeIterator;

/**
 * @author Markus Gebhard
 */
public class NamedDemoCaseIterator implements Cloneable {
  private DefaultMutableTreeNodeIterator iterator;
  private final DefaultTreeModel treeModel;

  public NamedDemoCaseIterator(DefaultTreeModel treeModel) {
    this.treeModel = treeModel;
    iterator = new DefaultMutableTreeNodeIterator(treeModel, new DemoCaseTreeNodeFilter());
  }

  public Object clone() {
    try {
      NamedDemoCaseIterator clone = (NamedDemoCaseIterator) super.clone();
      clone.iterator = (DefaultMutableTreeNodeIterator) iterator.clone();
      return clone;
    }
    catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean hasNext() {
    return iterator.hasNext();
  }

  public boolean hasPrevious() {
    return iterator.hasPrevious();
  }

  public IDemoCase getNext() {
    DefaultMutableTreeNode next = iterator.getNext();
    return (IDemoCase) next.getUserObject();
  }

  public IDemoCase getPrevious() {
    DefaultMutableTreeNode previous = iterator.getPrevious();
    return (IDemoCase) previous.getUserObject();
  }

  public DefaultMutableTreeNode getTreeNode() {
    return iterator.getCurrent();
  }

  public void remove() {
    remove(getTreeNode());
  }

  private void remove(DefaultMutableTreeNode node) {
    if (node.isRoot()) {
      return;
    }
    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();

    treeModel.removeNodeFromParent(node);
    if (parent.getChildCount() == 0) {
      remove(parent);
    }
  }
}