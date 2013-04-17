package de.java2html.properties.test;

import de.java2html.javasource.JavaSourceType;
import de.java2html.options.HorizontalAlignment;
import de.java2html.options.JavaSourceConversionOptions;
import de.java2html.options.JavaSourceStyleEntry;
import de.java2html.options.JavaSourceStyleTable;
import de.java2html.properties.ConversionOptionsPropertiesReader;
import de.java2html.properties.ConversionOptionsPropertiesWriter;
import de.java2html.properties.IllegalPropertyValueException;
import de.java2html.util.RGB;
import junit.framework.TestCase;

/**
 * @author Markus
 */
public class ConversionOptionsPropertiesPersistenceTest extends TestCase {
  private JavaSourceConversionOptions options;
  
  protected void setUp() throws Exception {
    options = JavaSourceConversionOptions.getDefault();
  }
  
  public void testPersistDefaultOptions() throws IllegalPropertyValueException {
    assertEquals(options, writeAndRead(options));
  }
  
  public void testPersistDefaultStyleTableName() throws IllegalPropertyValueException {
    JavaSourceStyleTable styleTable = JavaSourceStyleTable.getDefaultKawaStyleTable();
    options.setStyleTable(styleTable);
    assertEquals(styleTable.getName(), writeAndRead(options).getStyleTable().getName());
  }

  public void testPersistTabSize() throws IllegalPropertyValueException {
    options.setTabSize(11);
    assertEquals(11, writeAndRead(options).getTabSize());
  }

  public void testPersistShowLineNumbers() throws IllegalPropertyValueException {
    options.setShowLineNumbers(!options.isShowLineNumbers());
    assertEquals(options.isShowLineNumbers(), writeAndRead(options).isShowLineNumbers());
  }
  
  public void testPersistShowJava2HtmlLink() throws IllegalPropertyValueException {
    options.setShowJava2HtmlLink(!options.isShowJava2HtmlLink());
    assertEquals(options.isShowJava2HtmlLink(), writeAndRead(options).isShowJava2HtmlLink());
  }
  
  public void testPersistShowFileName() throws IllegalPropertyValueException {
    options.setShowFileName(!options.isShowFileName());
    assertEquals(options.isShowFileName(), writeAndRead(options).isShowFileName());
  }
  
  public void testPersistShowTableBorder() throws IllegalPropertyValueException {
    options.setShowTableBorder(!options.isShowTableBorder());
    assertEquals(options.isShowTableBorder(), writeAndRead(options).isShowTableBorder());
  }
  
  public void testPersistHorizontalAlignment() throws IllegalPropertyValueException {
    options.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    assertEquals(options.getHorizontalAlignment(), writeAndRead(options).getHorizontalAlignment());
  }
  
  public void testPersistStyleEntry1() throws IllegalPropertyValueException {
    JavaSourceStyleEntry entry = new JavaSourceStyleEntry(RGB.MAGENTA, true, false);
    options.getStyleTable().put(JavaSourceType.COMMENT_LINE, entry);
    assertEquals(entry, writeAndRead(options).getStyleTable().get(JavaSourceType.COMMENT_LINE));
  }

  public void testPersistStyleEntry2() throws IllegalPropertyValueException {
    JavaSourceStyleEntry entry = new JavaSourceStyleEntry(RGB.GREEN, false, true);
    options.getStyleTable().put(JavaSourceType.COMMENT_LINE, entry);
    assertEquals(entry, writeAndRead(options).getStyleTable().get(JavaSourceType.COMMENT_LINE));
  }
  
  private JavaSourceConversionOptions writeAndRead(JavaSourceConversionOptions options) throws IllegalPropertyValueException {
    return new ConversionOptionsPropertiesReader().read(new ConversionOptionsPropertiesWriter().write(options));
  }
}