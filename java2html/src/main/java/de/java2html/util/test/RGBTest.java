package de.java2html.util.test;

import de.java2html.util.RGB;
import junit.framework.TestCase;

/**
 * @author Markus Gebhard
 */
public class RGBTest extends TestCase {

  public void testCreate() {
    RGB rgb = new RGB(1, 2, 3);
    assertEquals(1, rgb.getRed());
    assertEquals(2, rgb.getGreen());
    assertEquals(3, rgb.getBlue());
  }

  public void testIllegalArgumentsInConstructor() {
    assertConstructorArgumentsThrowsIllegalArgumentException(-1, 0, 0);
    assertConstructorArgumentsThrowsIllegalArgumentException(0, -1, 0);
    assertConstructorArgumentsThrowsIllegalArgumentException(0, 0, -1);
    assertConstructorArgumentsThrowsIllegalArgumentException(256, 0, 0);
    assertConstructorArgumentsThrowsIllegalArgumentException(0, 256, 0);
    assertConstructorArgumentsThrowsIllegalArgumentException(0, 0, 256);
  }

  private void assertConstructorArgumentsThrowsIllegalArgumentException(int red, int green, int blue) {
    try {
      new RGB(red, green, blue);
      fail();
    }
    catch (IllegalArgumentException expected) {
      //expected  
    }
  }

  public void testSameEquals() {
    RGB rgb = new RGB(1, 2, 3);
    assertEquals(rgb, rgb);
  }

  public void testEqualEquals() {
    assertEquals(new RGB(1, 2, 3), new RGB(1, 2, 3));
  }

  public void testEqualHasEqualHashCode() {
    assertEquals(new RGB(1, 2, 3).hashCode(), new RGB(1, 2, 3).hashCode());
  }

  public void testDifferentNotEquals() {
    assertFalse(new RGB(0, 0, 0).equals(new RGB(1, 0, 0)));
    assertFalse(new RGB(0, 0, 0).equals(new RGB(0, 1, 0)));
    assertFalse(new RGB(0, 0, 0).equals(new RGB(0, 0, 1)));
  }
}