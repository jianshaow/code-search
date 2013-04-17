package de.jdemo.swingui.util.test;

import java.util.NoSuchElementException;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import de.jdemo.swingui.util.DefaultMutableTreeNodeIterator;
import de.jdemo.swingui.util.ITreeNodeFilter;

import junit.framework.TestCase;

/**
 * @author Markus Gebhard
 */
public class DefaultMutableTreeNodeIteratorTest extends TestCase {

  public void testIterateNextWithEmptyTree() {
    DefaultMutableTreeNodeIterator iterator = new DefaultMutableTreeNodeIterator(new DefaultTreeModel(null));
    assertHasNoNextElement(iterator);
  }

  public void testIteratePreviousWithEmptyTree() {
    DefaultMutableTreeNodeIterator iterator = new DefaultMutableTreeNodeIterator(new DefaultTreeModel(null));
    assertHasNoPreviousElement(iterator);
  }

  public void testIterateNextWithSingleNodeTree() {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root"); //$NON-NLS-1$
    DefaultMutableTreeNodeIterator iterator = new DefaultMutableTreeNodeIterator(new DefaultTreeModel(root));
    assertIsNext(root, iterator);
    assertHasNoNextElement(iterator);
  }

  public void testIteratePreviousWithSingleNodeTree() {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root"); //$NON-NLS-1$
    DefaultMutableTreeNodeIterator iterator = new DefaultMutableTreeNodeIterator(new DefaultTreeModel(root));
    assertHasNoPreviousElement(iterator);
    assertSame(root, iterator.getNext());
    assertHasNoPreviousElement(iterator);
  }

  private static void assertHasNoNextElement(DefaultMutableTreeNodeIterator iterator) {
    assertFalse(iterator.hasNext());
    try {
      iterator.getNext();
      fail();
    }
    catch (NoSuchElementException expected) {
      //expected
    }
  }

  private static void assertHasNoPreviousElement(DefaultMutableTreeNodeIterator iterator) {
    assertFalse(iterator.hasPrevious());
    try {
      iterator.getPrevious();
      fail();
    }
    catch (NoSuchElementException expected) {
      //expected
    }
  }

  public void testIterateNextWithTree() {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root"); //$NON-NLS-1$
    DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("child1"); //$NON-NLS-1$
    root.add(child1);
    DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("child2"); //$NON-NLS-1$
    root.add(child2);
    DefaultMutableTreeNodeIterator iterator = new DefaultMutableTreeNodeIterator(new DefaultTreeModel(root));
    assertIsNext(root, iterator);
    assertIsNext(child1, iterator);
    assertIsNext(child2, iterator);
    assertHasNoNextElement(iterator);
  }

  public void testIteratePreviousWithTree() {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root"); //$NON-NLS-1$
    DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("child1"); //$NON-NLS-1$
    root.add(child1);
    DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("child2"); //$NON-NLS-1$
    root.add(child2);
    DefaultMutableTreeNodeIterator iterator = new DefaultMutableTreeNodeIterator(new DefaultTreeModel(root));

    //Iterate down to the end...
    assertSame(root, iterator.getNext());
    assertSame(child1, iterator.getNext());
    assertSame(child2, iterator.getNext());

    //And back up.    
    assertIsPrevious(child1, iterator);
    assertIsPrevious(root, iterator);
    assertHasNoPreviousElement(iterator);
  }

  private static void assertIsNext(DefaultMutableTreeNode node, DefaultMutableTreeNodeIterator iterator) {
    assertTrue(iterator.hasNext());
    assertSame(node, iterator.getNext());
  }

  private static void assertIsPrevious(DefaultMutableTreeNode node, DefaultMutableTreeNodeIterator iterator) {
    assertTrue(iterator.hasPrevious());
    assertSame(node, iterator.getPrevious());
  }

  public void testIterateInMixedDirections() {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root"); //$NON-NLS-1$
    DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("child1"); //$NON-NLS-1$
    root.add(child1);
    DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("child2"); //$NON-NLS-1$
    root.add(child2);
    DefaultMutableTreeNodeIterator iterator = new DefaultMutableTreeNodeIterator(new DefaultTreeModel(root));

    assertHasNoPreviousElement(iterator);
    assertIsNext(root, iterator);
    assertHasNoPreviousElement(iterator);
    assertIsNext(child1, iterator);
    assertIsPrevious(root, iterator);
    assertIsNext(child1, iterator);
    assertIsNext(child2, iterator);
    assertIsPrevious(child1, iterator);
    assertIsNext(child2, iterator);
    assertHasNoNextElement(iterator);
  }


  public void testIterateNextWithTreeAndFilter() {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root"); //$NON-NLS-1$
    DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("child1_accepted"); //$NON-NLS-1$
    root.add(child1);
    DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("child2"); //$NON-NLS-1$
    root.add(child2);
    DefaultMutableTreeNode child3 = new DefaultMutableTreeNode("child3_accepted"); //$NON-NLS-1$
    root.add(child3);
    DefaultMutableTreeNode child4 = new DefaultMutableTreeNode("child4"); //$NON-NLS-1$
    root.add(child4);

    DefaultMutableTreeNodeIterator iterator = new DefaultMutableTreeNodeIterator(new DefaultTreeModel(root), new ITreeNodeFilter() {
      public boolean isAccepted(DefaultMutableTreeNode treeNode) {
        return ((String)treeNode.getUserObject()).indexOf("accepted")!=-1; //$NON-NLS-1$
      }
    });
    assertIsNext(child1, iterator);
    assertIsNext(child3, iterator);
    assertHasNoNextElement(iterator);
  }

  public void testIterateInComplexTree() {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root"); //$NON-NLS-1$
    DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("child1"); //$NON-NLS-1$
    root.add(child1);
    DefaultMutableTreeNode child1a = new DefaultMutableTreeNode("child1a"); //$NON-NLS-1$
    child1.add(child1a);
    DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("child2"); //$NON-NLS-1$
    root.add(child2);
    DefaultMutableTreeNode child2a = new DefaultMutableTreeNode("child2a"); //$NON-NLS-1$
    child2.add(child2a);
    DefaultMutableTreeNodeIterator iterator = new DefaultMutableTreeNodeIterator(new DefaultTreeModel(root));

    assertHasNoPreviousElement(iterator);
    assertIsNext(root, iterator);
    assertHasNoPreviousElement(iterator);
    assertIsNext(child1, iterator);
    assertIsNext(child1a, iterator);
    assertIsNext(child2, iterator);
    assertIsNext(child2a, iterator);
    assertHasNoNextElement(iterator);

    assertIsPrevious(child2, iterator);
    assertIsPrevious(child1a, iterator);
    assertIsPrevious(child1, iterator);
    assertIsPrevious(root, iterator);
    assertHasNoPreviousElement(iterator);
 }

  public void testIterateWithTreeFilterInComplexTree() {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root"); //$NON-NLS-1$
    DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("child1"); //$NON-NLS-1$
    root.add(child1);
    DefaultMutableTreeNode child1a = new DefaultMutableTreeNode("child1a_accepted"); //$NON-NLS-1$
    child1.add(child1a);
    DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("child2"); //$NON-NLS-1$
    root.add(child2);
    DefaultMutableTreeNode child2a = new DefaultMutableTreeNode("child2a_accepted"); //$NON-NLS-1$
    child2.add(child2a);
    DefaultMutableTreeNodeIterator iterator = new DefaultMutableTreeNodeIterator(new DefaultTreeModel(root), new ITreeNodeFilter() {
      public boolean isAccepted(DefaultMutableTreeNode treeNode) {
        return ((String)treeNode.getUserObject()).indexOf("accepted")!=-1; //$NON-NLS-1$
      }
    });

    assertHasNoPreviousElement(iterator);
    assertIsNext(child1a, iterator);
    assertHasNoPreviousElement(iterator);
    assertIsNext(child2a, iterator);
    assertHasNoNextElement(iterator);

    assertIsPrevious(child1a, iterator);
    assertHasNoPreviousElement(iterator);
 }
}