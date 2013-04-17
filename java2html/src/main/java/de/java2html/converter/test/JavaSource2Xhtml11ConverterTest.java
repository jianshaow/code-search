package de.java2html.converter.test;

import de.java2html.converter.IJavaSourceConverter;
import de.java2html.converter.JavaSource2Xhtml11Converter;

public class JavaSource2Xhtml11ConverterTest extends AbstractJavaSourceConverterTest {

  protected IJavaSourceConverter createObjectUnderTest() {
    return new JavaSource2Xhtml11Converter();
  }

}