package de.jdemo.swingui.preferences;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.UIManager.LookAndFeelInfo;

/**
 * @author Markus Gebhard
 */
public class LookAndFeelPreferences {

  private static final String KEY_CUSTOM_LOOK_AND_FEELS = "customLookAndFeels"; //$NON-NLS-1$
  private static final String KEY_CLASSNAME = "className"; //$NON-NLS-1$
  private static final String KEY_NAME = "name"; //$NON-NLS-1$
  private static final String KEY_COUNT = "count"; //$NON-NLS-1$
  private static final String KEY_DEFAULT_LOOK_AND_FEEL_NAME = "defaultLookAndFeelName"; //$NON-NLS-1$

  private final Preferences preferences;

  public LookAndFeelPreferences(Preferences jdemoPreferences) {
    preferences = jdemoPreferences.node(KEY_CUSTOM_LOOK_AND_FEELS);
  }

  public void clear() throws BackingStoreException {
    preferences.clear();
  }

  public void setCustomLookAndFeels(LookAndFeelInfo[] customLookAndFeels) {
    preferences.putInt(KEY_COUNT, customLookAndFeels.length);
    for (int i = 0; i < customLookAndFeels.length; i++) {
      preferences.put(KEY_NAME + i, customLookAndFeels[i].getName());
      preferences.put(KEY_CLASSNAME + i, customLookAndFeels[i].getClassName());
    }
  }

  public void flush() throws BackingStoreException {
    preferences.flush();
  }

  public void setDefaultLookAndFeelName(String defaultLookAndFeelName) {
    preferences.put(KEY_DEFAULT_LOOK_AND_FEEL_NAME, defaultLookAndFeelName);
  }

  public LookAndFeelInfo[] getCustomLookAndFeels() {
    int count = preferences.getInt(KEY_COUNT, 0);
    LookAndFeelInfo[] lookAndFeels = new LookAndFeelInfo[count];
    for (int i = 0; i < count; i++) {
      String name = preferences.get(KEY_NAME + i, null);
      String className = preferences.get(KEY_CLASSNAME + i, null);
      lookAndFeels[i] = new LookAndFeelInfo(name, className);
    }
    return lookAndFeels;
  }

  public String getDefaultLookAndFeelName() {
    return preferences.get(KEY_DEFAULT_LOOK_AND_FEEL_NAME, null);
  }
}