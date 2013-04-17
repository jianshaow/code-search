package de.jdemo.swingui.lookandfeel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.UIManager.LookAndFeelInfo;

import de.jdemo.swingui.util.AbstractChangeableModel;

/**
 * @author Markus Gebhard
 */
public class LookAndFeelManagerDialogModel extends AbstractChangeableModel {

  private final LookAndFeelRegistry registry;

  private final LookAndFeelInfo[] systemLookAndFeels;
  private final List/*<LookAndFeelInfo>*/customLookAndFeels = new ArrayList();

  private LookAndFeelInfo defaultLookAndFeel;

  public LookAndFeelManagerDialogModel(LookAndFeelRegistry registry) {
    this.registry = registry;
    systemLookAndFeels = registry.getSystemDefaultLookAndFeels();
    defaultLookAndFeel = registry.getDefaultLookAndFeel();
    customLookAndFeels.addAll(Arrays.asList(registry.getCustomLookAndFeels()));
  }

  public void saveInRegistry() {
    LookAndFeelInfo[] lookAndFeels = (LookAndFeelInfo[]) customLookAndFeels
        .toArray(new LookAndFeelInfo[customLookAndFeels.size()]);
    registry.setCustomLookAndFeels(lookAndFeels);
    registry.setDefaultLookAndFeel(defaultLookAndFeel);
  }

  public int getLookAndFeelCount() {
    return systemLookAndFeels.length + customLookAndFeels.size();
  }

  public LookAndFeelInfo getLookAndFeel(int index) {
    if (isSystemLookAndFeelIndex(index)) {
      return systemLookAndFeels[index];
    }
    else {
      return (LookAndFeelInfo) customLookAndFeels.get(index - systemLookAndFeels.length);
    }
  }

  public void removeLookAndFeelAt(int index) {
    customLookAndFeels.remove(index - systemLookAndFeels.length);
    fireChangeEvent();
  }

  public boolean isSystemLookAndFeelIndex(int index) {
    return index < systemLookAndFeels.length;
  }

  public void addCustomLookAndFeel(LookAndFeelInfo lookAndFeel) {
    customLookAndFeels.add(lookAndFeel);
    fireChangeEvent();
  }

  public void setDefaultLookAndFeel(LookAndFeelInfo defaultLookAndFeel) {
    this.defaultLookAndFeel = defaultLookAndFeel;
    fireChangeEvent();
  }

  public LookAndFeelInfo getDefaultLookAndFeel() {
    if (!customLookAndFeels.contains(defaultLookAndFeel)
        && !Arrays.asList(systemLookAndFeels).contains(defaultLookAndFeel)) {
      return null;
    }
    return defaultLookAndFeel;
  }

  public LookAndFeelInfo[] getAllLookAndFeels() {
    List all = new ArrayList();
    all.addAll(Arrays.asList(systemLookAndFeels));
    all.addAll(customLookAndFeels);
    return (LookAndFeelInfo[]) all.toArray(new LookAndFeelInfo[all.size()]);
  }
}