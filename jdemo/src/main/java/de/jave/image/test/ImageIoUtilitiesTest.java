package de.jave.image.test;

import de.jave.image.ImageIoUtilities;
import junit.framework.TestCase;

/**
 * @author Markus Gebhard
 */
public class ImageIoUtilitiesTest extends TestCase {
  public void testGuessImageFormat() {
    assertEquals("png", ImageIoUtilities.guessImageFormat("test.png")); //$NON-NLS-1$ //$NON-NLS-2$
    assertEquals("gif", ImageIoUtilities.guessImageFormat("test.name.gif")); //$NON-NLS-1$ //$NON-NLS-2$
    assertNull(ImageIoUtilities.guessImageFormat("testnamegif")); //$NON-NLS-1$
  }
}