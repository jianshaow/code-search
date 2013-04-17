package de.java2html.javasource.test;

import java.io.IOException;
import java.util.NoSuchElementException;

import de.java2html.javasource.JavaSource;
import de.java2html.javasource.JavaSourceIterator;
import de.java2html.javasource.JavaSourceRun;
import de.java2html.javasource.JavaSourceType;
import junit.framework.TestCase;

public class JavaSourceIteratorTest extends TestCase {
  public void testEmpty() throws IOException{
    JavaSource source = JavaSourceParserTestCase.doParse(""); //$NON-NLS-1$
    JavaSourceIterator iterator = source.getIterator();
    assertFalse(iterator.hasNext());
  }

  public void testFirstIsNewLine() throws IOException{
    JavaSource source = JavaSourceParserTestCase.doParse("\na"); //$NON-NLS-1$
    JavaSourceIterator iterator = source.getIterator();
    
    assertTrue(iterator.hasNext());
    JavaSourceRun run = iterator.getNext();
//run.dump();
    assertEquals("", run.getCode()); //$NON-NLS-1$
    assertTrue(run.isAtStartOfLine());
    assertTrue(run.isAtEndOfLine());
    assertEquals(JavaSourceType.BACKGROUND, run.getType());

    assertTrue(iterator.hasNext());
    run = iterator.getNext();
//run.dump();
    assertEquals("a", run.getCode()); //$NON-NLS-1$
    assertTrue(run.isAtStartOfLine());
    assertTrue(run.isAtEndOfLine());
    assertEquals(JavaSourceType.CODE, run.getType());
    assertFalse(iterator.hasNext());
  }

  public void testSingle() throws IOException{
    JavaSource source = JavaSourceParserTestCase.doParse("a"); //$NON-NLS-1$
    JavaSourceIterator iterator = source.getIterator();
    
    assertTrue(iterator.hasNext());
    JavaSourceRun run = iterator.getNext();
  
    assertEquals("a", run.getCode()); //$NON-NLS-1$
    assertTrue(run.isAtStartOfLine());
    assertTrue(run.isAtEndOfLine());
    assertEquals(JavaSourceType.CODE, run.getType());

    assertFalse(iterator.hasNext());
    try{
      iterator.next();      
      fail(); 
    }catch(NoSuchElementException expected){
      //expected
    }
  }

//  public void testSingleLineBreak() throws IOException{
//    JavaSource source = JavaSourceParserTest.doParse("a\n");
//    assertEquals("a\r\n", source.getCode());
//    
//    JavaSourceIterator iterator = source.getIterator();
//    
//    assertTrue(iterator.hasNext());
//    JavaSourceRun run = iterator.getNext();
//run.dump();    
//    assertEquals("a", run.getCode());
//    assertTrue(run.isAtStartOfLine());
//    assertTrue(run.isAtEndOfLine());
//    assertEquals(JavaSourceType.CODE, run.getType());
//    
//    assertTrue(iterator.hasNext());
//    run = iterator.getNext();
//    run.dump();
//    
//    assertEquals("", run.getCode());
//    assertTrue(run.isAtStartOfLine());
//    assertTrue(run.isAtEndOfLine());
//    assertEquals(JavaSourceType.CODE, run.getType());
//
//    assertTrue(iterator.hasNext());
//    run = iterator.getNext();
//    run.dump();
//    
//    assertEquals("", run.getCode());
//    assertTrue(run.isAtStartOfLine());
//    assertTrue(run.isAtEndOfLine());
//    assertEquals(JavaSourceType.CODE, run.getType());
//
//    assertFalse(iterator.hasNext());
//  }


  public void testTwoLines() throws IOException{
    JavaSource source = JavaSourceParserTestCase.doParse("a\nb"); //$NON-NLS-1$
    JavaSourceIterator iterator = source.getIterator();

    JavaSourceRun run = iterator.getNext();

//run.dump();
    assertEquals("a", run.getCode()); //$NON-NLS-1$
    assertTrue(run.isAtStartOfLine());
    assertTrue(run.isAtEndOfLine());
    assertEquals(JavaSourceType.CODE, run.getType());
    
    assertTrue(iterator.hasNext());
    run = iterator.getNext();

//run.dump();
    assertEquals("b", run.getCode()); //$NON-NLS-1$
    assertTrue(run.isAtStartOfLine());
    assertTrue(run.isAtEndOfLine());
    assertEquals(JavaSourceType.CODE, run.getType());

    assertFalse(iterator.hasNext());
  }



  public void test1() throws IOException{
    JavaSource source = JavaSourceParserTestCase.doParse("public String text =\"test\";"); //$NON-NLS-1$

    JavaSourceIterator iterator = source.getIterator();
    
    assertTrue(iterator.hasNext());
    JavaSourceRun run = iterator.getNext();
    
// run.dump();
    assertEquals("public ", run.getCode()); //$NON-NLS-1$
    assertTrue(run.isAtStartOfLine());
    assertFalse(run.isAtEndOfLine());
    assertEquals(JavaSourceType.KEYWORD, run.getType());

    assertTrue(iterator.hasNext());
    run = iterator.getNext();
//    run.dump();
    assertEquals("String text =", run.getCode()); //$NON-NLS-1$
    assertFalse(run.isAtStartOfLine());
    assertFalse(run.isAtEndOfLine());
    assertEquals(JavaSourceType.CODE, run.getType());

    assertTrue(iterator.hasNext());
    run = iterator.getNext();

//    run.dump();
    assertEquals("\"test\"", run.getCode()); //$NON-NLS-1$
    assertFalse(run.isAtStartOfLine());
    assertFalse(run.isAtEndOfLine());
    assertEquals(JavaSourceType.STRING, run.getType());

    assertTrue(iterator.hasNext());
    run = iterator.getNext();

// run.dump();
    assertEquals(";", run.getCode()); //$NON-NLS-1$
    assertFalse(run.isAtStartOfLine());
    assertTrue(run.isAtEndOfLine());
    assertEquals(JavaSourceType.CODE, run.getType());


    assertFalse(iterator.hasNext());
  }
  
  public void testEmptyLinesAreEmptyRuns() throws IOException{
    JavaSource source = JavaSourceParserTestCase.doParse("public\n\ntest"); //$NON-NLS-1$
    JavaSourceIterator iterator = source.getIterator();
    
    JavaSourceRun run = iterator.getNext();
//run.dump();
    assertEquals("public", run.getCode()); //$NON-NLS-1$
    
    run = iterator.getNext();
//run.dump();
    assertEquals("", run.getCode()); //$NON-NLS-1$

    run = iterator.getNext();
    assertEquals("test", run.getCode()); //$NON-NLS-1$
   
    assertFalse(iterator.hasNext());
  }
}