package de.jdemo.swingui.tree.iterate.test;

import java.util.NoSuchElementException;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import junit.framework.TestCase;

import de.jdemo.framework.DemoIdentifier;
import de.jdemo.framework.IDemoCase;
import de.jdemo.framework.IDemoCaseRunnable;
import de.jdemo.framework.IDemoVisitor;
import de.jdemo.swingui.tree.iterate.NamedDemoCaseIterator;

/**
 * @author Markus Gebhard
 */
public class NamedDemoCaseIteratorTest extends TestCase {

  public void testNextWithEmptyTree() {
    final DefaultTreeModel treeModel = new DefaultTreeModel(null);
    final NamedDemoCaseIterator iterator = new NamedDemoCaseIterator(treeModel);
    assertHasNoNextElement(iterator);
  }

  public void testPreviousWithEmptyTree() {
    final DefaultTreeModel treeModel = new DefaultTreeModel(null);
    final NamedDemoCaseIterator iterator = new NamedDemoCaseIterator(treeModel);
    assertHasNoPreviousElement(iterator);
  }

  public void testNextWithSingleNodeTreeWithDemoCase() {
    final IDemoCase rootDemoCase = createDemoCase("root"); //$NON-NLS-1$
    final DefaultTreeModel treeModel = new DefaultTreeModel(new DefaultMutableTreeNode(rootDemoCase));
    final NamedDemoCaseIterator iterator = new NamedDemoCaseIterator(treeModel);
    assertIsNext(rootDemoCase, iterator);
    assertHasNoNextElement(iterator);
  }

  public void testPreviousWithSingleNodeTree() {
    final IDemoCase rootDemoCase = createDemoCase("root"); //$NON-NLS-1$
    final DefaultTreeModel treeModel = new DefaultTreeModel(new DefaultMutableTreeNode(rootDemoCase));
    final NamedDemoCaseIterator iterator = new NamedDemoCaseIterator(treeModel);
    assertIsNext(rootDemoCase, iterator);
    assertHasNoPreviousElement(iterator);
  }

  public void testNextWithSingleNodeTreeWithoutDemoCase() {
    final DefaultTreeModel treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("root")); //$NON-NLS-1$
    final NamedDemoCaseIterator iterator = new NamedDemoCaseIterator(treeModel);
    assertHasNoNextElement(iterator);
  }

  public void testNextWithTreeWithDemoCases() {
    final IDemoCase child1DemoCase = createDemoCase("child1"); //$NON-NLS-1$
    final IDemoCase child2DemoCase = createDemoCase("child2"); //$NON-NLS-1$
    final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
    rootNode.add(new DefaultMutableTreeNode(child1DemoCase));
    rootNode.add(new DefaultMutableTreeNode(child2DemoCase));
    final DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);

    final NamedDemoCaseIterator iterator = new NamedDemoCaseIterator(treeModel);
    assertIsNext(child1DemoCase, iterator);
    assertIsNext(child2DemoCase, iterator);
    assertHasNoNextElement(iterator);
  }

  public void testPreviousWithTreeWithDemoCases() {
    final IDemoCase child1DemoCase = createDemoCase("child1"); //$NON-NLS-1$
    final IDemoCase child2DemoCase = createDemoCase("child2"); //$NON-NLS-1$
    final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
    rootNode.add(new DefaultMutableTreeNode(child1DemoCase));
    rootNode.add(new DefaultMutableTreeNode(child2DemoCase));
    final DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);

    final NamedDemoCaseIterator iterator = new NamedDemoCaseIterator(treeModel);
    assertIsNext(child1DemoCase, iterator);
    assertHasNoPreviousElement(iterator);
    assertIsNext(child2DemoCase, iterator);
    assertIsPrevious(child1DemoCase, iterator);
    assertHasNoPreviousElement(iterator);
    assertIsNext(child2DemoCase, iterator);
    assertHasNoNextElement(iterator);
  }

  private static void assertIsNext(final IDemoCase demoCase, final NamedDemoCaseIterator iterator) {
    assertTrue(iterator.hasNext());
    assertSame(demoCase, iterator.getNext());
  }

  private static void assertIsPrevious(final IDemoCase demoCase, final NamedDemoCaseIterator iterator) {
    assertTrue(iterator.hasPrevious());
    assertSame(demoCase, iterator.getPrevious());
  }

  private static IDemoCase createDemoCase(final String name) {
    return new IDemoCase() {
      @Override
      public String toString() {
        return name;
      }

      @Override
      public IDemoCaseRunnable createRunnable(final boolean allowExternalLaunches) {
        throw new UnsupportedOperationException();
      }

      @Override
      public DemoIdentifier getIdentifier() {
        throw new UnsupportedOperationException();
      }

      @Override
      public String getName() {
        throw new UnsupportedOperationException();
      }

      @Override
      public void setName(final String mewName) {
        throw new UnsupportedOperationException();
      }

      @Override
      public void accept(final IDemoVisitor visitor) {
        throw new UnsupportedOperationException();
      }
    };
  }

  private static void assertHasNoNextElement(final NamedDemoCaseIterator iterator) {
    assertFalse(iterator.hasNext());
    try {
      iterator.getNext();
      fail();
    }
    catch (final NoSuchElementException expected) {
      //expected
    }
  }

  private static void assertHasNoPreviousElement(final NamedDemoCaseIterator iterator) {
    assertFalse(iterator.hasPrevious());
    try {
      iterator.getPrevious();
      fail();
    }
    catch (final NoSuchElementException expected) {
      //expected
    }
  }
}