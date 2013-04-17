package de.jave.image;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;


/**
 * @author Markus Gebhard
 */
public class RescaleProcessing implements IImageProcessing {

  private IRescaleProcessingOptions processingOptions;

  public RescaleProcessing(IRescaleProcessingOptions processingOptions) {
    this.processingOptions = processingOptions;
  }

  public BufferedImage process(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();

    int newWidth;
    int newHeight;

    if (processingOptions.isHeightValueSpecified()) {
      newHeight = processingOptions.getHeightValue();
      newWidth = width * newHeight / height;
    }
    else if (processingOptions.isWidthValueSpecified()) {
      newWidth = processingOptions.getWidthValue();
      newHeight = height * newWidth / width;
    }
    else if (processingOptions.isHeightPercentageSpecified()) {
      newHeight = height * processingOptions.getHeightPercentage() / 100;
      newWidth = width * newHeight / height;
    }
    else if (processingOptions.isWidthPercentageSpecified()) {
      newWidth = width * processingOptions.getHeightPercentage() / 100;
      newHeight = height * newWidth / width;
    }
    else {
      return image;
    }

    Image scaledImage = image.getScaledInstance(newWidth, newHeight, processingOptions
        .getRescaleQualityHint()
        .getAwtValue());

    BufferedImage bi = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
    Graphics g = bi.getGraphics();
    g.drawImage(scaledImage, 0, 0, null);
    g.dispose();
    return bi;
  }
}