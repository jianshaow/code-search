package de.java2html.options.test;

import de.java2html.options.HorizontalAlignment;
import junit.framework.TestCase;

/**
 * @author Markus Gebhard
 */
public class HorizontalAlignmentTest extends TestCase {
  public void testByName() {
    assertSame(HorizontalAlignment.LEFT, HorizontalAlignment.getByName(HorizontalAlignment.LEFT.getName()));
    assertSame(HorizontalAlignment.RIGHT, HorizontalAlignment.getByName(HorizontalAlignment.RIGHT.getName()));
    assertSame(HorizontalAlignment.CENTER, HorizontalAlignment.getByName(HorizontalAlignment.CENTER.getName()));
  }

  public void testLeftIsDefault() {
    assertSame(HorizontalAlignment.LEFT, HorizontalAlignment.getAll()[0]);
  }
}