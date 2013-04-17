package de.jdemo.swingui.lookandfeel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import de.jdemo.swingui.preferences.JDemoPreferences;
import de.jdemo.swingui.preferences.LookAndFeelPreferences;

/**
 * @author Markus Gebhard
 */
public class LookAndFeelRegistry {

  private final static LookAndFeelRegistry instance = new LookAndFeelRegistry();

  public static LookAndFeelRegistry getInstance() {
    return instance;
  }

  private final LookAndFeelInfo[] systemDefaultLookAndFeels;
  private final List/*<LookAndFeelInfo>*/customLookAndFeels = new ArrayList();
  private String defaultLookAndFeelName = null;

  private LookAndFeelRegistry() {
    systemDefaultLookAndFeels = UIManager.getInstalledLookAndFeels();
    loadCustomLookAndFeelsFromPreferences();
  }

  public LookAndFeelInfo[] getSystemDefaultLookAndFeels() {
    return systemDefaultLookAndFeels;
  }

  public LookAndFeelInfo[] getCustomLookAndFeels() {
    return (LookAndFeelInfo[]) customLookAndFeels.toArray(new LookAndFeelInfo[customLookAndFeels.size()]);
  }

  public void setCustomLookAndFeels(LookAndFeelInfo[] lookAndFeels) {
    customLookAndFeels.clear();
    customLookAndFeels.addAll(Arrays.asList(lookAndFeels));
    saveCustomLookAndFeelsToPreferences();
  }

  public void addCustomLookAndFeel(LookAndFeelInfo lookAndFeel) {
    customLookAndFeels.add(lookAndFeel);
    saveCustomLookAndFeelsToPreferences();
  }

  private void loadCustomLookAndFeelsFromPreferences() {
    try {
      LookAndFeelPreferences lafPreferences = getLookAndFeelPreferences();
      LookAndFeelInfo[] lookAndFeels = lafPreferences.getCustomLookAndFeels();
      customLookAndFeels.addAll(Arrays.asList(lookAndFeels));
      defaultLookAndFeelName = lafPreferences.getDefaultLookAndFeelName();
    }
    catch (Exception e) {
      System.err.println("Error reading preferences"); //$NON-NLS-1$
      e.printStackTrace();
    }
  }

  private void saveCustomLookAndFeelsToPreferences() {
    try {
      LookAndFeelPreferences lafPreferences = getLookAndFeelPreferences();
      lafPreferences.clear();
      lafPreferences.setCustomLookAndFeels(getCustomLookAndFeels());
      if (defaultLookAndFeelName != null) {
        lafPreferences.setDefaultLookAndFeelName(defaultLookAndFeelName);
      }
      lafPreferences.flush();
    }
    catch (Exception e) {
      System.err.println("Error writing preferences"); //$NON-NLS-1$
      e.printStackTrace();
    }
  }

  private LookAndFeelPreferences getLookAndFeelPreferences() {
    JDemoPreferences preferences = JDemoPreferences.getInstance();
    return preferences.getLookAndFeelPreferences();
  }

  /** Returns the name of the default look&feel or <code>null</code> if there is none defined. */
  public LookAndFeelInfo getDefaultLookAndFeel() {
    return defaultLookAndFeelName == null ? null : getLookAndFeelByName(defaultLookAndFeelName);
  }

  private LookAndFeelInfo getLookAndFeelByName(String name) {
    LookAndFeelInfo[] systemLafs = getSystemDefaultLookAndFeels();
    for (int i = 0; i < systemLafs.length; i++) {
      if (name.equals(systemLafs[i].getName())) {
        return systemLafs[i];
      }
    }
    LookAndFeelInfo[] customLafs = getCustomLookAndFeels();
    for (int i = 0; i < customLafs.length; i++) {
      if (name.equals(customLafs[i].getName())) {
        return customLafs[i];
      }
    }
    return null;
  }

  public void setDefaultLookAndFeel(LookAndFeelInfo lookAndFeel) {
    this.defaultLookAndFeelName = lookAndFeel == null ? null : lookAndFeel.getName();
    saveCustomLookAndFeelsToPreferences();
  }
}