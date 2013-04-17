package de.java2html.options.test;

import de.java2html.options.JavaSourceConversionOptions;
import junit.framework.TestCase;

/**
 * @author Markus Gebhard
 */
public class JavaSourceConversionOptionsTest extends TestCase {
  public void testEqualEquals() {
    assertEquals(JavaSourceConversionOptions.getDefault(), JavaSourceConversionOptions.getDefault());
  }
}