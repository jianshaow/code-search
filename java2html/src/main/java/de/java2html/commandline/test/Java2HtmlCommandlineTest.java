package de.java2html.commandline.test;

import de.java2html.commandline.IJava2HtmlConversion;
import de.java2html.commandline.IllegalCommandlineParametersException;
import de.java2html.commandline.Java2HtmlCommandline;
import de.java2html.commandline.Java2HtmlDirectoryConversion;
import de.java2html.commandline.Java2HtmlFileConversion;
import de.java2html.converter.JavaSource2HTMLConverter;
import de.java2html.converter.JavaSource2TeXConverter;
import de.java2html.options.JavaSourceConversionOptions;
import de.java2html.options.JavaSourceStyleTable;
import de.java2html.test.BasicTestCase;

/**
 * @author Markus Gebhard
 */
public class Java2HtmlCommandlineTest extends BasicTestCase {

  /* ------------------ File/Directory --------------- */

  public void testCreateDirectoryConverter() throws IllegalCommandlineParametersException {
    String[] arguments = new String[]{ "-srcdir", "d:/src" };
    IJava2HtmlConversion conversion = Java2HtmlCommandline.createCommandlineConversion(arguments);
    assertInstanceOf(Java2HtmlDirectoryConversion.class, conversion);
  }

  public void testCreateFileConverter() throws IllegalCommandlineParametersException {
    String[] arguments = new String[]{ "-srcfile", "d:/src/test.java" };
    IJava2HtmlConversion conversion = Java2HtmlCommandline.createCommandlineConversion(arguments);
    assertInstanceOf(Java2HtmlFileConversion.class, conversion);
  }

  /* ------------------ Converter type --------------- */

  public void testHtmlIsDefaultConverter() throws IllegalCommandlineParametersException {
    String[] arguments = new String[]{ "-srcfile", "d:/src/test.java" };
    IJava2HtmlConversion conversion = Java2HtmlCommandline.createCommandlineConversion(arguments);
    assertInstanceOf(JavaSource2HTMLConverter.class, conversion.getConverter());
  }

  public void testUsesSpecifiedConverter() throws IllegalCommandlineParametersException {
    String[] arguments = new String[]{ "-srcfile", "d:/src/test.java", "-converter", "TeX" };
    IJava2HtmlConversion conversion = Java2HtmlCommandline.createCommandlineConversion(arguments);
    assertInstanceOf(JavaSource2TeXConverter.class, conversion.getConverter());
  }

  public void testUnknwonConverterThrowsException() {
    final String[] arguments = new String[]{ "-srcfile", "d:/src/test.java", "-converter", "UnknownConverterName" };
    assertThrowsException(IllegalCommandlineParametersException.class, new IBlock() {
      public void execute() throws Exception {
        Java2HtmlCommandline.createCommandlineConversion(arguments);
      }
    });
  }

  /* ------------------ Style --------------- */
  public void testUsesCorrectDefaultStyle() throws IllegalCommandlineParametersException {
    String[] arguments = new String[]{ "-srcfile", "d:/src/test.java" };
    IJava2HtmlConversion conversion = Java2HtmlCommandline.createCommandlineConversion(arguments);
    assertEquals(JavaSourceStyleTable.getDefault(), conversion

    .getConversionOptions().getStyleTable());
  }

  public void testUsesSpecifiedStyle() throws IllegalCommandlineParametersException {
    String[] arguments = new String[]{ "-srcfile", "d:/src/test.java", "-style", "Monochrome" };
    IJava2HtmlConversion conversion = Java2HtmlCommandline.createCommandlineConversion(arguments);
    assertEquals(JavaSourceStyleTable.getPredefinedTable("Monochrome"), conversion
        .getConversionOptions().getStyleTable());
  }

  public void testUnknownStyleThrowsException() {
    final String[] arguments = new String[]{ "-srcfile", "d:/src/test.java", "-style", "UnknownStyleName" };
    assertThrowsException(IllegalCommandlineParametersException.class, new IBlock() {
      public void execute() throws Exception {
        Java2HtmlCommandline.createCommandlineConversion(arguments);
      }
    });
  }

  /* ------------------ Options --------------- */

  public void testUsesDefaultTabs() throws IllegalCommandlineParametersException {
    String[] arguments = new String[]{ "-srcfile", "d:/src/test.java" };
    IJava2HtmlConversion conversion = Java2HtmlCommandline.createCommandlineConversion(arguments);
    assertEquals(JavaSourceConversionOptions.getDefault().getTabSize(), conversion
        .getConversionOptions().getTabSize());

  }

  public void testUsesSpecifiedTabs() throws IllegalCommandlineParametersException {
    String[] arguments = new String[]{ "-srcfile", "d:/src/test.java", "-tabs", "5" };
    IJava2HtmlConversion conversion = Java2HtmlCommandline.createCommandlineConversion(arguments);
    assertEquals(5, conversion.getConversionOptions().getTabSize());
  }

  public void testIllegalTabsThrowsException() {
    final String[] arguments = new String[]{ "-srcfile", "d:/src/test.java", "-tabs", "illegal" };
    assertThrowsException(IllegalCommandlineParametersException.class, new IBlock() {
      public void execute() throws Exception {
        Java2HtmlCommandline.createCommandlineConversion(arguments);
      }
    });
  }

  public void testNegativeTabsThrowsException() {
    final String[] arguments = new String[]{ "-srcfile", "d:/src/test.java", "-tabs", "-3" };
    assertThrowsException(IllegalCommandlineParametersException.class, new IBlock() {
      public void execute() throws Exception {
        Java2HtmlCommandline.createCommandlineConversion(arguments);
      }
    });
  }

  /* ------------------ Misc --------------- */

  public void testUnsupportedArgumentThrowsException() {
    assertIsIllegalCommandLineArguments(new String[]{ "-anUnsupportedArgument" });
    assertIsIllegalCommandLineArguments(new String[]{ "-srcfile" });
    assertIsIllegalCommandLineArguments(new String[]{ "-srcfile", "d:/src/test.java", "-filemask", "*.txt" });
    assertIsIllegalCommandLineArguments(new String[]{
        "-srcfile",
        "d:/src/test.java",
        "-copytotargetunprocessedfiles" });
    assertIsIllegalCommandLineArguments(new String[]{
        "-srcdir",
        "d:/src",
        "-copytotargetunprocessedfiles",
        "true" });
    assertIsIllegalCommandLineArguments(new String[]{
        "-srcdir",
        "d:/src",
        "-copytotargetunprocessedfiles",
        "-anything" });
  }

  private static void assertIsIllegalCommandLineArguments(final String[] arguments) {
    assertThrowsException(IllegalCommandlineParametersException.class, new IBlock() {
      public void execute() throws Exception {
        Java2HtmlCommandline.createCommandlineConversion(arguments);
      }
    });
  }
}