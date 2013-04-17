package de.jdemo.swingui.launch.test;

import junit.framework.TestCase;

import de.jdemo.swingui.launch.CommandLineOptions;
import de.jdemo.swingui.launch.CommandLineParseException;
import de.jdemo.swingui.launch.DemoClassLaunchStrategy;
import de.jdemo.swingui.launch.DemoIdentifierLaunchStrategy;
import de.jdemo.swingui.launch.DemoRunnerCommandLineParser;
import de.jdemo.swingui.launch.EmptyDemoRunnerLaunchStrategy;

/**
 * @author Markus Gebhard
 */
public class DemoRunnerCommandLineParserTest extends TestCase {

  private DemoRunnerCommandLineParser parser;

  protected void setUp() throws Exception {
    parser = new DemoRunnerCommandLineParser();
  }

  public void testNewCommandLineOptions() {
    CommandLineOptions options = new CommandLineOptions();
    assertFalse(options.isShowVersion());
    assertFalse(options.isShowHelp());
    assertEquals(0, options.getSourcePath().getPathElementCount());
    assertNull(options.getLaunchStrategy());
  }

  public void testParseEmpty() throws CommandLineParseException {
    CommandLineOptions options = parser.parse(new String[0]);
    assertFalse(options.isShowVersion());
    assertFalse(options.isShowHelp());
    assertNull(options.getSourcePath());
    assertTrue(options.getLaunchStrategy() instanceof EmptyDemoRunnerLaunchStrategy);
  }

  public void testParseVersion() throws CommandLineParseException {
    CommandLineOptions options = parser.parse(new String[]{ "-version" }); //$NON-NLS-1$
    assertTrue(options.isShowVersion());
  }

  public void testShowHelp() throws CommandLineParseException {
    CommandLineOptions options = parser.parse(new String[]{ "-help" }); //$NON-NLS-1$
    assertTrue(options.isShowHelp());
  }

  public void testSourcePath1() throws CommandLineParseException {
    CommandLineOptions options = parser.parse(new String[]{ "-sp", "test" }); //$NON-NLS-1$ //$NON-NLS-2$
    assertEquals(1, options.getSourcePath().getPathElementCount());
    assertEquals("test", options.getSourcePath().getPathElement(0).toString()); //$NON-NLS-1$
  }

  public void testSourcePath2() throws CommandLineParseException {
    CommandLineOptions options = parser.parse(new String[]{ "-sourcepath", "test" }); //$NON-NLS-1$ //$NON-NLS-2$
    assertEquals(1, options.getSourcePath().getPathElementCount());
    assertEquals("test", options.getSourcePath().getPathElement(0).toString()); //$NON-NLS-1$
  }

  public void testSourcePathWithoutPath() {
    try {
      parser.parse(new String[]{ "-sourcepath" }); //$NON-NLS-1$
      fail();
    }
    catch (CommandLineParseException expected) {
      //expected
    }
  }

  public void testDemoIdentifierLaunchStrategy() throws CommandLineParseException {
    CommandLineOptions options = parser.parse(new String[]{ "de.jdemo.examples.HelloWorldDemo:demoHelloWorld" }); //$NON-NLS-1$
    assertTrue(options.getLaunchStrategy() instanceof DemoIdentifierLaunchStrategy);
  }

  public void testDemoClassLaunchStrategy() throws CommandLineParseException {
    CommandLineOptions options = parser.parse(new String[]{ "de.jdemo.examples.HelloWorldDemo" }); //$NON-NLS-1$
    assertTrue(options.getLaunchStrategy() instanceof DemoClassLaunchStrategy);
  }
}