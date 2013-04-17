package de.java2html.properties.test;

import java.util.Properties;

import junit.framework.TestCase;
import de.java2html.javasource.JavaSourceType;
import de.java2html.options.HorizontalAlignment;
import de.java2html.options.IConversionOptionsConstants;
import de.java2html.options.JavaSourceConversionOptions;
import de.java2html.options.JavaSourceStyleTable;
import de.java2html.properties.ConversionOptionsPropertiesReader;
import de.java2html.properties.IllegalPropertyValueException;
import de.java2html.util.RGB;

/**
 * @author Markus Gebhard
 */
public class ConversionOptionsPropertiesReaderTest extends TestCase {
  private ConversionOptionsPropertiesReader reader;

  protected void setUp() throws Exception {
    reader = new ConversionOptionsPropertiesReader();
  }

  public void testReadingEmptyProperties() throws IllegalPropertyValueException {
    Properties properties = new Properties();
    JavaSourceConversionOptions options = reader.read(properties);
    assertEquals(JavaSourceConversionOptions.getDefault(), options);
  }
  
  public void testUnknownPropertyIgnored() throws IllegalPropertyValueException {
    Properties properties = new Properties();
    properties.setProperty("_unsupportedkey_", "value"); //$NON-NLS-1$ //$NON-NLS-2$
    JavaSourceConversionOptions options = reader.read(properties);
    assertEquals(JavaSourceConversionOptions.getDefault(), options);
  }
  
  public void testReadDefaultStyleTableName() throws IllegalPropertyValueException {
    Properties properties = new Properties();
    properties.put(
      IConversionOptionsConstants.DEFAULT_STYLE_NAME,
      JavaSourceStyleTable.getDefaultKawaStyleTable().getName());
    JavaSourceConversionOptions options = reader.read(properties);
    assertEquals(JavaSourceStyleTable.getDefaultKawaStyleTable(), options.getStyleTable());
  }

  public void testReadIllegalDefaultStyleTableName() {
    Properties properties = new Properties();
    properties.put(
      IConversionOptionsConstants.DEFAULT_STYLE_NAME,
      "not_existing_" + JavaSourceStyleTable.getDefaultKawaStyleTable().getName()); //$NON-NLS-1$
    assertParsingPropertiesFails(properties);
  }

  private void assertParsingPropertiesFails(Properties properties) {
    try {
      reader.read(properties);
      fail();
    }
    catch (IllegalPropertyValueException expected) {
      //expected
    }
  }

  public void testTabSize() throws IllegalPropertyValueException {
    Properties properties = new Properties();
    properties.put(IConversionOptionsConstants.TAB_SIZE, "9"); //$NON-NLS-1$
    JavaSourceConversionOptions options = reader.read(properties);
    assertEquals(9, options.getTabSize());
  }

  public void testIllegalTabSize() {
    Properties properties = new Properties();
    properties.put(IConversionOptionsConstants.TAB_SIZE, "illegal"); //$NON-NLS-1$
    assertParsingPropertiesFails(properties);
  }

  public void testShowLineNumbers() throws IllegalPropertyValueException {
    Properties properties = new Properties();
    properties.put(IConversionOptionsConstants.SHOW_LINE_NUMBERS, "false"); //$NON-NLS-1$
    JavaSourceConversionOptions options = reader.read(properties);
    assertFalse(options.isShowLineNumbers());
  }

  public void testIllegalShowLineNumbers() {
    Properties properties = new Properties();
    properties.put(IConversionOptionsConstants.SHOW_LINE_NUMBERS, "illegal"); //$NON-NLS-1$
    assertParsingPropertiesFails(properties);
  }

  public void testShowFileName() throws IllegalPropertyValueException {
    Properties properties = new Properties();
    properties.put(IConversionOptionsConstants.SHOW_FILE_NAME, "true"); //$NON-NLS-1$
    JavaSourceConversionOptions options = reader.read(properties);
    assertTrue(options.isShowFileName());
  }

  public void testIllegalShowFileName() {
    Properties properties = new Properties();
    properties.put(IConversionOptionsConstants.SHOW_FILE_NAME, "illegal"); //$NON-NLS-1$
    assertParsingPropertiesFails(properties);
  }

  public void testShowTableBorder() throws IllegalPropertyValueException {
    Properties properties = new Properties();
    properties.put(IConversionOptionsConstants.SHOW_TABLE_BORDER, "true"); //$NON-NLS-1$
    JavaSourceConversionOptions options = reader.read(properties);
    assertTrue(options.isShowTableBorder());
  }

  public void testIllegalShowTableBorder() {
    Properties properties = new Properties();
    properties.put(IConversionOptionsConstants.SHOW_TABLE_BORDER, "illegal"); //$NON-NLS-1$
    assertParsingPropertiesFails(properties);
  }

  public void testShowJava2HtmlLink() throws IllegalPropertyValueException {
    Properties properties = new Properties();
    properties.put(IConversionOptionsConstants.SHOW_JAVA2HTML_LINK, "true"); //$NON-NLS-1$
    JavaSourceConversionOptions options = reader.read(properties);
    assertTrue(options.isShowJava2HtmlLink());
  }

  public void testIllegalJava2HtmlLink() {
    Properties properties = new Properties();
    properties.put(IConversionOptionsConstants.SHOW_JAVA2HTML_LINK, "illegal"); //$NON-NLS-1$
    assertParsingPropertiesFails(properties);
  }
  
  public void testHorizontalAlignment() throws IllegalPropertyValueException {
    Properties properties = new Properties();
    properties.put(IConversionOptionsConstants.HORIZONTAL_ALIGNMENT, HorizontalAlignment.RIGHT.getName());
    JavaSourceConversionOptions options = reader.read(properties);
    assertEquals(HorizontalAlignment.RIGHT, options.getHorizontalAlignment());
  }

  public void testIllegalHorizontalAlignment() {
    Properties properties = new Properties();
    properties.put(IConversionOptionsConstants.HORIZONTAL_ALIGNMENT, "illegal"); //$NON-NLS-1$
    assertParsingPropertiesFails(properties);
  }

  public void testColor() throws IllegalPropertyValueException {
    Properties properties = new Properties();
    properties.put(JavaSourceType.CODE.getName() + IConversionOptionsConstants.POSTFIX_COLOR, "255,255,255"); //$NON-NLS-1$
    JavaSourceConversionOptions options = reader.read(properties);
    assertEquals(RGB.WHITE, options.getStyleTable().get(JavaSourceType.CODE).getColor());
  }

  public void testIllegalColor() {
    Properties properties = new Properties();
    properties.put(JavaSourceType.CODE.getName() + IConversionOptionsConstants.POSTFIX_COLOR, "illegal"); //$NON-NLS-1$
    assertParsingPropertiesFails(properties);
  }

  public void testBold() throws IllegalPropertyValueException {
    Properties properties = new Properties();
    properties.put(JavaSourceType.CODE.getName() + IConversionOptionsConstants.POSTFIX_BOLD, "true"); //$NON-NLS-1$
    JavaSourceConversionOptions options = reader.read(properties);
    assertTrue(options.getStyleTable().get(JavaSourceType.CODE).isBold());
  }

  public void testIllegalBold() {
    Properties properties = new Properties();
    properties.put(JavaSourceType.CODE.getName() + IConversionOptionsConstants.POSTFIX_BOLD, "illegal"); //$NON-NLS-1$
    assertParsingPropertiesFails(properties);
  }
  
  public void testItalic() throws IllegalPropertyValueException {
    Properties properties = new Properties();
    properties.put(JavaSourceType.CODE.getName() + IConversionOptionsConstants.POSTFIX_ITALIC, "true"); //$NON-NLS-1$
    JavaSourceConversionOptions options = reader.read(properties);
    assertTrue(options.getStyleTable().get(JavaSourceType.CODE).isItalic());
  }

  public void testIllegalItalic() {
    Properties properties = new Properties();
    properties.put(JavaSourceType.CODE.getName() + IConversionOptionsConstants.POSTFIX_ITALIC, "illegal"); //$NON-NLS-1$
    assertParsingPropertiesFails(properties);
  }
}