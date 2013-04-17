package de.java2html.javasource.test;

import java.io.IOException;
import java.io.Reader;

import de.java2html.javasource.JavaSource;
import de.java2html.javasource.JavaSourceParser;
import de.java2html.javasource.JavaSourceType;
import de.java2html.options.JavaSourceConversionOptions;

/**
 * @author Markus Gebhard
 */
public class JavaSourceParserTest extends JavaSourceParserTestCase {
  public void testParseNull() throws IOException {
    JavaSourceParser parser = new JavaSourceParser();
    try {
      parser.parse((Reader) null);
      fail();
    }
    catch (IllegalArgumentException expected) {
      //expected
    }
  }

  public void testParseEmpty() throws IOException {
    JavaSource source = doParse(""); //$NON-NLS-1$
    assertNotNull(source);
    assertEquals("", source.getCode()); //$NON-NLS-1$
    assertEquals(0, source.getLineCount());
    assertEquals(0, source.getMaxLineLength());
    assertNotNull(source.getClassification());
    assertEquals(0, source.getClassification().length);
    assertNotNull(source.getStatistic());
  }

  public void testParseSimple() throws IOException {
    JavaSource source = doParse("public String text =\"test\";"); //$NON-NLS-1$
    assertNotNull(source);
    assertEquals("public String text =\"test\";", source.getCode()); //$NON-NLS-1$
    assertEquals(1, source.getLineCount());
    assertEquals(27, source.getMaxLineLength());
    assertNotNull(source.getClassification());
    assertEquals(27, source.getClassification().length);
    assertNotNull(source.getStatistic());
  }

  public void testParseSingleTab() throws IOException {
    int tabSize = JavaSourceConversionOptions.getDefault().getTabSize();
    JavaSource source = doParse("\t"); //$NON-NLS-1$
    assertNotNull(source);
    assertEquals(tabSize, source.getCode().length());
  }

  public void testParseTabs() throws IOException {
    int tabSize = JavaSourceConversionOptions.getDefault().getTabSize();

    JavaSource source = doParse("\t\tpublic String text =\"test\";"); //$NON-NLS-1$
    assertNotNull(source);
    assertEquals(tabSize * 2 + "public String text =\"test\";".length(), source.getCode().length()); //$NON-NLS-1$
    assertEquals("public String text =\"test\";", source.getCode().trim()); //$NON-NLS-1$
  }

  public void testParse() throws IOException {
    JavaSource source = doParse("package test.it; public class Main{ public static void main(String [] " //$NON-NLS-1$
        + "args){System.out.println(}}\"Hello World!\");}}"); //$NON-NLS-1$
    assertNotNull(source);
  }

  public void testSimplePackage() throws Exception {
    JavaSource source = doParse("package de.java2html;"); //$NON-NLS-1$
    assertEquals("de.java2html", source.getStatistic().getPackageName()); //$NON-NLS-1$
  }

  public void testPackageWithPrefix() throws Exception {
    JavaSource source = doParse("/* foo.. */ package de.java2html;"); //$NON-NLS-1$
    assertEquals("de.java2html", source.getStatistic().getPackageName()); //$NON-NLS-1$
  }

  public void testPackageWithEmptySpace() throws Exception {
    JavaSource source = doParse("package       de.java2html      ;"); //$NON-NLS-1$
    assertEquals("de.java2html", source.getStatistic().getPackageName()); //$NON-NLS-1$
  }

  //  public void testHandlesSimpleLineBreakCorrect() throws Exception {
  //    String TEXT1 = "test\nline two";
  //    JavaSource source = doParse(TEXT1);
  //    assertEquals(TEXT1.length(), source.getCode().length());
  //    assertEquals(TEXT1.length(), source.getClassification().length);
  //  }

  //  public void testHandlesDoubleLineBreakCorrect() throws Exception {
  //    String TEXT1 = "test\r\nline two";
  //    JavaSource source = doParse(TEXT1);
  //    assertEquals(TEXT1.length(), source.getCode().length());
  //    assertEquals(TEXT1.length(), source.getClassification().length);
  //  }

  public void testParseTypeCHAR_CONSTANT() throws IOException {
    String text = "'c'"; //$NON-NLS-1$
    String type = "'''"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testParseTypeCODE() throws IOException {
    String text = "doThis()"; //$NON-NLS-1$
    String type = "CCCCCC{{"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testParseTypeCODE_TYPE() throws IOException {
    String text = "int doThis()"; //$NON-NLS-1$
    String type = "TTT_CCCCCC{{"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testParseTypeCOMMENT_BLOCK() throws IOException {
    String text = "/* int doThis() */"; //$NON-NLS-1$
    String type = "##_###_########_##"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testParseTypeCOMMENT_LINE() throws IOException {
    String text = "// /** int doThis()"; //$NON-NLS-1$
    String type = "//_///_///_////////"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testParseTypeJAVADOC() throws IOException {
    String text = "/** int doThis() */"; //$NON-NLS-1$
    String type = "***_***_********_**"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testParseTypeJAVADOC_HTML_TAG() throws IOException {
    String text = "/** <code>int</code> doThis() */"; //$NON-NLS-1$
    String type = "***_<<<<<<***<<<<<<<_********_**"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testParseTypeJAVADOC_KEYWORD() throws IOException {
    String text = "/** @deprecated doThis() */"; //$NON-NLS-1$
    String type = "***_@@@@@@@@@@@_********_**"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testParseTypeJAVADOC_KEYWORDWithoutSpace() throws IOException {
    String text = "/**@see*/"; //$NON-NLS-1$
    String type = "***@@@@**"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testParseTypeKEYWORD() throws IOException {
    String text = "public void"; //$NON-NLS-1$
    String type = "KKKKKK_TTTT"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testParseTypeNUM_CONSTANT() throws IOException {
    String text = "int i = 1;"; //$NON-NLS-1$
    String type = "TTT_C_C_1C"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testParseTypePARENTHESIS() throws IOException {
    String text = "{ }"; //$NON-NLS-1$
    String type = "{_{"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testParseTypeSTRING() throws IOException {
    String TEXT = "text = \"\\\"\";"; //$NON-NLS-1$
    JavaSource source = doParse(TEXT);
    assertEquals(JavaSourceType.STRING, source.getClassification()[8]);
    assertEquals(JavaSourceType.STRING, source.getClassification()[9]);
  }

  public void testLineBreaks1() throws IOException {
    String TEXT = "this\nand that"; //$NON-NLS-1$
    JavaSource source = doParse(TEXT);
    assertEquals(2, source.getLineCount());
    //    assertEquals(TEXT, source.getCode());
  }

  public void testLineBreaks2() throws IOException {
    String TEXT = "this\r\nand that"; //$NON-NLS-1$
    JavaSource source = doParse(TEXT);
    assertEquals(2, source.getLineCount());
  }

  //TODO: BufferedReader cuts linebreaks at the end - not serious, but look at it somewhen.
  // public void testLineBreaks3() throws IOException{
  //   JavaSource source = JavaSourceParserTest.doParse("a\n");
  //   assertEquals("a\r\n", source.getCode());
  //   
  //   source = JavaSourceParserTest.doParse("a\n\n");
  //   assertEquals("a\r\n\r\n", source.getCode());
  // }

  public void testParseTypeJAVADOC_LINKAsOrdinaryTag() throws IOException {
    String text = "/** @link this... */"; //$NON-NLS-1$
    String type = "***_@@@@@_*******_**"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testParseTypeJAVADOC_LINKS() throws IOException {
    String text = "/** {@link this...} */"; //$NON-NLS-1$
    String type = "***_LLLLLL_LLLLLLLL_**"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testParseTypeJAVADOC_LINKSDouble() throws IOException {
    String text = "/** {@link abc}{@link def} */"; //$NON-NLS-1$
    String type = "***_LLLLLL_LLLLLLLLLL_LLLL_**"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testParseTypeJAVADOC_LINKSOutliers() throws IOException {
    String text = "/**@link*/"; //$NON-NLS-1$
    String type = "***@@@@@**"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);

    text = "/**{@link*/"; //$NON-NLS-1$
    type = "****@@@@@**"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);

    text = "/**@linka*/"; //$NON-NLS-1$
    type = "***********"; //Not a valid keyword //$NON-NLS-1$
    assertParsedTypesEquals(text, type);

    text = "/**@link a*/"; //$NON-NLS-1$
    type = "***@@@@@_***"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);

    text = "/**{@link}*/"; //$NON-NLS-1$
    type = "***LLLLLLL**"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);

    text = "/**{@link }*/"; //$NON-NLS-1$
    type = "***LLLLLL_L**"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  //Unmatched JavaDoc-Link should be a JavaDoc tag  
  public void testParseTypeJAVADOC_LINKSOutlier1() throws IOException {
    String text = "/** {@link text */ public String[] texts=new char[]{'t', 'u'};"; //$NON-NLS-1$
    String type = "***_*@@@@@_****_**_KKKKKK_CCCCCC{{_CCCCCCKKK_TTTT{{{'''C_'''{C"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testEnumKeyword() throws IOException {
    String text = "public enum Coin {"; //$NON-NLS-1$
    String type = "KKKKKK_KKKK_CCCC_{"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testAnnotation() throws IOException {
    String text = "public class Test { @java.lang.Deprecated public void bla() {}}"; //$NON-NLS-1$
    String type = "KKKKKK_KKKKK_CCCC_{_AAAAAAAAAAAAAAAAAAAAA_KKKKKK_TTTT_CCC{{_{{{"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  public void testAnnotationInterfaceKeyword() throws IOException {
    String text = "public @interface Coin {"; //$NON-NLS-1$
    String type = "KKKKKK_KKKKKKKKKK_CCCC_{"; //$NON-NLS-1$
    assertParsedTypesEquals(text, type);
  }

  //TODO Mar 12, 2004 (Markus Gebhard) Generics:
  //  public void testGenerics() throws IOException {
  //    String text = "Vector<String>";
  //    String type = "CCCCCCGGGGGGGG";
  //    assertParsedTypesEquals(text, type);
  //
  //    text = "Seq<Seq<A>>";
  //    type = "CCCGGGGGGGG";
  //    assertParsedTypesEquals(text, type);
  //
  //    text = "Seq<String>.Zipper<Integer>";
  //    type = "CCCGGGGGGGGCCCCCCCGGGGGGGGG";
  //    assertParsedTypesEquals(text, type);
  //
  //    text = "Collection<Integer>";
  //    type = "CCCCCCCCCCGGGGGGGGG";
  //    assertParsedTypesEquals(text, type);
  //
  //    text = "Pair<String,String>";
  //    type = "CCCCGGGGGGGGGGGGGGG";
  //    assertParsedTypesEquals(text, type);
  //  }
}