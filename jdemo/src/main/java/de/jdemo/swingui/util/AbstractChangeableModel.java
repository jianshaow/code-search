package de.jdemo.swingui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Markus Gebhard
 */
public class AbstractChangeableModel {
  private final Collection/*<ChangeListener>*/changeListeners = new ArrayList();

  public synchronized void addChangeListener(ChangeListener listener) {
    changeListeners.add(listener);
  }

  public synchronized void removeChangeListener(ChangeListener listener) {
    changeListeners.remove(listener);
  }

  protected void fireChangeEvent() {
    Collection clone;
    synchronized (changeListeners) {
      clone = new ArrayList(changeListeners);

    }
    ChangeEvent event = new ChangeEvent(this);
    for (Iterator iter = clone.iterator(); iter.hasNext();) {
      ChangeListener listener = (ChangeListener) iter.next();
      listener.stateChanged(event);
    }
  }

}