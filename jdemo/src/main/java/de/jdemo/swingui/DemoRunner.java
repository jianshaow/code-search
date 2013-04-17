package de.jdemo.swingui;

import de.jdemo.Version;
import de.jdemo.framework.IDemo;
import de.jdemo.runner.path.SourcePathFactory;
import de.jdemo.swingui.launch.CommandLineOptions;
import de.jdemo.swingui.launch.CommandLineParseException;
import de.jdemo.swingui.launch.DemoClassLaunchStrategy;
import de.jdemo.swingui.launch.DemoRunnerCommandLineParser;
import de.jdemo.swingui.launch.LaunchException;

/**
 * @author Markus Gebhard
 */
public class DemoRunner {
  private DemoRunner() {
    //not instantiable - just a launcher
  }

  public static void main(String[] args) {
    try {
      CommandLineOptions options = new DemoRunnerCommandLineParser().parse(args);
      run(options);
    }
    catch (CommandLineParseException e) {
      System.err.println(e.getMessage());
      printUsage();
      System.exit(1);
    }
  }

  public static void run(CommandLineOptions options) {
    if (options.isShowVersion()) {
      printVersion();
      return;
    }
    if (options.isShowHelp()) {
      printUsage();
      return;
    }

    try {
      DemoRunnerApplication runnerApplication = options.getLaunchStrategy().createDemoRunnerApplication();
      runnerApplication.setSourcePath(options.getSourcePath());
      runnerApplication.show();
    }
    catch (LaunchException e) {
      e.printStackTrace();
      printUsage();
      System.exit(1);
    }
  }

  public static void run(Class theClass) {
    try {
      DemoRunnerApplication runner = new DemoClassLaunchStrategy(theClass.getName())
          .createDemoRunnerApplication();
      runner.show();
      return;
    }
    catch (LaunchException e) {
      e.printStackTrace();
    }
  }

  public static void run(IDemo demo) {
    DemoRunnerApplication runner = new DemoRunnerApplication(demo);
    runner.show();
  }

  private static void printVersion() {
    System.out.println("JDemo version " + Version.getFullVersionNumber() + " " + Version.getBuildDate());
  }

  private static void printUsage() {
    System.out.println("Usage: DemoRunner [-options] demo_class_name");
    System.out.println("        (to execute a demo case or demo suite by the given class name)");
    System.out.println("   or  DemoRunner [-options] demo_identifier");
    System.out.println("        (to execute a demo case by the given demo identifier name)");
    //    System.out.println("   or  DemoRunner [-options] -jdemo demo_xml_file");
    //    System.out.println("        (to execute the specified demo xml file)");
    //    System.out.println("   or  DemoRunner [-options]");
    //    System.out.println("        (to start the runner without any demos)");
    System.out.println();
    System.out.println("where options include:");
    System.out.println("    -sp <source code search path of directories and zip/jar files>");
    System.out.println("    -sourcepath <source code search path of directories and zip/jar files>");
    System.out.println("                    A "
        + SourcePathFactory.getPathSeparator()
        + " separated list of directories, JAR archives,");
    System.out.println("                    and ZIP archives to search for class files.");
    System.out.println("    -version        print version information and exit.");
    System.out.println("    -? -help        print this help message.");
    System.out.println();
    System.out.println("Examples:");
    System.out.println("   DemoRunner de.jdemo.examples.AllDemos");
    System.out.println("   DemoRunner de.jdemo.examples.HelloWorldDemo:demoHelloWorld");
    //    System.out.println("   DemoRunner -jdemo mydemos.jdemo");
  }
}