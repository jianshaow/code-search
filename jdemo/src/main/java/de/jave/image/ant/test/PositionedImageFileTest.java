package de.jave.image.ant.test;

import java.io.File;

import de.jave.image.ant.PositionedImageFile;

/**
 * @author Markus Gebhard
 */
public class PositionedImageFileTest extends AbstractAntTestCase {
  public void testValidSetup1() {
    PositionedImageFile file = new PositionedImageFile();
    file.setFile(new File("test.txt")); //$NON-NLS-1$
    file.checkParameters();
  }

  public void testValidSetup2() {
    PositionedImageFile file = new PositionedImageFile();
    file.setFile(new File("test.txt")); //$NON-NLS-1$
    file.setX(10);
    file.checkParameters();
  }

  public void testValidSetup3() {
    PositionedImageFile file = new PositionedImageFile();
    file.setFile(new File("test.txt")); //$NON-NLS-1$
    file.setY(10);
    file.checkParameters();
  }

  public void testValidSetup4() {
    PositionedImageFile file = new PositionedImageFile();
    file.setFile(new File("test.txt")); //$NON-NLS-1$
    file.setX(10);
    file.setY(42);
    file.checkParameters();
  }
  
  public void testMissingFile() {
    PositionedImageFile file = new PositionedImageFile();
    assertCheckParametersFails(file);
  }
}