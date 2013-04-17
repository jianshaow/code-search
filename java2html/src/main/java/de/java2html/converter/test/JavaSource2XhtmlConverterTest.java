package de.java2html.converter.test;

import de.java2html.converter.IJavaSourceConverter;
import de.java2html.converter.JavaSource2XhtmlConverter;

public class JavaSource2XhtmlConverterTest extends AbstractJavaSourceConverterTest {

  protected IJavaSourceConverter createObjectUnderTest() {
    return new JavaSource2XhtmlConverter();
  }

}