package de.jdemo.swingui.lookandfeel;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Markus Gebhard
 */
public class DefaultLookAndFeelComboBox {

  private final JComboBox comboBox;
  private final LookAndFeelManagerDialogModel model;

  private final static LookAndFeelInfo NO_DEFAULT_LAF = new LookAndFeelInfo("", ""); //$NON-NLS-1$ //$NON-NLS-2$

  public DefaultLookAndFeelComboBox(final LookAndFeelManagerDialogModel model) {
    this.model = model;
    comboBox = new JComboBox();
    comboBox.setRenderer(new DefaultLookAndFeelComboBoxRenderer());
    model.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        updateView();
      }
    });
    updateView();

    comboBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        LookAndFeelInfo selectedItem = (LookAndFeelInfo) comboBox.getSelectedItem();
        if (selectedItem == NO_DEFAULT_LAF) {
          model.setDefaultLookAndFeel(null);
        }
        else {
          model.setDefaultLookAndFeel(selectedItem);
        }
      }
    });
  }

  private void updateView() {
    LookAndFeelInfo[] availableLafs = model.getAllLookAndFeels();
    Vector elements = new Vector();
    elements.add(NO_DEFAULT_LAF);
    elements.addAll(Arrays.asList(availableLafs));

    comboBox.setModel(new DefaultComboBoxModel(elements));
    LookAndFeelInfo defaultLookAndFeel = model.getDefaultLookAndFeel();
    if (defaultLookAndFeel == null) {
      comboBox.setSelectedItem(NO_DEFAULT_LAF);
    }
    else {
      comboBox.setSelectedItem(defaultLookAndFeel);
    }
  }

  public JComponent getContent() {
    return comboBox;
  }

  private final static class DefaultLookAndFeelComboBoxRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(
        JList list,
        Object value,
        int index,
        boolean isSelected,
        boolean cellHasFocus) {
      super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      LookAndFeelInfo laf = (LookAndFeelInfo) value;
      if (laf == NO_DEFAULT_LAF) {
        setText("None (uses implementation default)");
      }
      else {
        setText(laf.getName());
      }
      return this;
    }
  }
}