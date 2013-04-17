package de.java2html.converter.test;

import junit.framework.TestCase;

import de.java2html.converter.IJavaSourceConverter;

public abstract class AbstractJavaSourceConverterTest extends TestCase {

  private IJavaSourceConverter converter;

  protected void setUp() throws Exception {
    super.setUp();
    converter = createObjectUnderTest();
  }

  protected abstract IJavaSourceConverter createObjectUnderTest();

  public void testGetMetaData() {
    assertNotNull(converter.getMetaData().getName());
    assertNotNull(converter.getMetaData().getPrintName());
    assertEquals(converter.getMetaData().getDefaultFileExtension(), converter.getDefaultFileExtension());
    assertNotNull(converter.getMetaData().getDefaultFileExtension());
  }

}