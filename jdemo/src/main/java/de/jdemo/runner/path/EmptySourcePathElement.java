package de.jdemo.runner.path;

/**
 * @author Markus Gebhard
 */
public class EmptySourcePathElement implements ISourcePathElement {

  @Override
  public boolean contains(final String className) {
    return false;
  }

  @Override
  public String load(final String className) {
    return null;
  }
}