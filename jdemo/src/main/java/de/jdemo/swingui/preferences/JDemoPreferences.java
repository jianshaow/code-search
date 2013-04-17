package de.jdemo.swingui.preferences;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * @author Markus Gebhard
 */
public class JDemoPreferences {

  private static JDemoPreferences instance = new JDemoPreferences();

  public static JDemoPreferences getInstance() {
    return instance;
  }

  private final WindowConfigurationPreferences windowConfigurationPreferences;
  private final LookAndFeelPreferences lookAndFeelPreferences;

  private JDemoPreferences() {
    Preferences preferences = Preferences.userRoot();
    Preferences jdemoPreferences = preferences.node("/JDemo"); //$NON-NLS-1$
    windowConfigurationPreferences = new WindowConfigurationPreferences(jdemoPreferences);
    lookAndFeelPreferences = new LookAndFeelPreferences(jdemoPreferences);
  }

  public LookAndFeelPreferences getLookAndFeelPreferences() {
    return lookAndFeelPreferences;
  }

  public WindowConfigurationPreferences getWindowConfigurationPreferences() {
    return windowConfigurationPreferences;
  }
  
  public void clear() throws BackingStoreException {
    windowConfigurationPreferences.clear();
    lookAndFeelPreferences.clear();
  }
  
  public void flush() throws BackingStoreException {
    windowConfigurationPreferences.flush();
    lookAndFeelPreferences.flush();
  }
}