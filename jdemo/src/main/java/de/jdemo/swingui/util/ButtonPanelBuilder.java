package de.jdemo.swingui.util;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ButtonPanelBuilder {

  private final List<JComponent> components = new ArrayList<JComponent>();
  private final LayoutDirection direction;

  public ButtonPanelBuilder(final LayoutDirection direction) {
    JDemoEnsure.ensureArgumentNotNull(direction);
    this.direction = direction;
  }

  public void add(final JComponent component) {
    components.add(component);
  }

  public JPanel createPanel() {
    switch (direction) {
      case HORIZONTAL:
        return createHorizontalPanel();
      case VERTICAL:
        return createVerticalPanel();
    }
    throw new IllegalStateException();
  }

  private JPanel createVerticalPanel() {
    final JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(0, 1, 5, 5));
    addAllComponents(buttonPanel);

    final JPanel panel = new JPanel(new BorderLayout());
    panel.add(buttonPanel, BorderLayout.NORTH);
    panel.add(new JLabel(""), BorderLayout.CENTER); //$NON-NLS-1$
    return panel;
  }

  private JPanel createHorizontalPanel() {
    final JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    panel.setBorder(new EmptyBorder(8, 4, 4, 4));
    addAllComponents(panel);
    return panel;
  }

  private void addAllComponents(final JPanel panel) {
    for (final JComponent component : components) {
      panel.add(component);
    }
  }
}