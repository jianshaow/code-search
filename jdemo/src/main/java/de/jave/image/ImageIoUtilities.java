package de.jave.image;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Markus Gebhard
 */
public class ImageIoUtilities {
  public static void write(RenderedImage image, String format, File file) throws IOException {
    //    ImageIO.scanForPlugins();
    if (format == null) {
      format = guessImageFormat(file.getName());
    }
    if (format == null) {
      throw new IllegalArgumentException("No image format specified and unable to guess from file name '" //$NON-NLS-1$
          + file.getAbsolutePath()
          + "'"); //$NON-NLS-1$
    }

    File folder = file.getParentFile();
    if (folder != null) {
      folder.mkdirs();
    }
    boolean written = ImageIO.write(image, format, file);
    if (!written) {
      throw new IOException("Unable to write image in format '" //$NON-NLS-1$
          + format + "'. Supported formats: " //$NON-NLS-1$
          + getAvailableFormatNamesAsString());
    }
  }

  public static String guessImageFormat(String fileName) {
    int index = fileName.lastIndexOf('.');
    if (index == -1) {
      return null;
    }
    return fileName.substring(index + 1);
  }

  private static String getAvailableFormatNamesAsString() {
    String[] formatNames = ImageIO.getWriterFormatNames();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < formatNames.length; ++i) {
      sb.append(formatNames[i]);
      if (i < formatNames.length - 1) {
        sb.append(", "); //$NON-NLS-1$
      }
    }
    return sb.toString();
  }
}
