package de.java2html.javasource.test;

import de.java2html.javasource.JavaSourceType;
import junit.framework.TestCase;

public class JavaSourceTypeTest extends TestCase {
  public void testArrayHasSameOrderAsIDs(){
    JavaSourceType[] types = JavaSourceType.getAll();
    for (int i=0;i<types.length;++i){
      assertEquals(i, types[i].getID());
    }
  }
}
