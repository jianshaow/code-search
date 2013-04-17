package de.jdemo.swingui.preferences;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Markus Gebhard
 */
public class WindowConfigurationPreferences {

  private static final String KEY_WINDOW_CONFIGURATION = "windowConfiguration"; //$NON-NLS-1$
  private static final String KEY_WINDOW_WIDTH_WITHOUT_ANNOTATIONS = "windowWidthWithoutAnnotations"; //$NON-NLS-1$
  private static final String KEY_WINDOW_WIDTH_WITH_ANNOTATIONS = "windowWidthWithAnnotations"; //$NON-NLS-1$
  private static final String KEY_WINDOW_HEIGHT = "windowHeight"; //$NON-NLS-1$
  private static final String KEY_WINDOW_X = "windowX"; //$NON-NLS-1$
  private static final String KEY_WINDOW_Y = "windowY"; //$NON-NLS-1$
  private static final String KEY_VERTICAL_DIVIDER_LOCATION = "verticalDividerLocation"; //$NON-NLS-1$
  private static final String KEY_HORIZONTAL_DIVIDER_LOCATION = "horizontalDividerLocation"; //$NON-NLS-1$
  private static final String KEY_ANNOTATION_PANEL_VISIBLE = "annotationPanelVisible"; //$NON-NLS-1$

  private final Preferences preferences;
  private List/*<ChangeListener>*/annotationPanelVisibilityChangeListeners = new ArrayList();

  public WindowConfigurationPreferences(Preferences jdemoPreferences) {
    preferences = jdemoPreferences.node(KEY_WINDOW_CONFIGURATION);
  }

  public Dimension getWindowSize() {
    final int width;
    if (isAnnotationPanelVisible()) {
      width = preferences.getInt(KEY_WINDOW_WIDTH_WITH_ANNOTATIONS, 615);
    }
    else {
      width = preferences.getInt(KEY_WINDOW_WIDTH_WITHOUT_ANNOTATIONS, 315);
    }
    int height = preferences.getInt(KEY_WINDOW_HEIGHT, 530);
    return new Dimension(width, height);
  }

  public Point getWindowLocation() {
    int x = preferences.getInt(KEY_WINDOW_X, 0);
    int y = preferences.getInt(KEY_WINDOW_Y, 10);
    return new Point(x, y);
  }

  public void setWindowSize(Dimension size) {
    if (isAnnotationPanelVisible()) {
      preferences.putInt(KEY_WINDOW_WIDTH_WITH_ANNOTATIONS, size.width);
    }
    else {
      preferences.putInt(KEY_WINDOW_WIDTH_WITHOUT_ANNOTATIONS, size.width);
    }
    preferences.putInt(KEY_WINDOW_HEIGHT, size.height);
  }

  public void setWindowLocation(Point location) {
    preferences.putInt(KEY_WINDOW_X, location.x);
    preferences.putInt(KEY_WINDOW_Y, location.y);
  }

  public void flush() throws BackingStoreException {
    preferences.flush();
  }

  public int getVerticalDividerLocation() {
    return preferences.getInt(KEY_VERTICAL_DIVIDER_LOCATION, 340);
  }

  public void setVerticalDividerLocation(int verticalDividerLocation) {
    preferences.putInt(KEY_VERTICAL_DIVIDER_LOCATION, verticalDividerLocation);
  }

  public int getHorizontalDividerLocation() {
    return preferences.getInt(KEY_HORIZONTAL_DIVIDER_LOCATION, 235);
  }

  public void setHorizontalDividerLocation(int horizontalDividerLocation) {
    preferences.putInt(KEY_HORIZONTAL_DIVIDER_LOCATION, horizontalDividerLocation);
  }

  public boolean isAnnotationPanelVisible() {
    return preferences.getBoolean(KEY_ANNOTATION_PANEL_VISIBLE, false);
  }

  public void setAnnotationPanelVisible(boolean annotationPanelVisible) {
    if (annotationPanelVisible == isAnnotationPanelVisible()) {
      return;
    }
    preferences.putBoolean(KEY_ANNOTATION_PANEL_VISIBLE, annotationPanelVisible);
    ChangeEvent changeEvent = new ChangeEvent(this);
    for (Iterator iter = annotationPanelVisibilityChangeListeners.iterator(); iter.hasNext();) {
      ChangeListener listener = (ChangeListener) iter.next();
      listener.stateChanged(changeEvent);
    }
  }

  public void addAnnotationPanelVisibilityChangeListener(ChangeListener listener) {
    annotationPanelVisibilityChangeListeners.add(listener);
  }

  public void clear() throws BackingStoreException {
    preferences.clear();
  }
}