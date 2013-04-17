package de.jdemo.runner.path;

/**
 * @author Markus Gebhard
 */
public class EmptySourcePath implements ISourcePath {

  @Override
  public int getPathElementCount() {
    return 0;
  }

  @Override
  public ISourcePathElement getPathElement(final int index) {
    throw new IllegalArgumentException();
  }

  @Override
  public boolean contains(final String className) {
    return false;
  }

  @Override
  public String load(final String className) {
    return null;
  }
}