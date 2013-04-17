package de.java2html.converter.test;

import de.java2html.converter.IJavaSourceConverter;
import de.java2html.converter.JavaSource2XmlConverter;

public class JavaSource2XmlConverterTest extends AbstractJavaSourceConverterTest {

  protected IJavaSourceConverter createObjectUnderTest() {
    return new JavaSource2XmlConverter();
  }

}