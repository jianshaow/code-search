package de.jdemo.swingui.util;

public class JDemoEnsure {

  private JDemoEnsure() {
    //nothing to do
  }

  public static void ensureArgumentNotNull(final String message, final Object object)
      throws IllegalArgumentException {
    ensureTrue(message, object != null);
  }

  public static void ensureArgumentNotNull(final Object object) throws IllegalArgumentException {
    ensureArgumentNotNull("Object must not be null", object); //$NON-NLS-1$
  }

  public static void ensureArgumentFalse(final boolean state) throws IllegalArgumentException {
    ensureTrue("boolean must be false", !state); //$NON-NLS-1$
  }

  public static void ensureArgumentFalse(final String message, final boolean state)
      throws IllegalArgumentException {
    ensureTrue(message, !state);
  }

  public static void ensureArgumentTrue(final boolean state) throws IllegalArgumentException {
    ensureTrue("boolean must be true", state); //$NON-NLS-1$
  }

  public static void ensureTrue(final String message, final boolean state) throws IllegalArgumentException {
    if (!state) {
      throw new IllegalArgumentException(message);
    }
  }
}