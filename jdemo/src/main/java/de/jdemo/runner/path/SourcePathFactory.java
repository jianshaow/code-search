package de.jdemo.runner.path;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * @author Markus Gebhard
 */
public class SourcePathFactory {

  public ISourcePath createSourcePath(String pathString) {
    String separator = getPathSeparator();
    StringTokenizer tokenizer = new StringTokenizer(pathString, separator);
    int pathElementCount = tokenizer.countTokens();
    ISourcePathElement[] pathElements = new ISourcePathElement[pathElementCount];
    for (int i = 0; i < pathElementCount; i++) {
      pathElements[i] = createPathElement(tokenizer.nextToken());
    }
    return new SourcePath(pathElements);
  }

  public static String getPathSeparator() {
    return System.getProperty("path.separator"); //$NON-NLS-1$
  }

  private ISourcePathElement createPathElement(String pathElementString) {
    File file = new File(pathElementString);
    String lowerCaseFileName = file.getName().toLowerCase();
    if (lowerCaseFileName.endsWith(".jar") || lowerCaseFileName.endsWith(".zip")) { //$NON-NLS-1$ //$NON-NLS-2$
      try {
        return new ZipSourcePathElement(file);
      }
      catch (IOException e) {
        return new EmptySourcePathElement();
      }
    }
    return new DirectorySourcePathElement(file);
  }
}