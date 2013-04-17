package de.jdemo.swingui;

import de.jdemo.framework.IDemo;
import de.jdemo.framework.IDemoCase;
import de.jdemo.framework.util.DemoUtilities;
import de.jdemo.swingui.find.IIncrementalSearchable;
import de.jdemo.swingui.tree.iterate.NamedDemoCaseIterator;

/**
 * @author Markus Gebhard
 */
public class IncrementalDemoSearchable implements IIncrementalSearchable {
  private DemoRunnerPanel runner;
  private NamedDemoCaseIterator iterator;
  private IDemo currentDemo;

  public IncrementalDemoSearchable(DemoRunnerPanel runner) {
    this.runner = runner;
  }

  public void showSearchMessage(String searchPattern, boolean failed) {
    if (failed) {
      runner.setStatus("Incremental find: " + searchPattern + " not found");
    }
    else {
      runner.setStatus("Incremental find: " + searchPattern);
    }
  }

  public void initSearch() {
    runner.getTree().requestFocus();
    selectFirstElement();
  }

  public boolean currentElementFits(String searchPattern) {
    return fits(currentDemo, searchPattern);
  }

  private static boolean fits(IDemo demo, String searchPattern) {
    if (demo == null) {
      return false;
    }
    return DemoUtilities.getDisplayName(demo).toLowerCase().indexOf(searchPattern) != -1;
  }

  public void selectFirstElement() {
    iterator = runner.getTree().getDemoCaseIterator();
    if (iterator.hasNext()) {
      currentDemo = iterator.getNext();
      //      runner.getTree().select(iterator.getTreeNode());
    }
    else {
      currentDemo = null;
    }
    runner.getTree().select(null);
  }

  public void selectLastElement() {
    iterator = runner.getTree().getDemoCaseIterator();
    currentDemo = null;
    while (iterator.hasNext()) {
      currentDemo = iterator.getNext();
    }
    //if (currentDemo != null) {
    //      runner.getTree().select(iterator.getTreeNode());
    //}
    runner.getTree().select(null);
  }

  public boolean findNextElement(String searchPattern) {
    NamedDemoCaseIterator iterator2 = (NamedDemoCaseIterator) iterator.clone();
    while (iterator2.hasNext()) {
      IDemoCase demoCase = iterator2.getNext();
      if (fits(demoCase, searchPattern)) {
        iterator = iterator2;
        currentDemo = demoCase;
        runner.getTree().select(iterator.getTreeNode());
        return true;
      }
    }
    return false;
  }

  public boolean findPreviousElement(String searchPattern) {
    NamedDemoCaseIterator iterator2 = (NamedDemoCaseIterator) iterator.clone();
    while (iterator2.hasPrevious()) {
      IDemoCase demoCase = iterator2.getPrevious();
      if (fits(demoCase, searchPattern)) {
        iterator = iterator2;
        currentDemo = demoCase;
        runner.getTree().select(iterator.getTreeNode());
        return true;
      }
    }
    return false;
  }

  public void clearSearchMessage() {
    runner.setStatus(""); //$NON-NLS-1$
  }

  public void beep() {
    runner.getTree().getComponent().getToolkit().beep();
  }

  public void selectCurrentElement() {
    runner.getTree().select(iterator.getTreeNode());
  }
}