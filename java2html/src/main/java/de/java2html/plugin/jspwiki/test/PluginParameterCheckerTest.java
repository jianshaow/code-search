package de.java2html.plugin.jspwiki.test;

import java.util.HashMap;

import com.ecyrd.jspwiki.plugin.PluginException;

import de.java2html.plugin.jspwiki.PluginParameter;
import de.java2html.plugin.jspwiki.PluginParameterChecker;
import junit.framework.TestCase;

/**
 * @author Markus Gebhard
 */
public class PluginParameterCheckerTest extends TestCase {
  private HashMap parameters = new HashMap();

  public void testEmptyParameterListIsSupported() throws PluginException {
    new PluginParameterChecker().checkParametersSupported(parameters);
  }

  public void testInternalParameterIsSupported() throws PluginException {
    parameters.put("_body", "");
    new PluginParameterChecker().checkParametersSupported(parameters);
  }

  public void testIllegalParameterIsNotSupported() {
    try {
      parameters.put("A very unsupported parameter am I", "");
      new PluginParameterChecker().checkParametersSupported(parameters);
      fail();
    }
    catch (PluginException expected) {
      //expected
    }
  }

  public void testValidParameterIsSupported() throws PluginException {
    parameters.put(PluginParameter.SOURCE.getName(), "");
    new PluginParameterChecker().checkParametersSupported(parameters);
  }

  public void testNonStringObjectIsSupported() throws PluginException {
    parameters.put(new Integer(42), "");
    new PluginParameterChecker().checkParametersSupported(parameters);
  }
 }