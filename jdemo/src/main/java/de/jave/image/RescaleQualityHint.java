package de.jave.image;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Markus Gebhard
 */
public class RescaleQualityHint {
  private final static List ALL = new ArrayList();
  private String name;
  private int awtValue;

  private RescaleQualityHint(String name, int awtValue) {
    ALL.add(this);
    this.awtValue = awtValue;
    this.name = name;
  }

  public static final RescaleQualityHint SMOOTH = new RescaleQualityHint(
      "Smooth", //$NON-NLS-1$
      Image.SCALE_SMOOTH);
  public static final RescaleQualityHint AREA_AVERAGING = new RescaleQualityHint(
      "AreaAveraging", //$NON-NLS-1$
      Image.SCALE_AREA_AVERAGING);
  public static final RescaleQualityHint DEFAULT = new RescaleQualityHint(
      "Default", //$NON-NLS-1$
      Image.SCALE_DEFAULT);
  public static final RescaleQualityHint FAST = new RescaleQualityHint("Fast", Image.SCALE_FAST); //$NON-NLS-1$
  public static final RescaleQualityHint REPLICATE = new RescaleQualityHint(
      "Replicate", //$NON-NLS-1$
      Image.SCALE_REPLICATE);

  public int getAwtValue() {
    return awtValue;
  }

  public String toString() {
    return name;
  }

  public static RescaleQualityHint getByName(String name) {
    for (int i = 0; i < ALL.size(); i++) {
      if (name.equalsIgnoreCase(((RescaleQualityHint) ALL.get(i)).getName())) {
        return (RescaleQualityHint) ALL.get(i);
      }
    }
    return null;
  }

  public static String getAllNamesAsCommaSeparatedString() {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < ALL.size(); i++) {
      if (i > 0) {
        sb.append(", "); //$NON-NLS-1$
      }
      sb.append(((RescaleQualityHint) ALL.get(i)).getName());
    }
    return sb.toString();
  }

  public String getName() {
    return name;
  }
}