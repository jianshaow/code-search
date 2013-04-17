package de.jdemo.swingui.lookandfeel;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;

/**
 * @author Markus Gebhard
 */
public class LookAndFeelTableModel extends AbstractTableModel {

  private final LookAndFeelManagerDialogModel model;

  public LookAndFeelTableModel(LookAndFeelManagerDialogModel model) {
    this.model = model;
    model.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        fireTableDataChanged();
      }
    });
  }

  public int getColumnCount() {
    return 3;
  }

  public int getRowCount() {
    return model.getLookAndFeelCount();
  }

  public String getColumnName(int columnIndex) {
    switch (columnIndex) {
      case 0:
        return "Name";
      case 1:
        return "Type";
      case 2:
        return "Class";
      default:
        throw new IndexOutOfBoundsException();
    }
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    switch (columnIndex) {
      case 0:
        return model.getLookAndFeel(rowIndex).getName();
      case 1:
        return model.isSystemLookAndFeelIndex(rowIndex) ? "System" : "Custom";
      case 2:
        return model.getLookAndFeel(rowIndex).getClassName();
      default:
        throw new IndexOutOfBoundsException();
    }
  }
}