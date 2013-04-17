package de.jdemo.swingui.launch;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import de.jdemo.framework.DemoIdentifier;
import de.jdemo.framework.util.DemoUtilities;
import de.jdemo.runner.path.ISourcePath;
import de.jdemo.runner.path.SourcePathFactory;

/**
 * @author Markus Gebhard
 */
public class DemoRunnerCommandLineParser {

  public CommandLineOptions parse(String[] args) throws CommandLineParseException {
    CommandLineOptions options = new CommandLineOptions();
    if (isShowHelp(args)) {
      options.setShowHelp(true);
    }
    else if (isShowVersion(args)) {
      options.setShowVersion(true);
    }
    else {
      List argumentList = new LinkedList(Arrays.asList(args));
      ISourcePath sourcePath = extractSourcePath(argumentList);
      options.setSourcePath(sourcePath);
      options.setLaunchStrategy(createLaunchStrategy(argumentList));
      if (argumentList.size() > 0) {
        throw new CommandLineParseException("Unrecognized option: " + argumentList.get(0));
      }
    }

    return options;
  }

  private IDemoRunnerLaunchStrategy createLaunchStrategy(List argumentList) {
    if (argumentList.size() == 0) {
      return new EmptyDemoRunnerLaunchStrategy();
    }
    if (DemoUtilities.isDemoIdentifier((String) argumentList.get(0))) {
      DemoIdentifier demoIdentifier = new DemoIdentifier((String) argumentList.get(0));
      argumentList.remove(0);
      return new DemoIdentifierLaunchStrategy(demoIdentifier);
    }

    String className = (String) argumentList.get(0);
    argumentList.remove(0);
    return new DemoClassLaunchStrategy(className);
  }

  private ISourcePath extractSourcePath(List argumentList) throws CommandLineParseException {
    for (int i = 0; i < argumentList.size(); ++i) {
      String argument = (String) argumentList.get(i);
      if ("-sp".equalsIgnoreCase(argument) || "-sourcepath".equalsIgnoreCase(argument)) {
        if (i == argumentList.size() - 1) {
          throw new CommandLineParseException("Missing sourcepath argument");
        }
        String pathString = (String) argumentList.get(i + 1);
        argumentList.remove(i + 1);
        argumentList.remove(i);
        return new SourcePathFactory().createSourcePath(pathString);
      }
    }
    return null;
  }

  private static boolean isShowHelp(String[] args) {
    if (args == null || args.length != 1) {
      return false;
    }
    return "-help".equalsIgnoreCase(args[0]) || "-?".equalsIgnoreCase(args[0]);
  }

  private static boolean isShowVersion(String[] args) {
    if (args == null || args.length != 1) {
      return false;
    }
    return "-version".equalsIgnoreCase(args[0]);
  }
}