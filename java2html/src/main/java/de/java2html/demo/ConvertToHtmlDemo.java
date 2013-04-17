package de.java2html.demo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import de.java2html.Java2Html;
import de.java2html.JavaSourceConversionSettings;
import de.java2html.gui.Java2HtmlOptionsPanel;
import de.jdemo.extensions.SwingDemoCase;
import de.jdemo.util.FileLauncher;

/**
 * @author Markus Gebhard
 */
public class ConvertToHtmlDemo extends SwingDemoCase {

  public void demo() {
    final Java2HtmlOptionsPanel optionsPanel = new Java2HtmlOptionsPanel();
    final JTextArea textArea = new JTextArea(10, 80);
    JButton button = new JButton("Convert to Html"); //$NON-NLS-1$
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        JavaSourceConversionSettings conversionOptions = optionsPanel.getConversionSettings();
        String htmlText = Java2Html.convertToHtmlPage(textArea.getText(), conversionOptions);
        File file = null;
        FileWriter writer = null;
        try {
          file = File.createTempFile("demo", "." //$NON-NLS-1$ //$NON-NLS-2$
              + conversionOptions.createConverter().getMetaData().getDefaultFileExtension());
          writer = new FileWriter(file);
          writer.write(htmlText);
        }
        catch (IOException exception) {
          // TODO Auto-generated catch block
          exception.printStackTrace();
        }
        finally {
          if (writer != null) {
            try {
              writer.close();
            }
            catch (IOException exception) {
              //nothing to do
            }
          }
        }
        try {
          new FileLauncher().launch(file);
        }
        catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(optionsPanel.getContent(), BorderLayout.NORTH);
    panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
    panel.add(button, BorderLayout.SOUTH);

    show(panel);
  }
}