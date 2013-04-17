package de.jdemo.swingui.list;

import de.jdemo.framework.IDemoCaseRunnable;
import de.jdemo.runner.IDemoIdentifierSelectionProvider;

/**
 * @author Markus Gebhard
 */
public interface IDemoExecutionList extends IDemoIdentifierSelectionProvider {

  public void removeAllTerminated();

  public void cancelSelectedDemo();

  public void rerunSelectedDemo();

  public IDemoCaseRunnable getSelectedDemoRunner();

  public boolean isEmpty();

}
