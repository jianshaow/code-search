package de.jave.image.ant.test;

import java.io.File;

import org.apache.tools.ant.BuildException;

import de.jave.image.ant.ImageProcessingTask;
import de.jave.image.ant.RescaleOptions;

/**
 * @author Markus Gebhard
 */
public class ImageProcessingTaskTest extends AbstractAntTestCase {
  public void testValid() {
    ImageProcessingTask task = new ImageProcessingTask();
    task.setSrcFile(new File("src.png")); //$NON-NLS-1$
    task.setOutputFile(new File("output.png")); //$NON-NLS-1$
    task.checkParameters();
  }

  public void testValid2() {
    ImageProcessingTask task = new ImageProcessingTask();
    task.setSrcFile(new File("src.png")); //$NON-NLS-1$
    task.setOutputFile(new File("output.png")); //$NON-NLS-1$
    RescaleOptions rescale = new RescaleOptions();
    rescale.setWidth("100"); //$NON-NLS-1$
    task.addConfiguredRescale(rescale);
    task.checkParameters();
  }

  public void testMissingSourceFile() {
    ImageProcessingTask task = new ImageProcessingTask();
    //task.setSrcFile(new File("src.png"));
    task.setOutputFile(new File("output.png")); //$NON-NLS-1$
    assertCheckParametersFails(task);
  }

  public void testMissingOutputFile() {
    ImageProcessingTask task = new ImageProcessingTask();
    task.setSrcFile(new File("src.png")); //$NON-NLS-1$
    //task.setOutputFile(new File("output.png"));
    assertCheckParametersFails(task);
  }

  public void testAddingInvalidProcessing() {
    ImageProcessingTask task = new ImageProcessingTask();
    RescaleOptions invalidRescale = new RescaleOptions();
    try {
      task.addConfiguredRescale(invalidRescale);
      task.checkParameters();
      fail();
    }
    catch (BuildException expected) {
      //expected
    }
  }
}