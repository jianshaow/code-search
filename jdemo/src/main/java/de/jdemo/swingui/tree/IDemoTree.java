package de.jdemo.swingui.tree;

/**
 * @author Markus Gebhard
 */
public interface IDemoTree extends IDemoSelectionProvider {

  public void removeSelectedDemo();

  public void removeDuplicates();

}