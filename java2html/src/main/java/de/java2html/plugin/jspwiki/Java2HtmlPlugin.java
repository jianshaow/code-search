package de.java2html.plugin.jspwiki;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.ecyrd.jspwiki.WikiContext;
import com.ecyrd.jspwiki.plugin.PluginException;
import com.ecyrd.jspwiki.plugin.WikiPlugin;

import de.java2html.JavaSourceConversionSettings;
import de.java2html.Version;
import de.java2html.converter.JavaSourceConverterProvider;
import de.java2html.options.JavaSourceConversionOptions;
import de.java2html.plugin.AbstractJava2HtmlPlugin;
import de.java2html.plugin.ParameterUtilities;
import de.java2html.util.IllegalConfigurationException;

/**
 * @author Markus Gebhard
 */
public class Java2HtmlPlugin extends AbstractJava2HtmlPlugin implements WikiPlugin {
  public static final String DEFAULT_USAGE_MESSAGE = "Missing parameter 'source', 'url' or 'attachment' or missing body. Valid parameters are:"
      + PluginParameterChecker.createValidParameterHtmlTable();

  public String execute(WikiContext context, Map params) throws PluginException {
    new PluginParameterChecker().checkParametersSupported(params);

    String printVersion = ParameterUtilities.getParameter(params, PluginParameter.PRINT_VERSION);
    if (printVersion != null) {
      return "<b>" + Version.getJava2HtmlConverterTitle() + "</b>";
    }

    JavaSourceConversionOptions conversionOptions;
    try {
      conversionOptions = new PluginConversionOptionsParser().parseConversionOptions(params);
    }
    catch (IllegalConfigurationException e) {
      throw new PluginException(e.getMessage(), e);
    }

    String converterName = ParameterUtilities.getParameter(params, PluginParameter.CONVERTER);
    if (converterName == null) {
      converterName = JavaSourceConverterProvider.getDefaultConverterName();
    }
    else {
      if (JavaSourceConverterProvider.getJavaSourceConverterByName(converterName) == null) {
        throw new PluginException("Unknown converter '" + converterName + "'."); //$NON-NLS-1$ //$NON-NLS-2$
      }
    }

    String urlString = ParameterUtilities.getParameter(params, PluginParameter.URL);
    if (urlString != null) {
      return convertFileFromUrl(urlString, conversionOptions, context, converterName);
    }

    String source = ParameterUtilities.getParameter(params, PluginParameter.SOURCE);
    if (source != null) {
      return convert(source, new JavaSourceConversionSettings(conversionOptions, converterName));
    }
    String body = ParameterUtilities.getParameter(params, PluginParameter._BODY);
    if (body != null) {
      body = removeLeadingNewLine(body);
      return convert(body, new JavaSourceConversionSettings(conversionOptions, converterName));
    }
    String attachment = ParameterUtilities.getParameter(params, PluginParameter.ATTACHMENT);
    if (attachment != null) {
      new PluginSecurityManager(context).checkValidAttachmentUrlPart(attachment);
      URL url = createAttachmentUrl(context, attachment);
      try {
        return convert(url, new JavaSourceConversionSettings(conversionOptions, converterName));
      }
      catch (IOException e) {
        throw new PluginException("Unable to read Url '" + urlString + "'", e);  //$NON-NLS-1$//$NON-NLS-2$
      }
    }

    throw new PluginException(DEFAULT_USAGE_MESSAGE);
  }

  private String removeLeadingNewLine(String body) {
    if (body.length() > 0 && body.charAt(0) == '\n') {
      return body.substring(1);
    }
    else {
      return body;
    }
  }

  private URL createAttachmentUrl(WikiContext context, String attachment) throws PluginException {
    String baseUrl = context.getEngine().getBaseURL();
    if (baseUrl == null || baseUrl.length() == 0) {
      throw new PluginException(
          "Unable to determine the base url for this wiki. Please contact the wiki administrator to adjust the settings."); //$NON-NLS-1$
    }
    try {
      return new URL(baseUrl + "attach?page=" + context.getPage().getName() + "/" + attachment); //$NON-NLS-1$ //$NON-NLS-2$
    }
    catch (MalformedURLException e) {
      throw new PluginException("Unable to build attachment URL", e); //$NON-NLS-1$
    }
  }

  private String convertFileFromUrl(
      String urlString,
      JavaSourceConversionOptions conversionOptions,
      WikiContext context,
      String converterName) throws PluginException {
    URL url = null;
    try {
      url = new URL(urlString);
    }
    catch (MalformedURLException e) {
      throw new PluginException("Unable to open url '" + urlString + "'", e); //$NON-NLS-1$ //$NON-NLS-2$
    }

    new PluginSecurityManager(context).checkUrlAccessEnabled(url);

    try {
      return convert(url, new JavaSourceConversionSettings(conversionOptions, converterName));
    }
    catch (IOException e) {
      throw new PluginException("Unable to read Url '" + url + "'", e); //$NON-NLS-1$ //$NON-NLS-2$
    }
  }
}