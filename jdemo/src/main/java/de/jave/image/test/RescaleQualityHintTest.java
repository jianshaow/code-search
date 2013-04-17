package de.jave.image.test;

import java.awt.Image;

import de.jave.image.RescaleQualityHint;

import junit.framework.TestCase;

/**
 * @author Markus Gebhard
 */
public class RescaleQualityHintTest extends TestCase {

  public void testGetAwtValue() {
    assertEquals(Image.SCALE_DEFAULT, RescaleQualityHint.DEFAULT.getAwtValue());
    assertEquals(Image.SCALE_SMOOTH, RescaleQualityHint.SMOOTH.getAwtValue());
    assertEquals(Image.SCALE_AREA_AVERAGING, RescaleQualityHint.AREA_AVERAGING.getAwtValue());
  }

  public void testGetByName() {
    assertEquals(null, RescaleQualityHint.getByName("unknown name")); //$NON-NLS-1$
    assertSame(RescaleQualityHint.FAST, RescaleQualityHint.getByName("Fast")); //$NON-NLS-1$
    assertSame(RescaleQualityHint.FAST, RescaleQualityHint.getByName("fast")); //$NON-NLS-1$
    assertSame(RescaleQualityHint.SMOOTH, RescaleQualityHint.getByName("smooth")); //$NON-NLS-1$
  }
}