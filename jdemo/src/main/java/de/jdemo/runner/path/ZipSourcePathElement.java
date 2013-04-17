package de.jdemo.runner.path;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import de.jdemo.util.IOUtilities;

/**
 * @author Markus Gebhard
 */
public class ZipSourcePathElement extends AbstractSourcePathElement {
  private final ZipFile zipFile;
  private final String fileName;

  public ZipSourcePathElement(final File file) throws IOException {
    fileName = file.getName();
    zipFile = new ZipFile(file, ZipFile.OPEN_READ);
  }

  @Override
  public boolean contains(final String className) {
    return createZipEntry(className) != null;
  }

  private ZipEntry createZipEntry(final String className) {
    return zipFile.getEntry(createSourceFileName(className));
  }

  @Override
  public String load(final String className) throws IOException {
    final ZipEntry entry = createZipEntry(className);
    if (entry == null) {
      return null;
    }
    InputStream inputStream = null;
    try {
      inputStream = zipFile.getInputStream(entry);
      return loadSourceFile(inputStream);
    }
    finally {
      IOUtilities.close(inputStream);
    }
  }

  @Override
  public String toString() {
    return fileName;
  }
}