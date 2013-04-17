package de.java2html.commandline.test;

import de.java2html.commandline.CommandlineArguments;
import de.java2html.commandline.IllegalCommandlineParametersException;
import de.java2html.test.BasicTestCase;

/**
 * @author Markus Gebhard
 */
public class CommandlineArgumentsTest extends BasicTestCase {

  public void testIsFlagSet() throws IllegalCommandlineParametersException {
    CommandlineArguments emptyArguments = new CommandlineArguments(new String[]{});
    assertFalse(emptyArguments.isFlagSet("flag"));

    CommandlineArguments arguments = new CommandlineArguments(new String[]{
        "-parameter",
        "value",
        "-flag" });
    assertTrue(arguments.isFlagSet("flag"));
    assertTrue(arguments.isFlagSet("FLAG"));
    assertFalse(arguments.isFlagSet("-flag"));
    assertFalse(arguments.isFlagSet("value"));
    assertFalse(arguments.isFlagSet("parameter"));
  }

  public void testIsParameterSet() throws IllegalCommandlineParametersException {
    CommandlineArguments emptyArguments = new CommandlineArguments(new String[]{});
    assertFalse(emptyArguments.isParameterSet("flag"));

    CommandlineArguments arguments = new CommandlineArguments(new String[]{
        "-parameter",
        "value",
        "-flag" });
    assertFalse(arguments.isParameterSet("flag"));
    assertFalse(arguments.isParameterSet("FLAG"));
    assertFalse(arguments.isParameterSet("-flag"));
    assertFalse(arguments.isParameterSet("value"));
    assertTrue(arguments.isParameterSet("parameter"));
    assertTrue(arguments.isParameterSet("pArAmEtEr"));
  }

  public void testNumbersAreNotFlags() throws IllegalCommandlineParametersException {
    CommandlineArguments arguments = new CommandlineArguments(new String[]{ "-flag", "-10" });
    assertFalse(arguments.isFlagSet("10"));
    assertFalse(arguments.isFlagSet("flag"));
    assertTrue(arguments.isParameterSet("flag"));
  }

  public void testDuplicateFlagDefinitionThrowsException() {
    assertThrowsException(IllegalCommandlineParametersException.class, new IBlock() {
      public void execute() throws Exception {
        new CommandlineArguments(new String[]{ "-flag1", "-flag1" });
      }
    });
  }

  public void testDuplicateParameterDefinitionThrowsException() {
    assertThrowsException(IllegalCommandlineParametersException.class, new IBlock() {
      public void execute() throws Exception {
        new CommandlineArguments(new String[]{ "-flag1", "value1", "-flag1", "value1" });
      }
    });
  }

  public void testDuplicateMixedDefinitionThrowsException() {
    assertThrowsException(IllegalCommandlineParametersException.class, new IBlock() {
      public void execute() throws Exception {
        new CommandlineArguments(new String[]{ "-flag1", "-flag1", "value" });
      }
    });
  }

  public void testGetArgumentStringValue() throws IllegalCommandlineParametersException {
    assertEquals("value", new CommandlineArguments(new String[]{ "-flag1", "value" })
        .getParameterStringValue("flag1"));
    assertEquals(null, new CommandlineArguments(new String[]{ "-flag1", "-flag2" })
        .getParameterStringValue("flag1"));
  }

  public void testGetRequiredArgumentStringValue() throws IllegalCommandlineParametersException {
    assertEquals("value", new CommandlineArguments(new String[]{ "-flag1", "value" })
        .getRequiredParameterStringValue(("flag1")));
    assertThrowsException(IllegalCommandlineParametersException.class, new IBlock() {
      public void execute() throws Exception {
        new CommandlineArguments(new String[]{ "-flag1" }).getRequiredParameterStringValue("flag1");
      }
    });
  }

  public void testGetArgumentStringValueWithDefault() throws IllegalCommandlineParametersException {
    assertEquals("default", new CommandlineArguments(new String[]{ "-flag1", "value" })
        .getParameterStringValue("undefinedFlag", "default"));
    assertEquals("value", new CommandlineArguments(new String[]{ "-definedflag", "value" })
        .getParameterStringValue("definedflag", "default"));
  }

  public void testGetArgumentIntValueWithDefault() throws IllegalCommandlineParametersException {
    assertEquals(42, new CommandlineArguments(new String[]{ "-flag1", "42" })
        .getParameterPositiveIntValue("flag1", 1));
    assertEquals(1, new CommandlineArguments(new String[]{}).getParameterPositiveIntValue(
        "flag1",
        1));
  }

  public void testGetArgumentIntValueWithIllegalValue() {
    assertThrowsException(IllegalCommandlineParametersException.class, new IBlock() {
      public void execute() throws Exception {
        new CommandlineArguments(new String[]{ "-flag1", "abc" }).getParameterPositiveIntValue(
            "flag1",
            1);
      }
    });
    assertThrowsException(IllegalCommandlineParametersException.class, new IBlock() {
      public void execute() throws Exception {
        new CommandlineArguments(new String[]{ "-flag1", "-10" }).getParameterPositiveIntValue(
            "flag1",
            1);
      }
    });
  }

  public void testUnsupportedParameterThrowsException()
      throws IllegalCommandlineParametersException {
    new CommandlineArguments(new String[]{ "-flag1", "value1", "-flag2" })
        .assertContainsNoUnsupportedParameters(new String[]{ "flag1" });
    new CommandlineArguments(new String[]{ "-flag1", "value1", "-flag2" })
        .assertContainsNoUnsupportedParameters(new String[]{ "FLag1" });

    assertThrowsException(IllegalCommandlineParametersException.class, new IBlock() {
      public void execute() throws Exception {
        new CommandlineArguments(new String[]{ "-flag1", "value1", "-flag2", "value2" })
            .assertContainsNoUnsupportedParameters(new String[]{ "flag1" });
      }
    });

    assertThrowsException(IllegalCommandlineParametersException.class, new IBlock() {
      public void execute() throws Exception {
        new CommandlineArguments(new String[]{ "-flag1", "value1", "-flag2" })
            .assertContainsNoUnsupportedParameters(new String[]{ "flag2" });
      }
    });

    assertThrowsException(IllegalCommandlineParametersException.class, new IBlock() {
      public void execute() throws Exception {
        new CommandlineArguments(new String[]{ "-flag", "value" })
            .assertContainsNoUnsupportedParameters(new String[0]);
      }
    });
  }

  public void testUnsupportedFlagThrowsException() throws IllegalCommandlineParametersException {
    new CommandlineArguments(new String[]{ "-flag1", "value1", "-flag2" })
        .assertContainsNoUnsupportedFlags(new String[]{ "flag1", "flag2" });
    new CommandlineArguments(new String[]{ "-flag1", "value1", "-flag2" })
        .assertContainsNoUnsupportedFlags(new String[]{ "FLag1", "FLag2" });

    assertThrowsException(IllegalCommandlineParametersException.class, new IBlock() {
      public void execute() throws Exception {
        new CommandlineArguments(new String[]{ "-flag1", "-flag2" })
            .assertContainsNoUnsupportedFlags(new String[]{ "flag1" });
      }
    });

    assertThrowsException(IllegalCommandlineParametersException.class, new IBlock() {
      public void execute() throws Exception {
        new CommandlineArguments(new String[]{ "-flag1", "-flag2" })
            .assertContainsNoUnsupportedFlags(new String[]{ "flag2" });
      }
    });

    assertThrowsException(IllegalCommandlineParametersException.class, new IBlock() {
      public void execute() throws Exception {
        new CommandlineArguments(new String[]{ "-flag" })
            .assertContainsNoUnsupportedFlags(new String[0]);
      }
    });
  }
}