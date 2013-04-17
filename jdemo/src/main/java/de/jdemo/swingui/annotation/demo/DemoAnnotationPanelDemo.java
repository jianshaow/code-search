package de.jdemo.swingui.annotation.demo;

import de.jdemo.extensions.SwingDemoCase;
import de.jdemo.swingui.annotation.DemoAnnotationPanel;
import de.jdemo.swingui.model.DemoSelectionModel;

/**
 * @author Markus Gebhard
 */
public class DemoAnnotationPanelDemo extends SwingDemoCase {

  public void demo() {
    DemoAnnotationPanel annotationPanel = new DemoAnnotationPanel(new DemoSelectionModel());
    show(annotationPanel.getContent());
  }

  public void demoNoDescription() {
    DemoAnnotationPanel annotationPanel = new DemoAnnotationPanel(new DemoSelectionModel());
    annotationPanel.setDescription(null);
    show(annotationPanel.getContent());
  }

  public void demoTextualDescription() {
    DemoAnnotationPanel annotationPanel = new DemoAnnotationPanel(new DemoSelectionModel());
    annotationPanel.setDescription("Textual description text. This text is quite long and so it might wrap."); //$NON-NLS-1$
    show(annotationPanel.getContent());
  }

  public void demoHtmlDescription() {
    DemoAnnotationPanel annotationPanel = new DemoAnnotationPanel(new DemoSelectionModel());
    annotationPanel.setDescription("Prints 'Hello World' to <code>System.out</code><ul><li>one<li>two<li>three</ul>"); //$NON-NLS-1$
    show(annotationPanel.getContent());
  }
}