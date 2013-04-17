package de.java2html.options.test;

import junit.framework.TestCase;
import de.java2html.javasource.JavaSourceType;
import de.java2html.options.JavaSourceStyleEntry;
import de.java2html.options.JavaSourceStyleTable;
import de.java2html.util.RGB;

/**
 * @author Markus Gebhard
 */
public class JavaSourceStyleTableTest extends TestCase {
  public void testEqualEquals() {
    assertEquals(JavaSourceStyleTable.getDefault(), JavaSourceStyleTable.getDefault());
  }

  public void testDifferentNotEquals() {
    assertFalse(
      JavaSourceStyleTable.getDefaultEclipseStyleTable().equals(JavaSourceStyleTable.getDefaultKawaStyleTable()));
  }

  /** @deprecated */
  public void testDifferentNameNotEquals() {
    JavaSourceStyleTable modifiedTable = JavaSourceStyleTable.getDefaultEclipseStyleTable();
    modifiedTable.setName(modifiedTable.getName() + "*");
    assertFalse(modifiedTable.equals(JavaSourceStyleTable.getDefaultEclipseStyleTable()));
  }

  public void testAdditionalEntryNotEquals() {
    JavaSourceStyleTable modifiedTable = JavaSourceStyleTable.getDefaultEclipseStyleTable();
    modifiedTable.put("kkkkkkk", new JavaSourceStyleEntry(RGB.RED));
    assertFalse(modifiedTable.equals(JavaSourceStyleTable.getDefaultEclipseStyleTable()));
  }

  public void testModifiedEntryNotEquals() {
    JavaSourceStyleTable modifiedTable = JavaSourceStyleTable.getDefaultEclipseStyleTable();
    JavaSourceStyleEntry entry = modifiedTable.get(JavaSourceType.CODE);
    modifiedTable.put(
      JavaSourceType.CODE,
      new JavaSourceStyleEntry(entry.getColor(), !entry.isBold(), entry.isItalic()));
    assertFalse(modifiedTable.equals(JavaSourceStyleTable.getDefaultEclipseStyleTable()));
  }
}