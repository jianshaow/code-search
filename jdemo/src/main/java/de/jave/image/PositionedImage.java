package de.jave.image;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * @author Markus Gebhard
 */
public class PositionedImage {

  private Point location;
  private BufferedImage image;

  public BufferedImage getImage() {
    return image;
  }

  public Point getLocation() {
    return location;
  }

  public PositionedImage(BufferedImage image, Point location) {
    this.image = image;
    this.location = location;
  }
}