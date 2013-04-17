package de.java2html.plugin.jspwiki.test;

import java.util.HashMap;

import junit.framework.TestCase;

import com.ecyrd.jspwiki.plugin.PluginException;

import de.java2html.JavaSourceConversionSettings;
import de.java2html.options.JavaSourceStyleTable;
import de.java2html.plugin.AbstractJava2HtmlPlugin;
import de.java2html.plugin.jspwiki.Java2HtmlPlugin;

/**
 * @author Markus Gebhard
 */
public class Java2HtmlPluginTest extends TestCase {
  private final static String SOURCE_CODE = "public class HelloWorld {\n"
      + "  public static void main(String args[]) {\n"
      + "    System.out.println(\"Hello World!\");\n"
      + "  }\n"
      + "}";

  private final static String CONVERTED_HTML_DEFAULT_STYLE = AbstractJava2HtmlPlugin.convert(
      SOURCE_CODE,
      AbstractJava2HtmlPlugin.getDefaultSettings());

  private Java2HtmlPlugin plugin;

  private static String CONVERTED_HTML_MONOCHROME_STYLE;

  static {
    JavaSourceConversionSettings monochromeOptions = AbstractJava2HtmlPlugin.getDefaultSettings();
    monochromeOptions.getConversionOptions().setStyleTable(JavaSourceStyleTable.getDefaultMonochromeStyleTable());
    CONVERTED_HTML_MONOCHROME_STYLE = AbstractJava2HtmlPlugin.convert(SOURCE_CODE, monochromeOptions);
  }

  protected void setUp() throws Exception {
    plugin = new Java2HtmlPlugin();

  }

  public void testThrowsUsageExceptionWhenNoSourceCodeGiven() {
    try {
      plugin.execute(null, new HashMap());
      fail();
    }
    catch (PluginException expected) {
      assertEquals(Java2HtmlPlugin.DEFAULT_USAGE_MESSAGE, expected.getMessage());
    }
  }

  public void testConversionAsSourceParameter() throws PluginException {
    HashMap map = new HashMap();
    map.put("source", SOURCE_CODE);
    assertEquals(CONVERTED_HTML_DEFAULT_STYLE, plugin.execute(null, map));
  }

  public void testConversionAsBodyParameter() throws PluginException {
    HashMap map = new HashMap();
    map.put("_body", SOURCE_CODE);
    assertEquals(CONVERTED_HTML_DEFAULT_STYLE, plugin.execute(null, map));
  }

  public void testConversionAsBodyParameterWithLeadingNewLine() throws PluginException {
    /*
     * Reason: JSPWiki does not automatically remove the extra newline in the
     * multiline parameter _body
     */
    HashMap map = new HashMap();
    map.put("_body", "\n" + SOURCE_CODE);
    assertEquals(CONVERTED_HTML_DEFAULT_STYLE, plugin.execute(null, map));
  }

  //TODO Dec 2, 2003 (Markus Gebhard): This test needs a WikiContext
  //  public void testConversionFromNonExistingFile() {
  //    HashMap map = new HashMap();
  //    map.put("url", "file:/i/am/not/existing/at/all.java");
  //    try {
  //      plugin.execute(null, map);
  //    }
  //    catch (PluginException expected) {
  //      //expected
  //    }
  //  }

  public void testUsingDefaultStyleNameIsSameAsUsingDefaultStyle() throws PluginException {
    HashMap map = new HashMap();
    map.put("source", SOURCE_CODE);
    map.put("style", AbstractJava2HtmlPlugin
        .getDefaultSettings().getConversionOptions().getStyleTable().getName());
    assertEquals(CONVERTED_HTML_DEFAULT_STYLE, plugin.execute(null, map));
  }

  public void testUsingMonochromeStyle() throws PluginException {
    HashMap map = new HashMap();
    map.put("source", SOURCE_CODE);
    map.put("style", JavaSourceStyleTable.getDefaultMonochromeStyleTable().getName());
    assertEquals(CONVERTED_HTML_MONOCHROME_STYLE, plugin.execute(null, map));
  }

  public void testUnsupportedConversionStyle() {
    HashMap map = new HashMap();
    map.put("style", "a_definitely_not_existing_style");
    try {
      plugin.execute(null, map);
    }
    catch (PluginException expected) {
      //expected
    }
  }
}