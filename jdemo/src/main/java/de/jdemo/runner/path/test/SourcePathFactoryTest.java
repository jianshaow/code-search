package de.jdemo.runner.path.test;

import de.jdemo.runner.path.DirectorySourcePathElement;
import de.jdemo.runner.path.ISourcePath;
import de.jdemo.runner.path.SourcePathFactory;
import de.jdemo.runner.path.ZipSourcePathElement;

/**
 * @author Markus Gebhard
 */
public class SourcePathFactoryTest extends TestDataTestCase {
  private SourcePathFactory sourcePathFactory;

  protected void setUp() throws Exception {
    sourcePathFactory = new SourcePathFactory();
  }

  public void testEmptyPath() {
    ISourcePath sourcePath = sourcePathFactory.createSourcePath(""); //$NON-NLS-1$
    assertNotNull(sourcePath);
    assertEquals(0, sourcePath.getPathElementCount());
  }

  public void testSinglePathElement() {
    ISourcePath sourcePath = sourcePathFactory.createSourcePath("F:\\PRJ3\\JDemo\\examples"); //$NON-NLS-1$
    assertEquals(1, sourcePath.getPathElementCount());
    assertNotNull(sourcePath.getPathElement(0));
  }

  public void testTwoPathElements() {
    ISourcePath sourcePath = sourcePathFactory.createSourcePath("F:\\PRJ3\\JDemo\\examples" + //$NON-NLS-1$
        System.getProperty("path.separator") + //$NON-NLS-1$
        "F:\\PRJ3\\JDemo\\src"); //$NON-NLS-1$
    assertEquals(2, sourcePath.getPathElementCount());
    assertNotNull(sourcePath.getPathElement(0));
    assertTrue(sourcePath.getPathElement(0) instanceof DirectorySourcePathElement);
    assertNotNull(sourcePath.getPathElement(1));
    assertTrue(sourcePath.getPathElement(1) instanceof DirectorySourcePathElement);
  }

  public void testCreatesDirectoryPathElement() {
    ISourcePath sourcePath = sourcePathFactory.createSourcePath("F:\\PRJ3\\JDemo\\examples"); //$NON-NLS-1$
    assertTrue(sourcePath.getPathElement(0) instanceof DirectorySourcePathElement);
  }

  public void testCreatesJarPathElement() {
    ISourcePath sourcePath = sourcePathFactory.createSourcePath(JAR_FILE.getAbsolutePath());
    assertTrue(sourcePath.getPathElement(0) instanceof ZipSourcePathElement);
  }

  public void testCreatesZipPathElement() {
    ISourcePath sourcePath = sourcePathFactory.createSourcePath(ZIP_FILE.getAbsolutePath());
    assertTrue(sourcePath.getPathElement(0) instanceof ZipSourcePathElement);
  }
}