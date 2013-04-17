package de.jdemo.runner;

import de.jdemo.framework.DemoIdentifier;
import de.jdemo.runner.path.ISourcePath;

/**
 * An IDemoShowSourceCodeHandler provides the ability to show the source code for
 * the {@link de.jdemo.framework.IDemoCase} specified by the given
 * {@link de.jdemo.framework.DemoIdentifier}. 
 * 
 * @author Markus Gebhard
 */
public interface IDemoShowSourceCodeHandler {
  /** Shows the source code specified by the given {@link de.jdemo.framework.DemoIdentifier}.
   * The actual behavior for showing source code depends on the specific implementation. */
  public void showDemoSourceCode(DemoIdentifier id) throws DemoSourceCodeNotFoundException;

  /** Sets the path this sourcecodehandler shall look for source files. If this handler gets
   * the source code from a different source than the path this method might do nothing. */
  public void setSourcePath(ISourcePath sourcePath);
}