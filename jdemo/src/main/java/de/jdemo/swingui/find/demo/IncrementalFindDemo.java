package de.jdemo.swingui.find.demo;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.jdemo.extensions.SwingDemoCase;
import de.jdemo.swingui.find.IIncrementalSearchable;
import de.jdemo.swingui.find.IncrementalFind;

/**
 * @author Markus Gebhard
 */
public class IncrementalFindDemo extends SwingDemoCase {

  public void demoIncrementalFind() {
    final JList list = new JList(new String[]{ "eins", "zwei", "drei", "vier", "fünf", "sechs", "sieben" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
    final JTextField tfStatus = new JTextField();

    IncrementalFind.attachTo(list, new IIncrementalSearchable() {
      private int currentIndex;

      public void showSearchMessage(String searchPattern, boolean failed) {
        if (failed) {
          tfStatus.setText("Incremental find: " + searchPattern + " not found"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        else {
          tfStatus.setText("Incremental find: " + searchPattern); //$NON-NLS-1$
        }
      }

      public void initSearch() {
        currentIndex = 0;
      }

      public boolean currentElementFits(String searchPattern) {
        return fits(currentIndex, searchPattern);
      }

      private boolean fits(int index, String searchPattern) {
        //Attention: it might be useful to ignore the case here!
        return list.getModel().getElementAt(index).toString().indexOf(searchPattern) != -1;
      }

      public void selectFirstElement() {
        currentIndex = 0;
//        list.getSelectionModel().setSelectionInterval(currentIndex, currentIndex);
      }

      public void selectLastElement() {
        currentIndex = list.getModel().getSize() - 1;
//        list.getSelectionModel().setSelectionInterval(currentIndex, currentIndex);
      }

      public boolean findNextElement(String searchPattern) {
        for (int i = currentIndex + 1; i < list.getModel().getSize(); ++i) {
          if (fits(i, searchPattern)) {
            currentIndex = i;
            list.getSelectionModel().setSelectionInterval(currentIndex, currentIndex);
            return true;
          }
        }
        return false;
      }

      public boolean findPreviousElement(String searchPattern) {
        for (int i = currentIndex - 1; i >= 0; --i) {
          if (fits(i, searchPattern)) {
            currentIndex = i;
            list.getSelectionModel().setSelectionInterval(currentIndex, currentIndex);
            return true;
          }
        }
        return false;
      }

      public void clearSearchMessage() {
        tfStatus.setText(""); //$NON-NLS-1$
      }

      public void beep() {
        list.getToolkit().beep();
      }

      public void selectCurrentElement() {
        list.getSelectionModel().setSelectionInterval(currentIndex, currentIndex);
      }
    });

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add(list, BorderLayout.CENTER);
    panel.add(tfStatus, BorderLayout.SOUTH);
    show(panel);
  }
}