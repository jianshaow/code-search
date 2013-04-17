package de.jdemo.swingui.annotation;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.jdemo.annotation.DemoAnnotation;
import de.jdemo.annotation.DemoAnnotationReader;
import de.jdemo.annotation.IDemoAnnotationReader;
import de.jdemo.framework.IDemo;
import de.jdemo.framework.IDemoCase;
import de.jdemo.framework.IDemoSuite;
import de.jdemo.framework.IDemoVisitor;
import de.jdemo.framework.exceptions.DemoClassNotFoundException;
import de.jdemo.framework.util.DemoUtilities;
import de.jdemo.swingui.FramePanel;
import de.jdemo.swingui.model.DemoSelectionModel;

/**
 * @author Markus Gebhard
 */
public class DemoAnnotationPanel {

  private final JPanel panel;
  private final JTextPane descriptionTextPane;
  private final JTextField idTextField;
  private final JTextField nameTextField;
  private final JComponent content;

  public DemoAnnotationPanel(final DemoSelectionModel selectionModel) {
    descriptionTextPane = new JTextPane();
    descriptionTextPane.setContentType("text/html"); //$NON-NLS-1$
    descriptionTextPane.setEditable(false);
    idTextField = new JTextField();
    idTextField.setEditable(false);
    nameTextField = new JTextField();
    nameTextField.setEditable(false);

    final JPanel panel1 = new JPanel(new BorderLayout(5, 5));
    panel1.add(new JLabel("Name:"), BorderLayout.WEST);
    panel1.add(nameTextField, BorderLayout.CENTER);

    final JPanel panel2 = new JPanel(new BorderLayout(5, 5));
    panel2.add(new JLabel("ID:"), BorderLayout.WEST);
    panel2.add(idTextField, BorderLayout.CENTER);

    final JPanel panel3 = new JPanel(new BorderLayout(5, 5));
    panel3.add(new JLabel("Description:"), BorderLayout.WEST);

    final JPanel topPanel = new JPanel(new GridLayout(0, 1, 5, 5));
    topPanel.add(panel1);
    topPanel.add(panel2);
    topPanel.add(panel3);

    panel = new JPanel(new BorderLayout());
    panel.add(topPanel, BorderLayout.NORTH);
    panel.add(new JScrollPane(descriptionTextPane), BorderLayout.CENTER);

    selectionModel.addChangeListener(new ChangeListener() {
      public void stateChanged(final ChangeEvent e) {
        updateView(selectionModel.getSelectedDemo());
      }
    });

    content = new FramePanel("Selected demo:", panel).getContent();
  }

  public JComponent getContent() {
    return content;
  }

  private void updateView(final IDemo demo) {
    if (demo == null) {
      panel.setEnabled(false);
      nameTextField.setText(""); //$NON-NLS-1$
      idTextField.setText(""); //$NON-NLS-1$
      descriptionTextPane.setText(""); //$NON-NLS-1$
      return;
    }
    nameTextField.setText(DemoUtilities.getDisplayName(demo));
    demo.accept(new IDemoVisitor() {
      public void visitDemoCase(final IDemoCase demoCase) {
        panel.setEnabled(true);
        idTextField.setText(demoCase.getIdentifier().toString());
        final IDemoAnnotationReader reader = new DemoAnnotationReader();
        try {
          final DemoAnnotation annotation = reader.getAnnotation(demoCase.getIdentifier());
          setDescription(annotation == null ? null : annotation.getDescription());
        }
        catch (final DemoClassNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

      public void visitDemoSuite(final IDemoSuite demoSuite) {
        panel.setEnabled(false);
        descriptionTextPane.setText(""); //$NON-NLS-1$
      }
    });
  }

  public void setDescription(final String description) {
    descriptionTextPane.setEnabled(true);

    final String text = description == null ? "No description available." : description;
    try {
      descriptionTextPane.read(new StringReader(text), null);
    }
    catch (final IOException e) {
      e.printStackTrace();
      descriptionTextPane.setText(text);
    }
  }
}