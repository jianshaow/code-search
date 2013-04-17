package de.jave.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Markus Gebhard
 */
public class ImageComposition {

  private Color backgroundColor;
  private List/*<PositionedImage>*/images = new ArrayList();

  public ImageComposition(Color backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public void addImage(PositionedImage image) {
    images.add(image);
  }

  public BufferedImage createComposedImage() {
    Dimension size = getTotalSize();
    BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
    Graphics graphics = image.getGraphics();
    graphics.setColor(backgroundColor);
    graphics.fillRect(0, 0, size.width, size.height);
    for (int i = 0; i < images.size(); i++) {
      PositionedImage positionedImage = (PositionedImage) images.get(i);
      graphics.drawImage(positionedImage.getImage(), positionedImage.getLocation().x, positionedImage
          .getLocation().y, null);
    }
    graphics.dispose();
    return image;
  }

  private Dimension getTotalSize() {
    int maxWidth = 0;
    int maxHeight = 0;
    for (int i = 0; i < images.size(); i++) {
      PositionedImage positionedImage = (PositionedImage) images.get(i);
      int width = positionedImage.getLocation().x + positionedImage.getImage().getWidth();
      int height = positionedImage.getLocation().y + positionedImage.getImage().getHeight();
      if (width > maxWidth) {
        maxWidth = width;
      }
      if (height > maxHeight) {
        maxHeight = height;
      }
    }
    return new Dimension(maxWidth, maxHeight);
  }
}