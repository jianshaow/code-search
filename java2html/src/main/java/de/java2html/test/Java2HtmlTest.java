package de.java2html.test;

import java.io.IOException;
import java.io.StringReader;

import de.java2html.Java2Html;
import de.java2html.JavaSourceConversionSettings;
import de.java2html.javasource.JavaSource;
import de.java2html.javasource.JavaSourceIterator;
import de.java2html.javasource.JavaSourceParser;
import de.java2html.javasource.JavaSourceRun;
import de.java2html.options.HorizontalAlignment;
import de.java2html.options.JavaSourceConversionOptions;
import junit.framework.TestCase;

public class Java2HtmlTest extends TestCase {

  public void testConvertToHtml() {
    //Just a smoke test
    String result = Java2Html.convertToHtml("public static void main(){}"); //$NON-NLS-1$
    assertNotNull(result);
    assertTrue(result.indexOf("public") != -1); //$NON-NLS-1$
    assertTrue(result.indexOf("static") != -1); //$NON-NLS-1$
    assertTrue(result.indexOf("void") != -1); //$NON-NLS-1$
    assertTrue(result.indexOf("main") != -1); //$NON-NLS-1$
    assertTrue(result.indexOf("<font") != -1); //$NON-NLS-1$
    assertTrue(result.indexOf("</font>") != -1); //$NON-NLS-1$
    assertTrue(result.indexOf("public static") == -1); //$NON-NLS-1$
  }

  public void testConvertWithTabs() {
    String result = Java2Html.convertToHtml("public static void main(){\t\t}"); //$NON-NLS-1$
    assertNotNull(result);
  }

  public void testConvertToHtmlPage() {
    //Just a smoke test
    String result = Java2Html.convertToHtmlPage("public static void main(){}"); //$NON-NLS-1$
    assertNotNull(result);
    assertTrue(result.indexOf("public") != -1); //$NON-NLS-1$
    assertTrue(result.indexOf("static") != -1); //$NON-NLS-1$
    assertTrue(result.indexOf("void") != -1); //$NON-NLS-1$
    assertTrue(result.indexOf("main") != -1); //$NON-NLS-1$
    assertTrue(result.indexOf("<font") != -1); //$NON-NLS-1$
    assertTrue(result.indexOf("</font>") != -1); //$NON-NLS-1$
    assertTrue(result.indexOf("public static") == -1); //$NON-NLS-1$
    assertTrue(result.indexOf("<html>") != -1); //$NON-NLS-1$
    assertTrue(result.indexOf("</html>") != -1); //$NON-NLS-1$
  }

  public void testConvertBug0() {
    String result = Java2Html.convertToHtmlPage("package com.equate.core.bean;\r\n" //$NON-NLS-1$
        + "\r\n" //$NON-NLS-1$
        + "public interface ResultBean {\r\n" //$NON-NLS-1$
        + "  \r\n" //$NON-NLS-1$
        + "}\r\n" //$NON-NLS-1$
        + "\r\n" //$NON-NLS-1$
        + "\r\n"); //$NON-NLS-1$
    assertNotNull(result);
  }

  public void testConvertBug0Base() throws IOException {
    String javaSource = "\r\n\r\n"; //$NON-NLS-1$

    StringReader stringReader = new StringReader(javaSource);
    JavaSource source = new JavaSourceParser().parse(stringReader);

    //System.err.println("'"+source.getCode()+"'");
    //System.err.println(source.getCode().length()+"
    // "+source.getClassification().length);

    //  assertNotNull(source);

    JavaSourceIterator iterator = source.getIterator();
    JavaSourceRun run1 = iterator.getNext();
    assertNotNull(run1);

    // System.err.println("'"+run1.getCode()+"'");
    // System.err.println("'"+run1.getType()+"'");
    // System.err.println("'"+run1.isAtStartOfLine()+"'");
    // System.err.println("'"+run1.isAtEndOfLine()+"'");

    JavaSourceRun run2 = iterator.getNext();
    assertNotNull(run2);
    // System.err.println("'"+run2.getCode()+"'");
    // System.err.println("'"+run2.getType()+"'");
    // System.err.println("'"+run2.isAtStartOfLine()+"'");
    // System.err.println("'"+run2.isAtEndOfLine()+"'");

    Java2Html.convertToHtmlPage(javaSource);
  }

  public void testOptionsDefaultToNoAchor() {
    assertFalse("By specification the html anchors must be off in default options.", JavaSourceConversionOptions //$NON-NLS-1$
        .getDefault().isAddLineAnchors());
  }

  public void testNoAnchors() {
    JavaSourceConversionOptions options = JavaSourceConversionOptions.getDefault();
    String result = createHelloWorldConversionResult(options);
    assertTrue(result.indexOf("<a name=\"1\">") == -1); //$NON-NLS-1$
    assertTrue(result.indexOf("<a name=\"2\">") == -1); //$NON-NLS-1$
    assertTrue(result.indexOf("<a name=\"3\">") == -1); //$NON-NLS-1$
  }

  public void testAnchors() {
    JavaSourceConversionOptions options = JavaSourceConversionOptions.getDefault();
    options.setAddLineAnchors(true);
    String result = createHelloWorldConversionResult(options);
    assertTrue(result.indexOf("<a name=\"1\">") != -1); //$NON-NLS-1$
    assertTrue(result.indexOf("<a name=\"2\">") != -1); //$NON-NLS-1$
    assertTrue(result.indexOf("<a name=\"3\">") != -1); //$NON-NLS-1$
  }

  public void testAnchorPrefix() {
    JavaSourceConversionOptions options = JavaSourceConversionOptions.getDefault();
    options.setAddLineAnchors(true);
    options.setLineAnchorPrefix("prefix"); //$NON-NLS-1$
    String result = createHelloWorldConversionResult(options);
    assertTrue(result.indexOf("<a name=\"prefix1\">") != -1); //$NON-NLS-1$
    assertTrue(result.indexOf("<a name=\"prefix2\">") != -1); //$NON-NLS-1$
    assertTrue(result.indexOf("<a name=\"prefix3\">") != -1); //$NON-NLS-1$
  }

  public void testLeftTableAlignment() {
    JavaSourceConversionOptions options = JavaSourceConversionOptions.getDefault();
    options.setHorizontalAlignment(HorizontalAlignment.LEFT);
    String result = createHelloWorldConversionResult(options);
    assertTrue(result.indexOf(" align=\"left\"") != -1); //$NON-NLS-1$
  }

  public void testCenterTableAlignment() {
    JavaSourceConversionOptions options = JavaSourceConversionOptions.getDefault();
    options.setHorizontalAlignment(HorizontalAlignment.CENTER);
    String result = createHelloWorldConversionResult(options);
    assertTrue(result.indexOf(" align=\"center\"") != -1); //$NON-NLS-1$
  }

  public void testRightTableAlignment() {
    JavaSourceConversionOptions options = JavaSourceConversionOptions.getDefault();
    options.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    String result = createHelloWorldConversionResult(options);
    assertTrue(result.indexOf(" align=\"right\"") != -1); //$NON-NLS-1$
  }

  private String createHelloWorldConversionResult(JavaSourceConversionOptions options) {
    return Java2Html.convertToHtmlPage(
        "public static void main(){\n System.out.println(\"Hello\");\n}", //$NON-NLS-1$
        new JavaSourceConversionSettings(options));
  }
}