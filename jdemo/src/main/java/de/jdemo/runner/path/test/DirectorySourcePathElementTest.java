package de.jdemo.runner.path.test;

import java.io.File;
import java.io.IOException;

import de.jdemo.runner.path.DirectorySourcePathElement;
import de.jdemo.runner.path.ISourcePathElement;

/**
 * @author Markus Gebhard
 */
public class DirectorySourcePathElementTest extends TestDataTestCase {
  private static final File DIRECTORY = TEST_DATA_DIR;
  private static final File JAVA_CLASS_File1 = new File(DIRECTORY, "TestDataClass1.java"); //$NON-NLS-1$
  private static final File JAVA_CLASS_File2 = new File(DIRECTORY, "de/TestDataClass2.java"); //$NON-NLS-1$
  private static final String JAVA_CLASS_NAME1 = "TestDataClass1"; //$NON-NLS-1$
  private static final String JAVA_CLASS_NAME2 = "de.TestDataClass2"; //$NON-NLS-1$
  private ISourcePathElement pathElement;

  protected void setUp() throws Exception {
    pathElement = new DirectorySourcePathElement(DIRECTORY);
  }

  public void testTestPreconditions() {
    assertTrue(DIRECTORY.exists());
    assertTrue(JAVA_CLASS_File1.exists());
    assertTrue(JAVA_CLASS_File2.exists());
  }

  public void testToString() {
    assertEquals(DIRECTORY.getName(), pathElement.toString());
  }

  public void testExistingSourceFilesExist() {
    assertTrue(pathElement.contains(JAVA_CLASS_NAME1));
    assertTrue(pathElement.contains(JAVA_CLASS_NAME2));
  }

  public void testNonExistingSourceFilesNotExist() {
    assertFalse(pathElement.contains(JAVA_CLASS_NAME1 + "xyz")); //$NON-NLS-1$
    assertFalse(pathElement.contains("123" + JAVA_CLASS_NAME1)); //$NON-NLS-1$
    assertFalse(pathElement.contains("")); //$NON-NLS-1$
  }

  public void testLoadNonExistingSourceFiles() throws IOException {
    assertNull(pathElement.load(JAVA_CLASS_NAME1 + "xyz")); //$NON-NLS-1$
    assertNull(pathElement.load("123" + JAVA_CLASS_NAME1)); //$NON-NLS-1$
    assertNull(pathElement.load("")); //$NON-NLS-1$
  }

  public void testLoadExistingSourceFiles() throws IOException {
    assertNotNull(pathElement.load(JAVA_CLASS_NAME1));
    assertNotNull(pathElement.load(JAVA_CLASS_NAME2));
  }
}