package de.jave.image.ant;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

import de.jave.image.ImageComposition;
import de.jave.image.PositionedImage;

/**
 * @author Markus Gebhard
 */
public class ImageCompositionTask extends AbstractImagingTask {
  private String backgroundColor = "FFFFFF"; //$NON-NLS-1$
  private String imageFormat;
  private File outputFile;
  private List/*<PositionedImageFile>*/images = new ArrayList();

  public void setImageFormat(String imageFormat) {
    this.imageFormat = imageFormat;
  }

  public void setBackgroundColor(String backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public void setOutputFile(File outputFile) {
    this.outputFile = outputFile;
  }

  public void addConfiguredImage(PositionedImageFile image) {
    image.checkParameters();
    images.add(image);
  }

  public final void execute() throws BuildException {
    checkParameters();
    log("Creating image composition...", Project.MSG_INFO); //$NON-NLS-1$

    Color color = getBackgroundColor();
    ImageComposition composition = new ImageComposition(color);
    for (int i = 0; i < images.size(); i++) {
      PositionedImageFile imageFile = (PositionedImageFile) images.get(i);
      BufferedImage image = loadImage(imageFile.getFile());
      composition.addImage(new PositionedImage(image, imageFile.getLocation()));
    }
    log("creating composition..", Project.MSG_INFO); //$NON-NLS-1$
    BufferedImage image = composition.createComposedImage();
    writeImage(image, outputFile, imageFormat);
  }

  private Color getBackgroundColor() {
    if (backgroundColor.length() != 6) {
      throw new BuildException("Illegal format for parameter 'backgroundColor': '" //$NON-NLS-1$
          + backgroundColor
          + "'. 6 digit hex value expected."); //$NON-NLS-1$
    }
    try {
      int red = Integer.parseInt(backgroundColor.substring(0, 2), 16);
      int green = Integer.parseInt(backgroundColor.substring(2, 4), 16);
      int blue = Integer.parseInt(backgroundColor.substring(4, 6), 16);
      return new Color(red, green, blue);
    }
    catch (NumberFormatException e) {
      throw new BuildException("Illegal format for parameter 'backgroundColor': '" //$NON-NLS-1$
          + backgroundColor
          + "'. 6 digit hex value expected."); //$NON-NLS-1$
    }
  }

  public void checkParameters() {
    if (outputFile == null) {
      throw new BuildException("Required parameter 'outputFile' is not set."); //$NON-NLS-1$
    }
    if (images.size() == 0) {
      throw new BuildException("Missing required nested elements 'image'."); //$NON-NLS-1$
    }
    getBackgroundColor();
  }
}