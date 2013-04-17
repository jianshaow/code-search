package de.jdemo.runner.path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Markus Gebhard
 */
public abstract class AbstractSourcePathElement implements ISourcePathElement {
  protected final String createSourceFileName(final String className) {
    return className.replace('.', '/') + ".java"; //$NON-NLS-1$
  }

  protected final String loadSourceFile(final InputStream stream) throws IOException {
    return readString(new BufferedReader(new InputStreamReader(stream)));
  }

  private static String readString(final Reader reader) throws IOException {
    final StringBuffer buffer = new StringBuffer();
    final char[] buf = new char[1024];
    int numChars = 0;
    while ((numChars = reader.read(buf)) > 0) {
      buffer.append(buf, 0, numChars);
    }
    return buffer.toString();
  }
}