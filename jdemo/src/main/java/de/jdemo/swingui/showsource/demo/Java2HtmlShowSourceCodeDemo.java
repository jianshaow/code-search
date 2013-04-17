package de.jdemo.swingui.showsource.demo;

import java.awt.SystemColor;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import de.jdemo.extensions.SwingDemoCase;
import de.jdemo.framework.DemoIdentifier;
import de.jdemo.runner.path.ISourcePath;
import de.jdemo.runner.path.SourcePathFactory;
import de.jdemo.swingui.showsource.Java2HtmlShowSourceCodeHandler;
import de.jdemo.swingui.suite.testdemos.AwtDemoCaseUsageDemo;
import de.jdemo.swingui.suite.testdemos.HelloWorldDemo;

/**
 * @author Markus Gebhard
 */
public class Java2HtmlShowSourceCodeDemo extends SwingDemoCase {

  private Java2HtmlShowSourceCodeHandler showSourceCodeHandler;

  @Override
  protected void setUp() throws Exception {
    final ISourcePath sourcePath = new SourcePathFactory().createSourcePath("./src/"); //$NON-NLS-1$
    showSourceCodeHandler = Java2HtmlShowSourceCodeHandler.createIfAvailable(createParentComponent());
    showSourceCodeHandler.setSourcePath(sourcePath);
  }

  public void demoHelloWorld() {
    final DemoIdentifier id = new DemoIdentifier(HelloWorldDemo.class.getName(), "demoHelloWorld"); //$NON-NLS-1$
    final String source = showSourceCodeHandler.loadSourceCode(id);
    if (source == null) {
      fail("Unable to find source code on the source path"); //$NON-NLS-1$
    }
    show(showSourceCodeHandler.createSourceCodeDialog(source, id));
  }

  public void demoAwtDemoCaseUsage() {
    final DemoIdentifier id = new DemoIdentifier(AwtDemoCaseUsageDemo.class.getName(), "demoWindowWithParent"); //$NON-NLS-1$
    final String source = showSourceCodeHandler.loadSourceCode(id);
    if (source == null) {
      fail("Unable to find source code on the source path"); //$NON-NLS-1$
    }
    show(showSourceCodeHandler.createSourceCodeDialog(source, id));
  }

  public void demoDialogFromParsedSourceCode() {
    final String sourceCode = "public void demoHelloWorld() {\n" + "  System.out.println(\"Hello World\");\n" + "}"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    show(showSourceCodeHandler.createSourceCodeDialog(sourceCode, new DemoIdentifier("HelloWorldDemo", //$NON-NLS-1$
        "demoHelloWorld"))); //$NON-NLS-1$
  }

  public void demoJTextPaneWithHtmlCode() {
    final String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" //$NON-NLS-1$
        + " <html><head> <title></title>" //$NON-NLS-1$
        + "   <style type=\"text/css\">  " //$NON-NLS-1$
        + "   <!--code { font-family: Courier New, Courier; font-size: 11pt; margin: 0px; }--> " //$NON-NLS-1$
        + "  </style> " //$NON-NLS-1$
        + "</head>" //$NON-NLS-1$
        + "<body>" //$NON-NLS-1$
        + "   <!-- ======================================================== -->" //$NON-NLS-1$
        + " <!-- = Java Sourcecode to HTML automatically converted code = -->" //$NON-NLS-1$
        + " <!-- =   Java2Html Converter V4.1 2004 by Markus Gebhard  markus@jave.de   = -->" //$NON-NLS-1$
        + " <!-- =     Further information: http://www.java2html.de     = -->" //$NON-NLS-1$
        + " <div align=\"left\" class=\"java\">" //$NON-NLS-1$
        + " <table border=\"0\" cellpadding=\"3\" cellspacing=\"0\" bgcolor=\"#f0f0f0\"> " //$NON-NLS-1$
        + "   <tr>" //$NON-NLS-1$
        + "    <!-- start source code -->" //$NON-NLS-1$
        + "    <td nowrap=\"nowrap\" valign=\"top\" align=\"left\"> " //$NON-NLS-1$
        + "    <code> <font color=\"#7f0055\"><b>public&nbsp;</b></font><font color=\"#7f0055\"><b>void&nbsp;</b></font><font color=\"#000000\">demoHelloWorld</font><font color=\"#000000\">()&nbsp;{</font><br>" //$NON-NLS-1$
        + "  <font color=\"#f0f0f0\">&nbsp;&nbsp;</font><font color=\"#000000\">System.out.println</font><font color=\"#000000\">(</font><font color=\"#2a00ff\">&#34;Hello&nbsp;World&#34;</font><font color=\"#000000\">)</font><font color=\"#000000\">;</font><br>  <font color=\"#000000\">}</font></code>   " //$NON-NLS-1$
        + "      </td> " //$NON-NLS-1$
        + "  <!-- end source code --> " //$NON-NLS-1$
        + "   </tr> " //$NON-NLS-1$
        + " </table>" //$NON-NLS-1$
        + " </div>" //$NON-NLS-1$
        + " <!-- =       END of automatically generated HTML code       = -->" //$NON-NLS-1$
        + " <!-- ======================================================== -->   " //$NON-NLS-1$
        + "</body></html>"; //$NON-NLS-1$
    final JTextPane editorPane = new JTextPane();
    editorPane.setBackground(SystemColor.text);
    editorPane.setEditable(false);
    editorPane.setContentType("text/html"); //$NON-NLS-1$
    editorPane.setText(html);
    show(new JScrollPane(editorPane));
  }
}