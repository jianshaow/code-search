package de.java2html.options.test;

import junit.framework.TestCase;
import de.java2html.options.JavaSourceStyleEntry;
import de.java2html.util.HtmlUtilities;
import de.java2html.util.RGB;

/**
 * @author Markus Gebhard
 */
public class JavaSourceStyleEntryTest extends TestCase {
  private JavaSourceStyleEntry entry;

  protected void setUp() throws Exception {
    entry = new JavaSourceStyleEntry(RGB.RED, true, false);
  }

  public void testCreate() {
    assertEquals(RGB.RED, entry.getColor());
    assertTrue(entry.isBold());
    assertFalse(entry.isItalic());
  }

  /** @deprecated As of Dec 21, 2003 (Markus Gebhard) */
  public void testCloneEquals() {
    assertEquals(entry, entry.getClone());
  }

  public void testSameEquals() {
    assertEquals(entry, entry);
  }

  public void testDifferentNotEquals() {
    JavaSourceStyleEntry other = new JavaSourceStyleEntry(RGB.GREEN, true, false);
    assertFalse(other.equals(entry));
  }

  public void testGetHtmlColor() {
    assertEquals(HtmlUtilities.toHTML(RGB.RED), entry.getHtmlColor());
  }
}