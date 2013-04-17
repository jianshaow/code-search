package de.jdemo.swingui.util;

import java.util.NoSuchElementException;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * @author Markus Gebhard
 */
public class DefaultMutableTreeNodeIterator implements Cloneable {
  private DefaultMutableTreeNode currentNode;
  private DefaultMutableTreeNode nextNode;
  private DefaultMutableTreeNode previousNode;
  private final ITreeNodeFilter nodeFilter;

  public Object clone() {
    try {
      return super.clone();
    }
    catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  public DefaultMutableTreeNodeIterator(DefaultTreeModel treeModel) {
    this(treeModel, new ITreeNodeFilter() {
      public boolean isAccepted(DefaultMutableTreeNode treeNode) {
        return true;
      }
    });
  }

  public DefaultMutableTreeNodeIterator(DefaultTreeModel treeModel, ITreeNodeFilter nodeFilter) {
    this.nodeFilter = nodeFilter;
    nextNode = (DefaultMutableTreeNode) treeModel.getRoot();
    if (nextNode!=null && !nodeFilter.isAccepted(nextNode)) {
      currentNode = nextNode;
      findNext();
      currentNode = null;
    }
  }

  public boolean hasNext() {
    return nextNode != null;
  }

  public DefaultMutableTreeNode getNext() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    previousNode = currentNode;
    currentNode = nextNode;
    findNext();
    return currentNode;
  }

  public DefaultMutableTreeNode getPrevious() {
    if (!hasPrevious()) {
      throw new NoSuchElementException();
    }
    nextNode = currentNode;
    currentNode = previousNode;
    findPrevious();
    return currentNode;
  }

  private void findPrevious() {
    do {
      DefaultMutableTreeNode previousSibling = previousNode.getPreviousSibling();
      if (previousSibling != null) {
        previousNode = getLastChild(previousSibling);
      }
      else {
        previousNode = (DefaultMutableTreeNode) previousNode.getParent();
      }
    }
    while (previousNode != null && !nodeFilter.isAccepted(previousNode));
  }

  private static DefaultMutableTreeNode getLastChild(DefaultMutableTreeNode node) {
    if (node.getChildCount()==0) {
      return node;
    }
    return getLastChild((DefaultMutableTreeNode) node.getChildAt(node.getChildCount()-1));
  }

  private void findNext() {
    do {
      if (nextNode.getChildCount() > 0) {
        nextNode = (DefaultMutableTreeNode) nextNode.getChildAt(0);
      }
      else {
        nextNode = findNextSibling(nextNode);
      }
    }
    while (nextNode != null && !nodeFilter.isAccepted(nextNode));
  }

  private static DefaultMutableTreeNode findNextSibling(DefaultMutableTreeNode node) {
    DefaultMutableTreeNode nextSibling = node.getNextSibling();
    if (nextSibling != null) {
      return nextSibling;
    }
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
    if (parentNode==null) {
      return null;
    }
    return findNextSibling(parentNode);
  }

  public boolean hasPrevious() {
    return previousNode != null;
  }

  public DefaultMutableTreeNode getCurrent() {
    return currentNode;
  }
}