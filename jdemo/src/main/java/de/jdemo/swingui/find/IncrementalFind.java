package de.jdemo.swingui.find;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Generic incremental find feature similar to the one in eclipse (or emacs).
 * <ul>
 *   <li>Hitting Ctrl-J enters Incremental search mode
 *   <li>Characters, numbers, backspace edit the search string
 *   <li>Cursor down or Ctrl-J searches next occurance
 *   <li>Cursor up searches previous occurance (not yet supported)
 *   <li>Hitting other characters exits search mode 
 * </ul>
 * @author Markus Gebhard
 */
public class IncrementalFind {
  public static IncrementalFind attachTo(Component keyListeningComponent, IIncrementalSearchable searchable) {
    return new IncrementalFind(searchable, keyListeningComponent);
  }

  private boolean failed;
  private IncrementalSearchDirection searchDirection = IncrementalSearchDirection.DOWN;
  private IIncrementalSearchable searchable;
  private boolean searching;
  private String searchString;
  private String previousSearchString;

  private IncrementalFind(IIncrementalSearchable searchable, Component keyListeningComponent) {
    this.searchable = searchable;
    searching = false;
    searchString = ""; //$NON-NLS-1$
    keyListeningComponent.addKeyListener(createKeyListener());
  }

  private KeyListener createKeyListener() {
    return new KeyListener() {
      public void keyTyped(KeyEvent e) {
        //nothing to do
      }

      public void keyPressed(KeyEvent event) {
        if (event.isControlDown() && event.getKeyCode() == KeyEvent.VK_J) {
          controlJPressed();
          event.consume();
          return;
        }
        if (!searching) {
          return;
        }

        if (event.getKeyCode() == KeyEvent.VK_CONTROL
            || event.getKeyCode() == KeyEvent.VK_SHIFT
            || event.getKeyCode() == KeyEvent.VK_ALT) {
          return;
        }

        if (event.getKeyCode() == KeyEvent.VK_DOWN) {
          downPressed();
          event.consume();
        }
        else if (event.getKeyCode() == KeyEvent.VK_UP) {
          upPressed();
          event.consume();
        }
        else if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
          backSpacePressed();
          event.consume();
        }
        else {
          char ch = event.getKeyChar();
          if (Character.isLetterOrDigit(ch) || ch == '_' || ch == ' ') {
            characterEntered(ch);
            event.consume();
          }
          else {
            unsupportedCharacterEntered(ch);
            event.consume();
          }
        }
      }

      public void keyReleased(KeyEvent e) {
        //nothing to do
      }
    };
  }

  protected void unsupportedCharacterEntered(char ch) {
    if (!searching) {
      throw new IllegalStateException();
    }
    previousSearchString = searchString;
    searching = false;
    updateMessage();
  }

  protected void characterEntered(char ch) {
    searchString = searchString + ch;
    gotoCurrentElementFits();
  }

  private void gotoCurrentElementFits() {
    if (searchable.currentElementFits(searchString)) {
      searchable.selectCurrentElement();
      updateMessage();
      return;
    }
    else {
      if (searchDirection == IncrementalSearchDirection.UP) {
        gotoFindPreviousElement();
      }
      else {
        gotoFindNextElement();
      }
    }
  }

  private void gotoFindNextElement() {
    if (searchable.findNextElement(searchString)) {
      updateMessage();
      return;
    }
    else {
      failed = true;
      //beep();
      updateMessage();
      return;
    }
  }

  private void gotoFindPreviousElement() {
    if (searchable.findPreviousElement(searchString)) {
      updateMessage();
      return;
    }
    else {
      failed = true;
      //beep();
      updateMessage();
      return;
    }
  }

  protected void backSpacePressed() {
    if (searchString.length() == 0) {
      return;
    }
    else {
      searchString = searchString.substring(0, searchString.length() - 1);
      updateMessage();
      gotoCurrentElementFits();
    }
  }

  protected void upPressed() {
    if (searching) {
      gotoPreviousPressedInSearch();
    }
  }

  protected void downPressed() {
    if (searching) {
      gotoNextPressedInSearch();
    }
  }

  protected void controlJPressed() {
    if (!searching) {
      startIncrementalFind();
    }
    else {
      if (searchString.equals("")) {
        searchString = previousSearchString;
      }
      gotoNextPressedInSearch();
    }
  }

  public void startIncrementalFind() {
    if (searching) {
      return;
    }
    searching = true;
    failed = false;
    searchString = ""; //$NON-NLS-1$
    searchable.initSearch();
    updateMessage();
  }

  private void gotoNextPressedInSearch() {
    searchDirection = IncrementalSearchDirection.DOWN;
    if (searchString == null || searchString.length() == 0) {
      return;
    }
    if (failed) {
      searchable.selectFirstElement();
      failed = false;
      gotoCurrentElementFits();
    }
    else {
      gotoFindNextElement();
    }
  }

  private void gotoPreviousPressedInSearch() {
    searchDirection = IncrementalSearchDirection.UP;
    if (searchString == null || searchString.length() == 0) {
      return;
    }
    if (failed) {
      searchable.selectLastElement();
      failed = false;
      gotoCurrentElementFits();
    }
    else {
      gotoFindPreviousElement();
    }
  }

  private void updateMessage() {
    if (searching) {
      searchable.showSearchMessage(searchString, failed);
    }
    else {
      searchable.clearSearchMessage();
    }
  }

  public boolean isSearching() {
    return searching;
  }
}