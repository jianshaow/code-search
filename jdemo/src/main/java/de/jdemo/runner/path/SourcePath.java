package de.jdemo.runner.path;

import java.io.IOException;

/**
 * @author Markus Gebhard
 */
public class SourcePath implements ISourcePath {
  private final ISourcePathElement[] pathElements;

  public SourcePath(final ISourcePathElement[] pathElements) {
    this.pathElements = pathElements;
  }

  public int getPathElementCount() {
    return pathElements.length;
  }

  public ISourcePathElement getPathElement(final int index) {
    return pathElements[index];
  }

  public boolean contains(final String className) {
    for (int i = 0; i < getPathElementCount(); i++) {
      if (getPathElement(i).contains(className)) {
        return true;
      }
    }
    return false;
  }

  public String load(final String className) throws IOException {
    for (int i = 0; i < getPathElementCount(); i++) {
      if (getPathElement(i).contains(className)) {
        return getPathElement(i).load(className);
      }
    }
    return null;
  }
}