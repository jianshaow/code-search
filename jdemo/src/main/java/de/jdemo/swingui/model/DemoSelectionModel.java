package de.jdemo.swingui.model;

import de.jdemo.framework.IDemo;
import de.jdemo.swingui.util.AbstractChangeableModel;
import de.jdemo.swingui.util.ObjectUtilities;

/**
 * @author Markus Gebhard
 */
public class DemoSelectionModel extends AbstractChangeableModel {

  private IDemo selectedDemo;

  public IDemo getSelectedDemo() {
    return selectedDemo;
  }

  public void setSelectedDemo(IDemo selectedDemo) {
    if (ObjectUtilities.equals(this.selectedDemo, selectedDemo)) {
      return;
    }
    this.selectedDemo = selectedDemo;
    fireChangeEvent();
  }

}