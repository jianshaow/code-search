package de.jdemo.swingui.launch;

import de.jdemo.runner.path.EmptySourcePath;
import de.jdemo.runner.path.ISourcePath;

/**
 * @author Markus Gebhard
 */
public class CommandLineOptions {
  private boolean showVersion;
  private boolean showHelp;
  private ISourcePath sourcePath = new EmptySourcePath();
  private IDemoRunnerLaunchStrategy launchStrategy;
  
  public boolean isShowVersion() {
    return showVersion;
  }

  public void setShowVersion(boolean showVersion) {
    this.showVersion = showVersion;
  }

  public boolean isShowHelp() {
    return showHelp;
  }

  public void setShowHelp(boolean showHelp) {
    this.showHelp = showHelp;
  }

  public ISourcePath getSourcePath() {
    return sourcePath;
  }

  public void setSourcePath(ISourcePath sourcePath) {
    this.sourcePath = sourcePath;
  }

  public void setLaunchStrategy(IDemoRunnerLaunchStrategy launchStrategy) {
    this.launchStrategy=launchStrategy;
  }

  public IDemoRunnerLaunchStrategy getLaunchStrategy() {
    return launchStrategy;
  }
}