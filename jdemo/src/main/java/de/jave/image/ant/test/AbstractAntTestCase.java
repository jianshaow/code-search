package de.jave.image.ant.test;

import org.apache.tools.ant.BuildException;

import de.jave.image.ant.IAntParameterCheckable;
import junit.framework.TestCase;

/**
 * @author Markus Gebhard
 */
public abstract class AbstractAntTestCase extends TestCase {
  protected void assertCheckParametersFails(IAntParameterCheckable checkable) {
    try {
      checkable.checkParameters();
      fail();
    }
    catch (BuildException expected) {
      //expected
    }
  }

}