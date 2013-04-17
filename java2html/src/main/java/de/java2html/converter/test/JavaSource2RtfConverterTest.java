package de.java2html.converter.test;

import de.java2html.converter.IJavaSourceConverter;
import de.java2html.converter.JavaSource2RtfConverter;

public class JavaSource2RtfConverterTest extends AbstractJavaSourceConverterTest {

  protected IJavaSourceConverter createObjectUnderTest() {
    return new JavaSource2RtfConverter();
  }

}