package de.jdemo.runner.path;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import de.jdemo.util.IOUtilities;

/**
 * @author Markus Gebhard
 */
public class DirectorySourcePathElement extends AbstractSourcePathElement {

  private final File directory;

  public DirectorySourcePathElement(final File directory) {
    this.directory = directory;
  }

  @Override
  public boolean contains(final String className) {
    return createSourceFile(className).exists();
  }

  private File createSourceFile(final String className) {
    return new File(directory, createSourceFileName(className));
  }

  @Override
  public String load(final String className) throws IOException {
    final File file = createSourceFile(className);
    if (!file.exists()) {
      return null;
    }
    FileInputStream inputStream = null;
    try {
      inputStream = new FileInputStream(file);
      return loadSourceFile(inputStream);
    }
    finally {
      IOUtilities.close(inputStream);
    }
  }

  @Override
  public String toString() {
    return directory.getName();
  }
}