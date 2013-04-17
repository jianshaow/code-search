package de.jdemo.swingui.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * @author Markus Gebhard
 */
public class WebLinkLabel {

  private final JLabel label;
  private final String urlString;

  public WebLinkLabel(final String urlString) {
    this.urlString = urlString;
    label = new JLabel(urlString);

    updateColor(false);
    label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    label.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(final MouseEvent e) {
        if (!e.isMetaDown()) {
          gotoWebSite();
        }
        updateColor(false);
      }

      @Override
      public void mousePressed(final MouseEvent e) {
        updateColor(true);
      }
    });
  }

  private void updateColor(final boolean mousePressed) {
    if (mousePressed) {
      label.setForeground(new Color(64, 64, 224));
    }
    else {
      label.setForeground(new Color(0, 0, 128));
    }
  }

  private void gotoWebSite() {
    try {
      final URL url = new URL(urlString);
      final URI uri = url.toURI();
      Desktop.getDesktop().browse(uri);
    }
    catch (final IOException e) {
      e.printStackTrace();
    }
    catch (final URISyntaxException e) {
      e.printStackTrace();
    }
  }

  public JComponent getContent() {
    return label;
  }
}