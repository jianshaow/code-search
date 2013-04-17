package de.jave.image.ant;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

import de.jave.image.IImageProcessing;
import de.jave.image.RescaleProcessing;

/**
 * @author Markus Gebhard
 */
public class ImageProcessingTask extends AbstractImagingTask {
  private List /*<IImageProcessing>*/processings = new ArrayList();
  private File srcFile;
  private String imageFormat;
  private File outputFile;

  public void setOutputFile(File outputFile) {
    this.outputFile = outputFile;
  }

  public void setSrcFile(File srcFile) {
    this.srcFile = srcFile;
  }

  public void setImageFormat(String imageFormat) {
    this.imageFormat = imageFormat;
  }

  public void checkParameters() {
    if (srcFile == null) {
      throw new BuildException("Missing required parameter 'srcFile'."); //$NON-NLS-1$
    }
    if (outputFile == null) {
      throw new BuildException("Missing required parameter 'outputFile'."); //$NON-NLS-1$
    }
  }

  public void addConfiguredRescale(RescaleOptions rescaleOptions) {
    rescaleOptions.checkParameters();
    processings.add(new RescaleProcessing(rescaleOptions));
  }

  public final void execute() throws BuildException {
    checkParameters();
    log("Processing image...", Project.MSG_INFO); //$NON-NLS-1$

    BufferedImage image = loadImage(srcFile);
    for (int i = 0; i < processings.size(); ++i) {
      IImageProcessing processing = (IImageProcessing) processings.get(i);
      image = processing.process(image);
    }
    writeImage(image, outputFile, imageFormat);
  }
}