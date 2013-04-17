package de.java2html.util.test;

import de.java2html.util.LinkedProperties;
import junit.framework.TestCase;

/**
 * @author Markus Gebhard
 */
public class LinkedPropertiesTest extends TestCase {
  private LinkedProperties properties;

  protected void setUp() throws Exception {
    properties = new LinkedProperties();
  }

  public void testCreate() {
    assertEquals(0, properties.size());
    assertTrue(properties.isEmpty());
  }

  public void testSetProperty() {
    properties.setProperty("key", "value");  //$NON-NLS-1$//$NON-NLS-2$
    assertEquals("value", properties.get("key")); //$NON-NLS-1$ //$NON-NLS-2$
    assertEquals("value", properties.getProperty("key")); //$NON-NLS-1$ //$NON-NLS-2$
  }

  public void testGetDefaultValue() {
    assertEquals("default", properties.getProperty("key", "default"));  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
  }
}