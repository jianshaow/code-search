package de.jave.image.ant;

import org.apache.tools.ant.BuildException;

import de.jave.image.IRescaleProcessingOptions;
import de.jave.image.RescaleQualityHint;

/**
 * @author Markus Gebhard
 */
public class RescaleOptions implements IRescaleProcessingOptions, IAntParameterCheckable {
  private String width;
  private String height;
  private String qualityHint = RescaleQualityHint.SMOOTH.getName();

  public void setHeight(String height) {
    this.height = height;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public void checkParameters() {
    if (width == null && height == null) {
      throw new BuildException("Missing attribute 'width' or 'height'"); //$NON-NLS-1$
    }
    if (qualityHint != null) {
      if (RescaleQualityHint.getByName(qualityHint) == null) {
        throw new BuildException("Illegal value '" //$NON-NLS-1$
            + qualityHint
            + "' for quality hint. Supported values:" //$NON-NLS-1$
            + RescaleQualityHint.getAllNamesAsCommaSeparatedString());
      }
    }
    if (width != null) {
      if (!isPercentage(width) && !isPositiveNumber(width)) {
        throw new BuildException("Attribute width must be a positive number or percentage, was '" //$NON-NLS-1$
            + width
            + "'."); //$NON-NLS-1$
      }
    }
    else {
      if (!isPercentage(height) && !isPositiveNumber(height)) {
        throw new BuildException("Attribute height must be a positive number or percentage, was '" //$NON-NLS-1$
            + height
            + "'."); //$NON-NLS-1$
      }
    }
  }

  private boolean isPositiveNumber(String value) {
    try {
      int i = Integer.parseInt(value);
      if (i <= 0) {
        return false;
      }
      return true;
    }
    catch (NumberFormatException e) {
      return false;
    }
  }

  private boolean isPercentage(String value) {
    return value.endsWith("%") && isPositiveNumber(value.substring(0, value.length() - 1)); //$NON-NLS-1$
  }

  public boolean isHeightValueSpecified() {
    return height != null && isPositiveNumber(height);
  }

  public int getHeightValue() {
    return Integer.parseInt(height);
  }

  public boolean isWidthValueSpecified() {
    return width != null && isPositiveNumber(width);
  }

  public int getWidthValue() {
    return Integer.parseInt(width);
  }

  public boolean isWidthPercentageSpecified() {
    return width != null && isPercentage(width);
  }

  public int getWidthPercentage() {
    return Integer.parseInt(width.substring(0, width.length() - 1));
  }

  public boolean isHeightPercentageSpecified() {
    return height != null && isPercentage(height);
  }

  public int getHeightPercentage() {
    return Integer.parseInt(height.substring(0, height.length() - 1));
  }

  public RescaleQualityHint getRescaleQualityHint() {
    if (qualityHint==null) {
      return RescaleQualityHint.SMOOTH;
    }
    return RescaleQualityHint.getByName(qualityHint);
  }
  
  public void setQualityHint(String qualityHint){
    this.qualityHint = qualityHint;
  }
}