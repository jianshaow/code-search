package de.java2html.util.test;

import java.io.File;

import de.java2html.util.IoUtilities;
import junit.framework.TestCase;

/**
 * @author Markus Gebhard
 */
public class IoUtilitiesTest extends TestCase {
  public void testExchangeFileExtension() {
    assertEquals(new File("c:/test.txt"), IoUtilities.exchangeFileExtension( //$NON-NLS-1$
        new File("c:/test.java"), //$NON-NLS-1$
        "txt")); //$NON-NLS-1$
  }
}