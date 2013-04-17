package de.java2html.plugin.jspwiki;

import java.util.Map;

import com.ecyrd.jspwiki.plugin.PluginException;

import de.java2html.options.ConversionOptionsUtilities;
import de.java2html.options.HorizontalAlignment;
import de.java2html.options.JavaSourceConversionOptions;
import de.java2html.options.JavaSourceStyleTable;
import de.java2html.plugin.AbstractJava2HtmlPlugin;
import de.java2html.plugin.ParameterUtilities;

/**
 * @author Markus Gebhard
 */
public class PluginConversionOptionsParser {

  public JavaSourceConversionOptions parseConversionOptions(Map params) throws PluginException {
    JavaSourceConversionOptions conversionOptions = AbstractJava2HtmlPlugin.getDefaultSettings().getConversionOptions();
    adjustStyleTable(params, conversionOptions);
    adjustBorder(params, conversionOptions);
    adjustLineNumbers(params, conversionOptions);
    adjustTabSize(params, conversionOptions);
    adjustAlignment(params, conversionOptions);
    return conversionOptions;
  }

  private void adjustStyleTable(Map params, JavaSourceConversionOptions conversionOptions)
    throws PluginException {
    String style = ParameterUtilities.getParameter(params, PluginParameter.STYLE);
    if (style != null) {
      JavaSourceStyleTable styleTable = JavaSourceStyleTable.getPredefinedTable(style);
      if (styleTable == null) {
        throw new PluginException(
          "Unsupported style '"
            + style
            + "' - available styles are: "
            + ConversionOptionsUtilities.getPredefinedStyleTableNameString());
      }
      conversionOptions.setStyleTable(styleTable);
    }
  }

  private void adjustBorder(Map params, JavaSourceConversionOptions conversionOptions) throws PluginException {
    String border = ParameterUtilities.getParameter(params, PluginParameter.BORDER);
    if (border != null) {
      conversionOptions.setShowTableBorder(ParameterUtilities.getBoolean(border));
    }
  }

  private void adjustLineNumbers(Map params, JavaSourceConversionOptions conversionOptions)
    throws PluginException {
    String lineNumbers = ParameterUtilities.getParameter(params, PluginParameter.LINE_NUMBERS);
    if (lineNumbers != null) {
      conversionOptions.setShowLineNumbers(ParameterUtilities.getBoolean(lineNumbers));
    }
  }

  private void adjustTabSize(Map params, JavaSourceConversionOptions conversionOptions) throws PluginException {
    String tabSize = ParameterUtilities.getParameter(params, PluginParameter.TAB_SIZE);
    if (tabSize != null) {
      conversionOptions.setTabSize(ParameterUtilities.getInt(tabSize));
    }
  }

  private void adjustAlignment(Map params, JavaSourceConversionOptions conversionOptions) throws PluginException {
    String alignment = ParameterUtilities.getParameter(params, PluginParameter.ALIGNMENT);
    if (alignment != null) {
      HorizontalAlignment horizontalAlignment = HorizontalAlignment.getByName(alignment);
      if (horizontalAlignment == null) {
        throw new PluginException(
          "Unsupported alignment '"
            + alignment
            + "' - valid values are: "
            + ConversionOptionsUtilities.getAvailableHorizontalAlignmentNameString());
      }
      conversionOptions.setHorizontalAlignment(horizontalAlignment);
    }
  }
}
