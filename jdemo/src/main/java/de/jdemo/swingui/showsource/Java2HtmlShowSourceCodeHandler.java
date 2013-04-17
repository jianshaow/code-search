package de.jdemo.swingui.showsource;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import de.jdemo.framework.DemoIdentifier;
import de.jdemo.runner.DemoSourceCodeNotFoundException;
import de.jdemo.runner.IDemoShowSourceCodeHandler;
import de.jdemo.runner.path.EmptySourcePath;
import de.jdemo.runner.path.ISourcePath;
import de.jdemo.swingui.util.JDemoEnsure;
import de.jdemo.util.GuiUtilities;

/**
 * @author Markus Gebhard
 */
public class Java2HtmlShowSourceCodeHandler implements IDemoShowSourceCodeHandler {

  private final Component parentComponent;
  private final IJava2HtmlFacadeMethod facadeMethod;

  private ISourcePath sourcePath = new EmptySourcePath();

  private Java2HtmlShowSourceCodeHandler(
      final Component parentComponent,
      final IJava2HtmlFacadeMethod facadeMethod) {
    JDemoEnsure.ensureArgumentNotNull(facadeMethod);
    this.parentComponent = parentComponent;
    this.facadeMethod = facadeMethod;
  }

  public void showDemoSourceCode(final DemoIdentifier id) throws DemoSourceCodeNotFoundException {
    final JDialog dialog = createSourceCodeDialog(id);
    dialog.setVisible(true);
  }

  /** For internal use only */
  public JDialog createSourceCodeDialog(final DemoIdentifier id) throws DemoSourceCodeNotFoundException {
    final String sourceCode = loadSourceCode(id);
    if (sourceCode == null) {
      throw new DemoSourceCodeNotFoundException();
    }
    return createSourceCodeDialog(sourceCode, id);
  }

  public JDialog createSourceCodeDialog(final String sourceCode, final DemoIdentifier id) {
    final JTextPane editorPane = facadeMethod.execute(sourceCode, id.getMethodName());

    final JScrollPane scrollPane = new JScrollPane(editorPane);
    scrollPane.setPreferredSize(new Dimension(580, 400));
    final JDialog dialog = GuiUtilities.createDialog(parentComponent);
    dialog.setTitle("Source code from " + id.getClassName());
    dialog.getContentPane().setLayout(new BorderLayout());
    dialog.getContentPane().add(scrollPane, BorderLayout.CENTER);
    dialog.pack();
    return dialog;
  }

  public String loadSourceCode(final DemoIdentifier id) {
    try {
      final String sourceCode = sourcePath.load(id.getClassName());
      return sourceCode;
    }
    catch (final IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }

  public void setSourcePath(final ISourcePath sourcePath) {
    this.sourcePath = sourcePath;
  }

  public static Java2HtmlShowSourceCodeHandler createIfAvailable(final Component parentComponent) {
    final String typeName = "de.java2html.facade.Java2HtmlSwingFacade"; //$NON-NLS-1$
    final String staticMethodName = "createTextPaneWithHighlightedMethod"; //$NON-NLS-1$
    final Class<?>[] parameterTypes = new Class<?>[]{ String.class, String.class };
    try {
      final Class<?> facadeClass = Class.forName(typeName);
      final Method method = facadeClass.getDeclaredMethod(staticMethodName, parameterTypes);
      final IJava2HtmlFacadeMethod facadeMethodBlock = new IJava2HtmlFacadeMethod() {

        @Override
        public JTextPane execute(final String sourceCode, final String methodName) {
          try {
            final Object result = method.invoke(null, sourceCode, methodName);
            return (JTextPane) result;
          }
          catch (final IllegalArgumentException e) {
            throw new RuntimeException("Java2Html facade not compatible", e); //$NON-NLS-1$
          }
          catch (final IllegalAccessException e) {
            throw new RuntimeException("Java2Html facade not compatible", e); //$NON-NLS-1$
          }
          catch (final InvocationTargetException e) {
            throw new RuntimeException("Java2Html facade not compatible", e); //$NON-NLS-1$
          }
        }
      };
      return new Java2HtmlShowSourceCodeHandler(parentComponent, facadeMethodBlock);
    }
    catch (final ClassNotFoundException e) {
      return null;
    }
    catch (final SecurityException e) {
      throw new RuntimeException(e);
    }
    catch (final NoSuchMethodException e) {
      throw new RuntimeException("Java2Html facade not compatible", e); //$NON-NLS-1$
    }
  }

  private static interface IJava2HtmlFacadeMethod {

    JTextPane execute(String sourceCode, String methodName);
  }
}