package de.jdemo.runner.path;

import java.io.IOException;

/**
 * @author Markus Gebhard
 */
public interface ISourcePathElement {

  public boolean contains(String className);

  public String load(String className) throws IOException;

}