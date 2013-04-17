package de.jdemo.runner.path.test;

import java.io.IOException;

import junit.framework.TestCase;

import de.jdemo.runner.path.ISourcePathElement;
import de.jdemo.runner.path.SourcePath;

/**
 * @author Markus Gebhard
 */
public class SourcePathTest extends TestCase {

  private final ISourcePathElement nothingContainingPathElement = new ISourcePathElement() {
    public boolean contains(final String className) {
      return false;
    }

    public String load(final String className) {
      throw new IllegalStateException();
    }
  };

  private final ISourcePathElement everyThingContainingPathElement = new ISourcePathElement() {
    public boolean contains(final String className) {
      return true;
    }

    public String load(final String className) {
      return "42"; //$NON-NLS-1$
    }
  };

  public void testNotExistingPathEntry() throws IOException {
    final SourcePath sourcePath = new SourcePath(new ISourcePathElement[]{ nothingContainingPathElement });
    assertFalse(sourcePath.contains("test")); //$NON-NLS-1$
    assertEquals(null, sourcePath.load("test")); //$NON-NLS-1$
  }

  public void testLoadsFromSecondElement() throws IOException {
    final SourcePath sourcePath = new SourcePath(new ISourcePathElement[]{
        nothingContainingPathElement,
        everyThingContainingPathElement });
    assertTrue(sourcePath.contains("test")); //$NON-NLS-1$
    assertEquals("42", sourcePath.load("test")); //$NON-NLS-1$ //$NON-NLS-2$
  }
}