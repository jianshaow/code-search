package de.java2html.plugin.jspwiki;

import java.util.HashSet;
import java.util.Set;

import de.java2html.options.ConversionOptionsUtilities;
import de.java2html.plugin.IParameter;

/**
 * @author Markus Gebhard
 */
public final class PluginParameter implements IParameter {
  private final static Set all = new HashSet();
  private final static Set allNames = new HashSet();

  public final static PluginParameter _BODY = new PluginParameter("_body", "-", "-");
  public final static PluginParameter STYLE = new PluginParameter("style", "supported styles are: "
      + ConversionOptionsUtilities.getPredefinedStyleTableNameString(), "monochrome");
  public final static PluginParameter ATTACHMENT = new PluginParameter(
      "attachment",
      "If specified, the source code from the attached Java file will be used.",
      "HelloWorld.java");
  public final static PluginParameter SOURCE = new PluginParameter(
      "source",
      "If specified, the source code contained in this parameter value will be used (only valid for one line of code).",
      "public final static main(String[] args);");
  public final static PluginParameter URL = new PluginParameter(
      "url",
      "If specified, the source code from the Java file given by the url will be used (only available if this option is enabled in the wiki properties).",
      "http://www.java2html.de/HelloWorld.java");
  public static final IParameter CONVERTER = new PluginParameter(
      "converter",
      "Name of the converter to use. Default is <code>html</code>.",
      "xhtml");
  public final static PluginParameter ALIGNMENT = new PluginParameter(
      "alignment",
      "Specifies the horizontal alignment of the output. Supported values are: "
          + ConversionOptionsUtilities.getAvailableHorizontalAlignmentNameString()
          + " default is <code>left</code>.",
      "center");
  public final static PluginParameter PRINT_VERSION = new PluginParameter(
      "printVersion",
      "If specified, the plugin only prints its name an version.",
      "true");
  public final static PluginParameter BORDER = new PluginParameter(
      "border",
      "boolean flag for rendering a table border around the converted result. Default is <code>false<code>",
      "true");
  public final static PluginParameter LINE_NUMBERS = new PluginParameter(
      "lineNumbers",
      "boolean flag for rendering line numbers. Default is <code>false</false>",
      "true");
  public final static PluginParameter TAB_SIZE = new PluginParameter(
      "tabSize",
      "Number of spaces representing a tab character. Default is <code>2</code>.",
      "4");

  private String name;
  private String description;
  private String exampleValue;

  public PluginParameter(String name, String description, String exampleValue) {
    this.exampleValue = exampleValue;
    this.name = name;
    this.description = description;
    all.add(this);
    allNames.add(name);
  }

  public String getName() {
    return name;
  }

  public static Set getAll() {
    return all;
  }

  public boolean isInternal() {
    return isInternal(getName());
  }

  public static boolean isInternal(String parameterName) {
    return parameterName.startsWith("_");
  }

  public String getDescription() {
    return description;
  }

  public String getExampleValue() {
    return exampleValue;
  }

  public static Set getAllNames() {
    return allNames;
  }
}