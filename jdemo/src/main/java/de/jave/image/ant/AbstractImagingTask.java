package de.jave.image.ant;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import de.jave.image.ImageIoUtilities;

/**
 * @author Markus Gebhard
 */
public abstract class AbstractImagingTask extends Task implements IAntParameterCheckable {

  protected BufferedImage loadImage(File file) {
    log("loading image '" + file.getAbsolutePath() + "'..", Project.MSG_INFO); //$NON-NLS-1$ //$NON-NLS-2$
    BufferedImage image;
    try {
      image = ImageIO.read(file);
    }
    catch (IOException e) {
      throw new BuildException("Unable to read image '" + file.getAbsolutePath() + "'", e); //$NON-NLS-1$ //$NON-NLS-2$
    }
    if (image == null) {
      throw new BuildException("No image reader found for reading image '" + file.getAbsolutePath() + "'"); //$NON-NLS-1$ //$NON-NLS-2$
    }
    return image;
  }


  protected void writeImage(RenderedImage image, File file, String format) {
    try {
      log("...writing to '" + file.getAbsolutePath() + "'.", Project.MSG_INFO); //$NON-NLS-1$ //$NON-NLS-2$
      ImageIoUtilities.write(image, format, file);
    }
    catch (IOException e) {
      throw new BuildException("Unable to write image.", e); //$NON-NLS-1$
    }
  }
}