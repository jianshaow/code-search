package de.jdemo.runner.path;

/**
 * @author Markus Gebhard
 */
public interface ISourcePath extends ISourcePathElement {

  public int getPathElementCount();

  public ISourcePathElement getPathElement(int index);

}