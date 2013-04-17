package de.java2html.plugin.jspwiki;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.ecyrd.jspwiki.plugin.PluginException;

/**
 * @author Markus Gebhard
 */
public class PluginParameterChecker {

  public void checkParametersSupported(Map params) throws PluginException {
    checkParameterKeysSupported(params.keySet().toArray());
  }

  private void checkParameterKeysSupported(Object[] parameterKeys) throws PluginException {
    for (int i = 0; i < parameterKeys.length; i++) {
      checkParameterKeySupported(parameterKeys[i]);
    }
  }

  private void checkParameterKeySupported(Object parameterKey) throws PluginException {
    if (!(parameterKey instanceof String)) {
      return;
    }
    String parameterName = (String) parameterKey;
    if (PluginParameter.isInternal(parameterName)) {
      return;
    }
    if (!PluginParameter.getAllNames().contains(parameterName)) {
      throw new PluginException(
        "Unsupported parameter '" + parameterName + "'." + createValidParameterHtmlTable());
    }
  }

  public static String createValidParameterHtmlTable() {
    StringBuffer html = new StringBuffer();
    html.append(
      "<table border=\"1\"><tr>" + "<th>Parameter</th>" + "<th>Description</th>" + "<th>Example</th>" + "</tr>");
    Set set = PluginParameter.getAll();
    for (Iterator iter = set.iterator(); iter.hasNext();) {
      PluginParameter parameter = (PluginParameter) iter.next();
      if (!parameter.isInternal()) {
        appendParameterTableRow(html, parameter);
      }
    }
    html.append("</table>");
    return html.toString();
  }

  private static void appendParameterTableRow(StringBuffer html, PluginParameter parameter) {
    html.append(
      "<tr>"
        + "<td><code>"
        + parameter.getName()
        + "</code></td>"
        + "<td>"
        + parameter.getDescription()
        + "</td>"
        + "<td><code>"
        + parameter.getName()
        + "="
        + "'"
        + parameter.getExampleValue()
        + "'"
        + "</code></td>"
        + "</tr>");
  }
}