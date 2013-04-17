package de.jave.image.ant.test;

import de.jave.image.RescaleQualityHint;
import de.jave.image.ant.RescaleOptions;

/**
 * @author Markus Gebhard
 */
public class RescaleOptionsTest extends AbstractAntTestCase {

  public void testMustSpecifyWidthOrHeight() {
    RescaleOptions options = new RescaleOptions();
    assertCheckParametersFails(options);
  }

  public void testInvalidHeight1() {
    RescaleOptions options = new RescaleOptions();
    options.setHeight("%"); //$NON-NLS-1$
    assertCheckParametersFails(options);
  }

  public void testInvalidQualityHint() {
    RescaleOptions options = new RescaleOptions();
    options.setQualityHint("unsupported hint name"); //$NON-NLS-1$
    assertCheckParametersFails(options);
  }

  public void testInvalidHeight2() {
    RescaleOptions options = new RescaleOptions();
    options.setHeight("123asdasd"); //$NON-NLS-1$
    assertCheckParametersFails(options);
  }

  public void testInvalidHeight3() {
    RescaleOptions options = new RescaleOptions();
    options.setHeight("-3"); //$NON-NLS-1$
    assertCheckParametersFails(options);
  }

  public void testInvalidWidth1() {
    RescaleOptions options = new RescaleOptions();
    options.setWidth("%"); //$NON-NLS-1$
    assertCheckParametersFails(options);
  }

  public void testInvalidWidth2() {
    RescaleOptions options = new RescaleOptions();
    options.setWidth("123asdasd"); //$NON-NLS-1$
    assertCheckParametersFails(options);
  }

  public void testInvalidWidth3() {
    RescaleOptions options = new RescaleOptions();
    options.setWidth("-3"); //$NON-NLS-1$
    assertCheckParametersFails(options);
  }

  public void testInvalidWidth4() {
    RescaleOptions options = new RescaleOptions();
    options.setWidth("0"); //$NON-NLS-1$
    assertCheckParametersFails(options);
  }

  public void testInvalidWidth5() {
    RescaleOptions options = new RescaleOptions();
    options.setWidth("0%"); //$NON-NLS-1$
    assertCheckParametersFails(options);
  }

  public void testInvalidHeight4() {
    RescaleOptions options = new RescaleOptions();
    options.setHeight("0"); //$NON-NLS-1$
    assertCheckParametersFails(options);
  }

  public void testInvalidHeight5() {
    RescaleOptions options = new RescaleOptions();
    options.setHeight("0%"); //$NON-NLS-1$
    assertCheckParametersFails(options);
  }

  public void testGetQualityHint() {
    RescaleOptions options = new RescaleOptions();
    options.setQualityHint("replicate"); //$NON-NLS-1$
    assertEquals(RescaleQualityHint.REPLICATE, options.getRescaleQualityHint());
  }

  public void testWidthAsValue() {
    RescaleOptions options = new RescaleOptions();
    options.setWidth("110"); //$NON-NLS-1$
    options.checkParameters();
    assertTrue(options.isWidthValueSpecified());
    assertFalse(options.isHeightValueSpecified());
    assertFalse(options.isWidthPercentageSpecified());
    assertFalse(options.isHeightPercentageSpecified());
    assertEquals(110, options.getWidthValue());
  }

  public void testHeightAsValue() {
    RescaleOptions options = new RescaleOptions();
    options.setHeight("100"); //$NON-NLS-1$
    options.checkParameters();
    assertFalse(options.isWidthValueSpecified());
    assertTrue(options.isHeightValueSpecified());
    assertFalse(options.isWidthPercentageSpecified());
    assertFalse(options.isHeightPercentageSpecified());
    assertEquals(100, options.getHeightValue());
  }

  public void testHeightAsPercentage() {
    RescaleOptions options = new RescaleOptions();
    options.setHeight("90%"); //$NON-NLS-1$
    options.checkParameters();
    assertFalse(options.isWidthValueSpecified());
    assertFalse(options.isHeightValueSpecified());
    assertFalse(options.isWidthPercentageSpecified());
    assertTrue(options.isHeightPercentageSpecified());
    assertEquals(90, options.getHeightPercentage());
  }

  public void testWidthAsPercentage() {
    RescaleOptions options = new RescaleOptions();
    options.setWidth("80%"); //$NON-NLS-1$
    options.checkParameters();
    assertFalse(options.isWidthValueSpecified());
    assertFalse(options.isHeightValueSpecified());
    assertTrue(options.isWidthPercentageSpecified());
    assertFalse(options.isHeightPercentageSpecified());
    assertEquals(80, options.getWidthPercentage());
  }
}