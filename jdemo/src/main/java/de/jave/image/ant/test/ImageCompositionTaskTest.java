package de.jave.image.ant.test;

import java.io.File;

import org.apache.tools.ant.BuildException;

import de.jave.image.ant.ImageCompositionTask;
import de.jave.image.ant.PositionedImageFile;

/**
 * @author Markus Gebhard
 */
public class ImageCompositionTaskTest extends AbstractAntTestCase {
  public void testValid() {
    ImageCompositionTask task = new ImageCompositionTask();
    task.setOutputFile(new File("output.png")); //$NON-NLS-1$
    PositionedImageFile imageFile = new PositionedImageFile();
    imageFile.setFile(new File("image.gif")); //$NON-NLS-1$
    task.addConfiguredImage(imageFile);
    task.setBackgroundColor("00ff80"); //$NON-NLS-1$
    task.checkParameters();
  }

  public void testAddingInvalidImage() {
    ImageCompositionTask task = new ImageCompositionTask();
    task.setOutputFile(new File("output.png")); //$NON-NLS-1$
    try {
      task.addConfiguredImage(new PositionedImageFile());
      fail();
    }
    catch (BuildException expected) {
      //expected
    }
  }

  public void testMissingOutputFile() {
    ImageCompositionTask task = new ImageCompositionTask();
    PositionedImageFile imageFile = new PositionedImageFile();
    imageFile.setFile(new File("image.gif")); //$NON-NLS-1$
    task.addConfiguredImage(imageFile);
    assertCheckParametersFails(task);
  }
  
  public void testIllegalBackgroundColor() {
    ImageCompositionTask task = new ImageCompositionTask();
    task.setOutputFile(new File("output.png")); //$NON-NLS-1$
    PositionedImageFile imageFile = new PositionedImageFile();
    imageFile.setFile(new File("image.gif")); //$NON-NLS-1$
    task.addConfiguredImage(imageFile);
    task.setBackgroundColor("zzzzzz"); //$NON-NLS-1$
    assertCheckParametersFails(task);
  }
}