package de.java2html.javasource.test;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import de.java2html.javasource.JavaSource;
import de.java2html.javasource.JavaSourceParser;
import de.java2html.javasource.JavaSourceType;

import junit.framework.TestCase;

/**
 * @author Markus Gebhard
 */
public abstract class JavaSourceParserTestCase extends TestCase {

  private static Map map;

  /* Defines a simple language for encoding JavaSourcType constants
   * to one character each.
   * Space character == ignore */
  static {
    map = new HashMap();
    //TODO Mar 12, 2004 (Markus Gebhard) Generics: map.put("G"), JavaSourceType.GENRICS);
    map.put("A", JavaSourceType.ANNOTATION);
    map.put("_", JavaSourceType.BACKGROUND);
    map.put(":", JavaSourceType.LINE_NUMBERS); //irrelevant for parsing
    map.put("#", JavaSourceType.COMMENT_BLOCK);
    map.put("/", JavaSourceType.COMMENT_LINE);
    map.put("K", JavaSourceType.KEYWORD);
    map.put("S", JavaSourceType.STRING);
    map.put("'", JavaSourceType.CHAR_CONSTANT);
    map.put("1", JavaSourceType.NUM_CONSTANT);
    map.put("{", JavaSourceType.PARENTHESIS);
    map.put("T", JavaSourceType.CODE_TYPE);
    map.put("C", JavaSourceType.CODE);
    map.put("@", JavaSourceType.JAVADOC_KEYWORD);
    map.put("<", JavaSourceType.JAVADOC_HTML_TAG);
    map.put("L", JavaSourceType.JAVADOC_LINKS);
    map.put("*", JavaSourceType.JAVADOC);
    map.put("-", JavaSourceType.UNDEFINED); //irrelevant for parsing
  }

  protected static JavaSource doParse(String text) throws IOException {
    StringReader stringReader = new StringReader(text);
    JavaSourceParser parser = new JavaSourceParser();
    return parser.parse(stringReader);
  }

  protected void assertParsedTypesEquals(String sourceCode, String typeCode) throws IOException {
    assertNotNull("SourceCode null - testcase broken", sourceCode);
    assertNotNull("TypeCode null - testcase broken", typeCode);
    JavaSource source = doParse(sourceCode);

    assertTrue(
        "Error in TestCase: more types specified than resulted",
        source.getClassification().length >= typeCode.length());

    for (int i = 0; i < typeCode.length(); ++i) {
      if (typeCode.charAt(i) == ' ') {
        continue;
      }
      JavaSourceType expectedType = getSourceTypeForTypeCode(typeCode.charAt(i));
      if (expectedType == null) {
        throw new IllegalArgumentException("Unknown type mapping charcter for testing: '"
            + typeCode.charAt(i)
            + "'");
      }
      assertEquals("At character " + i + " (" + sourceCode.substring(i) + "):", expectedType, source
          .getClassification()[i]);
    }
  }

  private JavaSourceType getSourceTypeForTypeCode(char character) {
    return (JavaSourceType) map.get(String.valueOf(character));
  }
}