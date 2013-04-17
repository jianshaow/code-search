package de.java2html.converter.test;

import junit.framework.TestCase;

import de.java2html.converter.IJavaSourceConverter;
import de.java2html.converter.JavaSourceConverterProvider;

/**
 * @author Markus Gebhard
 */
public class JavaSourceConverterProviderTest extends TestCase {

  public void testGetConverterNames() {
    final String[] names = JavaSourceConverterProvider.getAllConverterNames();
    assertNotNull(names);
    assertTrue(2 < names.length);
    for (int i = 0; i < names.length; ++i) {
      assertNotNull(names[i]);
    }
  }

  public void testGetConverterPrintNames() {
    final String[] names = JavaSourceConverterProvider.getAllConverterPrintNames();
    assertNotNull(names);
    assertTrue(2 < names.length);
    for (int i = 0; i < names.length; ++i) {
      assertNotNull(names[i]);
    }
  }

  public void testGetConvertersByName() {
    String[] names = JavaSourceConverterProvider.getAllConverterNames();
    for (int i = 0; i < names.length; i++) {
      final IJavaSourceConverter converter = JavaSourceConverterProvider.getJavaSourceConverterByName(names[i]);
      assertNotNull(converter);
      assertTrue(names[i].equalsIgnoreCase(converter.getMetaData().getName()));
    }
  }

  public void testGetConvertersByNameIgnoresCase() {
    String[] names = JavaSourceConverterProvider.getAllConverterNames();
    for (int i = 0; i < names.length; i++) {
      final IJavaSourceConverter converter1 = JavaSourceConverterProvider.getJavaSourceConverterByName(names[i]
          .toLowerCase());
      final IJavaSourceConverter converter2 = JavaSourceConverterProvider.getJavaSourceConverterByName(names[i]
          .toUpperCase());
      assertSame(converter1, converter2);
    }
  }

  public void testGetAllConvetersReturnsConverters() {
    IJavaSourceConverter[] allConverters = JavaSourceConverterProvider.getAllConverters();
    assertTrue(allConverters.length > 2);
    for (int i = 0; i < allConverters.length; ++i) {
      assertNotNull(allConverters[i]);
    }
  }
}