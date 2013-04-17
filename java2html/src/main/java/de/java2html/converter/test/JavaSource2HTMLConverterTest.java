package de.java2html.converter.test;

import de.java2html.converter.IJavaSourceConverter;
import de.java2html.converter.JavaSource2HTMLConverter;

public class JavaSource2HTMLConverterTest extends AbstractJavaSourceConverterTest {

  protected IJavaSourceConverter createObjectUnderTest() {
    return new JavaSource2HTMLConverter();
  }

}