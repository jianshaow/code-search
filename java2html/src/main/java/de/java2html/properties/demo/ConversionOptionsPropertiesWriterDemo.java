package de.java2html.properties.demo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import de.java2html.options.JavaSourceConversionOptions;
import de.java2html.properties.ConversionOptionsPropertiesWriter;
import de.jdemo.framework.DemoCase;

/**
 * @author Markus
 */
public class ConversionOptionsPropertiesWriterDemo extends DemoCase {
  public void demo() throws IOException {
    Properties properties =
      new ConversionOptionsPropertiesWriter().write(JavaSourceConversionOptions.getDefault());

    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    properties.store(stream, null);
    show(new String(stream.toByteArray()));
  }
}
