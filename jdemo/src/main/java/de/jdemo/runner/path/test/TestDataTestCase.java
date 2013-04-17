package de.jdemo.runner.path.test;

import java.io.File;

import junit.framework.TestCase;

/**
 * @author Markus Gebhard
 */
public abstract class TestDataTestCase extends TestCase {
  protected static final File TEST_DATA_DIR = new File("./testdata/"); //$NON-NLS-1$
  protected static final File JAR_FILE = new File(TEST_DATA_DIR, "TestDataClass4.jar"); //$NON-NLS-1$
  protected static final File ZIP_FILE = new File(TEST_DATA_DIR, "TestDataClass3.zip"); //$NON-NLS-1$
}