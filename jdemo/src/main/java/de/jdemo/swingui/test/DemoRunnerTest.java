package de.jdemo.swingui.test;

import java.io.PrintStream;
import java.io.StringWriter;

import junit.framework.TestCase;

import de.jdemo.capture.stream.SystemStreamType;
import de.jdemo.capture.stream.WriterOutputStream;
import de.jdemo.swingui.DemoRunner;
import de.jdemo.swingui.launch.CommandLineOptions;

/**
 * @author Markus Gebhard
 * @deprecated */
public class DemoRunnerTest extends TestCase {
  private StringWriter outWriter;
  private StringWriter errWriter;
  private CommandLineOptions commandLineOptions;

  /** @deprecated */
  protected void setUp() throws Exception {
    outWriter = new StringWriter();
    SystemStreamType.OUT.setPrintStream(new PrintStream(new WriterOutputStream(outWriter)));
    errWriter = new StringWriter();
    SystemStreamType.ERR.setPrintStream(new PrintStream(new WriterOutputStream(errWriter)));
    commandLineOptions = new CommandLineOptions();
  }

  /** @deprecated */
  private String getSystemOutString() {
    SystemStreamType.OUT.resetPrintStream();
    return outWriter.toString();
  }

  /** @deprecated */
  private String getSystemErrString() {
    SystemStreamType.ERR.resetPrintStream();
    return errWriter.toString();
  }

  public void testPrintsVersion() {
    commandLineOptions.setShowVersion(true);
    DemoRunner.run(commandLineOptions);
    assertEquals("", getSystemErrString()); //$NON-NLS-1$
    assertTrue(getSystemOutString().startsWith("JDemo version")); //$NON-NLS-1$
  }

  public void testPrintsUsage() {
    commandLineOptions.setShowHelp(true);
    DemoRunner.run(commandLineOptions);
    assertEquals("", getSystemErrString()); //$NON-NLS-1$
    assertTrue(getSystemOutString().startsWith("Usage:")); //$NON-NLS-1$
  }
}