package de.java2html.plugin;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;

import de.java2html.Java2Html;
import de.java2html.JavaSourceConversionSettings;
import de.java2html.converter.IJavaSourceConverter;
import de.java2html.javasource.JavaSource;
import de.java2html.javasource.JavaSourceParser;

/**
 * Abstract superclass for any kind of Wiki plugin.
 * 
 * @author Markus Gebhard
 */
public abstract class AbstractJava2HtmlPlugin {

  public static String convert(String source, JavaSourceConversionSettings settings) {
    return Java2Html.convertToHtml(source, settings);
  }

  public static JavaSourceConversionSettings getDefaultSettings() {
    return JavaSourceConversionSettings.getDefault();
  }

  public String convert(URL url, JavaSourceConversionSettings settings) throws IOException {
    JavaSource source = new JavaSourceParser(settings.getConversionOptions()).parse(url);
    IJavaSourceConverter converter = settings.createConverter();
    StringWriter writer = new StringWriter();
    try {
      converter.convert(source, settings.getConversionOptions(), writer);
    }
    catch (IOException e) {
      return null;
    }
    return writer.toString();
  }
}