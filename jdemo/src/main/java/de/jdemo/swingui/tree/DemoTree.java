package de.jdemo.swingui.tree;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import de.jdemo.framework.DemoIdentifier;
import de.jdemo.framework.DemoSuite;
import de.jdemo.framework.IDemo;
import de.jdemo.framework.IDemoCase;
import de.jdemo.framework.IDemoSuite;
import de.jdemo.framework.IDemoVisitor;
import de.jdemo.framework.util.DemoUtilities;
import de.jdemo.runner.IDemoShowSourceCodeHandler;
import de.jdemo.swingui.DemoRunnerPanel;
import de.jdemo.swingui.IDemoExecuter;
import de.jdemo.swingui.IncrementalDemoSearchable;
import de.jdemo.swingui.find.IncrementalFind;
import de.jdemo.swingui.model.DemoSelectionModel;
import de.jdemo.swingui.tree.iterate.DemoCaseTreeNodeFilter;
import de.jdemo.swingui.tree.iterate.NamedDemoCaseIterator;
import de.jdemo.swingui.util.DefaultMutableTreeNodeIterator;
import de.jdemo.swingui.util.IStatusIndicator;
import de.jdemo.util.GuiUtilities;

/**
 * @author Markus Gebhard
 */
public class DemoTree implements IDemoTree {
  private DemoTreeActions actions;
  private JTree tree;
  private IDemo rootDemo;
  private IStatusIndicator statusIndicator;
  private IDemoExecuter executer;
  private IncrementalFind incrementalFind;

  public DemoTree(
      IDemo rootDemo,
      IDemoExecuter executer,
      IDemoShowSourceCodeHandler showSourceCodeHandler,
      IStatusIndicator statusIndicator,
      final DemoSelectionModel selectionModel) {
    this.executer = executer;
    this.statusIndicator = statusIndicator;

    tree = new JTree() {
      public synchronized void addKeyListener(final KeyListener listener) {
        if (listener instanceof BasicTreeUI.KeyHandler) {
          //Disable automatic search by start letter when Incremental search is active
          super.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
              if (!isIncrementalFindActive()) {
                listener.keyPressed(e);
              }
            }

            public void keyReleased(KeyEvent e) {
              if (!isIncrementalFindActive()) {
                listener.keyReleased(e);
              }
            }

            public void keyTyped(KeyEvent e) {
              //The keyTyped part does not work anyway, since it works on toString() and does not consider the UI
              //              if (!isIncrementalFindActive()) {
              //                listener.keyTyped(e);
              //              }
            }

            private boolean isIncrementalFindActive() {
              return incrementalFind != null && incrementalFind.isSearching();
            }
          });
        }
        else {
          super.addKeyListener(listener);
        }
      }
    };
    tree.setToolTipText("");//Triggers tooltips on for this tree
    setRootDemo(rootDemo);
    actions = new DemoTreeActions(this, getDemoExecuter(), showSourceCodeHandler, selectionModel);
    tree.add(actions.getDemoCasePopupMenu());
    tree.add(actions.getStandardPopupMenu());

    tree.setCellRenderer(new DemoTreeCellRenderer());
    tree.setEditable(false);

    tree.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        //        //request focus in order for KeyEvents to be posted to the
        //				// DemoSearcher
        //        tree.requestFocus();
        //
        //Ensure node selection even with right mouse button
        if (!e.isMetaDown()) {
          return;
        }
        int rowIndex = tree.getRowForLocation(e.getX(), e.getY());
        if (rowIndex == -1) {
          tree.getSelectionModel().clearSelection();
          return;
        }
        tree.getSelectionModel().setSelectionPath(tree.getPathForRow(rowIndex));
      }

      public void mouseReleased(MouseEvent event) {
        if (event.isMetaDown()) {
          if (getSelectedDemoCase() != null) {
            actions.getDemoCasePopupMenu().show(tree, event.getX(), event.getY());
          }
          else {
            actions.getStandardPopupMenu().show(tree, event.getX(), event.getY());
          }
        }
        else if (event.getClickCount() == 2) {
          executeSelectedDemoCase();
        }
      }
    });

    tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
        actions.updateEnabled();
        selectionModel.setSelectedDemo(getSelectedDemo());
        //TODO Nov 1, 2004 (Markus Gebhard): Attach StatusIndicator as Listener to the SelectionModel
        if (tree.getSelectionModel().isSelectionEmpty()) {
          DemoTree.this.statusIndicator.setStatus(""); //$NON-NLS-1$
        }
        else {
          DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
          IDemo namedDemo = (IDemo) node.getUserObject();
          namedDemo.accept(new IDemoVisitor() {
            public void visitDemoCase(IDemoCase demoCase) {
              DemoTree.this.statusIndicator.setStatus(demoCase.getIdentifier().toString());
            }

            public void visitDemoSuite(IDemoSuite demoSuite) {
              DemoTree.this.statusIndicator.setStatus(DemoUtilities.getDisplayName(demoSuite));
            }
          });
        }
      }
    });

    tree.addKeyListener(new KeyListener() {
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          executeSelectedDemoCase();
        }
      }

      public void keyReleased(KeyEvent e) {
        //nothing to do
      }

      public void keyTyped(KeyEvent e) {
        //nothing to do
      }
    });
    tree.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent e) {
        selectionModel.setSelectedDemo(getSelectedDemo());
      }
    });
  }

  public void removeDuplicates() {
    NamedDemoCaseIterator iterator = getDemoCaseIterator();
    HashSet demoIds = new HashSet();
    int count = 0;
    while (iterator.hasNext()) {
      DemoIdentifier identifier = iterator.getNext().getIdentifier();
      if (demoIds.contains(identifier)) {
        iterator.remove();
        ++count;
      }
      else {
        demoIds.add(identifier);
      }
    }
    if (count == 1) {
      statusIndicator.setStatus("1 duplicate removed.");
    }
    else {
      statusIndicator.setStatus(count + " duplicate(s) removed.");
    }
  }

  private void executeSelectedDemoCase() {
    IDemoCase selectedDemo = getSelectedDemoCase();
    if (selectedDemo == null) {
      return;
    }
    getDemoExecuter().executeDemo(selectedDemo, null);
  }

  public IDemoExecuter getDemoExecuter() {
    return executer;
  }

  public void removeSelectedDemo() {
    DefaultMutableTreeNode node = getSelectedTreeNode();
    if (node == null) {
      return;
    }
    DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
    if (node.getParent() == null) {
      DemoRunnerPanel.showErrorDialog(tree, null, "The root node cannot be removed.", null);
      return;
    }
    model.removeNodeFromParent(node);
  }

  public IDemo getSelectedDemo() {
    DefaultMutableTreeNode selectedNode = getSelectedTreeNode();
    if (selectedNode == null) {
      return null;
    }
    return (IDemo) selectedNode.getUserObject();
  }

  private DefaultMutableTreeNode getSelectedTreeNode() {
    TreePath selectionPath = tree.getSelectionModel().getSelectionPath();
    if (selectionPath == null) {
      return null;
    }
    return (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
  }

  public JComponent getComponent() {
    return tree;
  }

  private static class DefaultMutableTreeNodeHolder {
    public DefaultMutableTreeNode node;
  }

  private static DefaultMutableTreeNode createTreeNode(IDemo demo) {
    final DefaultMutableTreeNodeHolder nodeHolder = new DefaultMutableTreeNodeHolder();
    demo.accept(new IDemoVisitor() {
      public void visitDemoCase(IDemoCase demoCase) {
        nodeHolder.node = createDemoCaseNode(demoCase);
      }

      public void visitDemoSuite(IDemoSuite demoSuite) {
        nodeHolder.node = createDemoSuiteNode(demoSuite);
        for (int i = 0; i < demoSuite.getDemoCount(); ++i) {
          nodeHolder.node.add(createTreeNode(demoSuite.getDemoAt(i)));
        }
      }
    });
    return nodeHolder.node;
  }

  public int getDemoCaseCount() {
    int count = 0;
    NamedDemoCaseIterator iterator = getDemoCaseIterator();
    while (iterator.hasNext()) {
      iterator.getNext();
      ++count;
    }
    return count;
  }

  public NamedDemoCaseIterator getDemoCaseIterator() {
    return new NamedDemoCaseIterator((DefaultTreeModel) tree.getModel());
  }

  public void select(DefaultMutableTreeNode node) {
    if (node == null) {
      tree.getSelectionModel().clearSelection();
    }
    else {
      TreePath path = new TreePath(node.getPath());
      int rowIndex = tree.getRowForPath(path);
      select(rowIndex);
    }
  }

  public void select(int rowIndex) {
    tree.expandRow(rowIndex);
    tree.getSelectionModel().setSelectionPath(tree.getPathForRow(rowIndex));
    tree.scrollRowToVisible(rowIndex);
  }

  public IDemoCase getSelectedDemoCase() {
    IDemo selectedDemo = getSelectedDemo();
    if (selectedDemo instanceof IDemoCase) {
      return (IDemoCase) selectedDemo;
    }
    else {
      return null;
    }
  }

  public IDemo getRootDemo() {
    return rootDemo;
  }

  public void setRootDemo(IDemo rootDemo) {
    this.rootDemo = rootDemo;
    if (rootDemo == null) {
      IDemoSuite suite = new DemoSuite();
      suite.setName("Demo");
      tree.setModel(new DefaultTreeModel(createDemoSuiteNode(suite)));
    }
    else {
      TreeNode rootNode = createTreeNode(rootDemo);
      tree.setModel(new DefaultTreeModel(rootNode));
      GuiUtilities.expandTreeNode(tree, rootNode);
    }
  }

  private static DefaultMutableTreeNode createDemoSuiteNode(IDemoSuite demoSuite) {
    return new DefaultMutableTreeNode(demoSuite);
  }

  private static DefaultMutableTreeNode createDemoCaseNode(IDemoCase demoCase) {
    DefaultMutableTreeNode node = new DefaultMutableTreeNode(demoCase);
    node.setAllowsChildren(false);
    return node;
  }

  public void renameSelectedNode() {
    tree.startEditingAtPath(getSelectionPath());
  }

  private TreePath getSelectionPath() {
    return getTreePath(getSelectedTreeNode());
  }

  private TreePath getTreePath(DefaultMutableTreeNode selectedTreeNode) {
    DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
    return new TreePath(model.getPathToRoot(selectedTreeNode));
  }

  public IncrementalFind attachIncrementalFind(IncrementalDemoSearchable searchable) {
    incrementalFind = IncrementalFind.attachTo(getComponent(), searchable);
    return incrementalFind;
  }

  public void requestFocus() {
    tree.requestFocus();
  }

  public void selectFirstDemoCaseIfAny() {
    DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
    DefaultMutableTreeNodeIterator iterator = new DefaultMutableTreeNodeIterator(
        model,
        new DemoCaseTreeNodeFilter());
    if (!iterator.hasNext()) {
      return;
    }
    DefaultMutableTreeNode node = iterator.getNext();
    select(node);
    tree.scrollRowToVisible(0);
  }
}