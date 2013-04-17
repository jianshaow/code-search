package de.java2html.test;

import junit.framework.TestCase;

/**
 * @author Markus Gebhard
 */
public abstract class BasicTestCase extends TestCase {
  public static void assertThrowsException(Class expectedExceptionClass, IBlock block) {
    try {
      block.execute();
      fail("Exception of type " + expectedExceptionClass + " expected.");  //$NON-NLS-1$//$NON-NLS-2$
    }
    catch (Exception exception) {
      assertTrue("Exception of type " //$NON-NLS-1$
          + expectedExceptionClass
          + " expecrted, but was " //$NON-NLS-1$
          + exception.getClass(), expectedExceptionClass.isAssignableFrom(exception.getClass()));
    }
  }

  public static void assertInstanceOf(Class expectedClass, Object actualObject) {
    assertNotNull(actualObject);
    assertTrue(expectedClass.isAssignableFrom(actualObject.getClass()));
  }

  public static interface IBlock {
    public void execute() throws Exception;
  }

}