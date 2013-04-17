package de.jdemo.swingui.list;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import de.jdemo.framework.IDemoCaseRunnable;
import de.jdemo.framework.state.DemoState;
import de.jdemo.framework.util.DemoUtilities;
import de.jdemo.swingui.icons.AggregatedIcon;
import de.jdemo.swingui.icons.JDemoIcons;
import de.jdemo.swingui.icons.SwingDemoStateDecoration;
import de.jdemo.swingui.icons.SwingDemoTypeDecoration;

/**
 * @author Markus Gebhard
 */
public class DemoExecutionListCellRenderer extends DefaultListCellRenderer {

  @Override
  public Component getListCellRendererComponent(
      final JList list,
      final Object value,
      final int index,
      final boolean isSelected,
      final boolean cellHasFocus) {

    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

    final IDemoCaseRunnable runner = (IDemoCaseRunnable) value;
    setText(DemoUtilities.getDisplayName(runner.getDemo()));

    final DemoState state = runner.getState();

    final AggregatedIcon icon = new AggregatedIcon(JDemoIcons.DEMO_CASE);
    icon.addDecorationIcon(SwingDemoTypeDecoration.getFor(runner.getDemo()).getIcon());
    final SwingDemoStateDecoration stateDecoration = SwingDemoStateDecoration.getFor(state);
    icon.addDecorationIcon(stateDecoration.getIcon());
    setIcon(icon);

    if (state.equals(DemoState.CRASHED)) {
      final Throwable error = runner.getThrowable();
      setText(getText() + " - " + error); //$NON-NLS-1$
    }
    setToolTipText(createToolTipText(runner));

    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    }
    else {
      setForeground(stateDecoration.getColor());
    }
    return this;
  }

  private String createToolTipText(final IDemoCaseRunnable runner) {
    final StringBuffer sb = new StringBuffer();
    sb.append("<html>"); //$NON-NLS-1$

    sb.append(runner.getDemo().getIdentifier().toString());
    if (runner.getState().equals(DemoState.CRASHED)) {
      sb.append("<br>"); //$NON-NLS-1$
      sb.append(createErrorToolTipTextPart(runner.getThrowable(), "&nbsp;&nbsp;")); //$NON-NLS-1$
    }
    sb.append("</html>"); //$NON-NLS-1$
    return sb.toString();
  }

  private String createErrorToolTipTextPart(final Throwable error, final String linePrefix) {
    final StringBuffer sb = new StringBuffer();
    sb.append(linePrefix);
    sb.append(error);
    sb.append("<br>"); //$NON-NLS-1$
    final StackTraceElement[] elements = error.getStackTrace();
    for (int i = 0; i < elements.length; ++i) {
      sb.append(linePrefix);
      sb.append("&nbsp;&nbsp;&nbsp;"); //$NON-NLS-1$
      sb.append(elements[i].toString());
      sb.append("<br>"); //$NON-NLS-1$
    }
    return sb.toString();
  }
}