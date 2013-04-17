package de.java2html.plugin;

import java.util.Map;

import com.ecyrd.jspwiki.plugin.PluginException;

/**
 * @author Markus Gebhard
 */
public class ParameterUtilities {
  private ParameterUtilities() {
    //nothing to do
  }

  public static String getParameter(Map params, IParameter parameter) {
    String stringValue = null;
    Object value = params.get(parameter.getName());
    if (value != null && value instanceof String) {
      stringValue = (String) value;
    }
    return stringValue;
  }

  public static int getInt(String intString) throws PluginException {
    try {
      return Integer.parseInt(intString);
    }
    catch (NumberFormatException e) {
      throw new PluginException("Illegal value for integer '" + intString + "'");
    }
  }

  public static boolean getBoolean(String booleanString) throws PluginException {
    if ("true".equals(booleanString) || "on".equals(booleanString)) {
      return true;
    }
    if ("false".equals(booleanString) || "off".equals(booleanString)) {
      return false;
    }
    throw new PluginException("Illegal value for boolean '" + booleanString + "'");
  }
}