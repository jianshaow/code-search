package de.jave.image;



/**
 * @author Markus Gebhard
 */
public interface IRescaleProcessingOptions {

  public boolean isHeightValueSpecified();

  public int getHeightValue();

  public boolean isWidthValueSpecified();

  public int getWidthValue();

  public boolean isWidthPercentageSpecified();

  public int getWidthPercentage();

  public boolean isHeightPercentageSpecified();

  public int getHeightPercentage();

  public RescaleQualityHint getRescaleQualityHint();
}
