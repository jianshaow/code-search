package de.jave.image.ant;

import java.awt.Point;
import java.io.File;

import org.apache.tools.ant.BuildException;

/**
 * @author Markus Gebhard
 */
public class PositionedImageFile implements IAntParameterCheckable {
  private File file;
  private int x = 0;
  private int y = 0;

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public Point getLocation() {
    return new Point(x, y);
  }

  public void checkParameters() {
    if (file == null) {
      throw new BuildException("Required parameter 'file' is not set."); //$NON-NLS-1$
    }
  }
}