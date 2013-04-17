package de.jdemo.swingui.find;

/**
 * @author Markus Gebhard
 */
public interface IIncrementalSearchable {

  /**
   * Display the status of the incremental search. E.g. show
   * <code>"Incremental find: "+searchPattern</code> if failed==false,
   * <code>"Incremental find: "+searchPattern+" not found</code> if failed==true.
   */
  public void showSearchMessage(String searchPattern, boolean failed);

  /**
   * Clear the message shown by {@link #showSearchMessage(String, boolean)} if any.
   */
  public void clearSearchMessage();

  /**
   * Indicate unsuccessful search, usually implemented by calling getToolkit().beep().
   */
  public void beep();

  /**
   * The incremental search has been started, remember the currently selected or first
   * element of the document as the current one for operation. 
   */
  public void initSearch();

  /**
   * Returns true if the currently selected element fits the given searchPattern.
   */
  public boolean currentElementFits(String searchPattern);

  /**
   * Set the first element of the document as current element.
   */
  public void selectFirstElement();

  /** Find the next element fitting the given searchPattern. Only search to the end of
   * document. Return <code>false</code> if no element fitting the searchPattern was found. */
  public boolean findNextElement(String searchPattern);

  /** Find the previous element fitting the given searchPattern. Only search to the start of
   * document. Return <code>false</code> if no element fitting the searchPattern was found. */
  public boolean findPreviousElement(String searchPattern);

  /**
   * Set the last element of the document as current element.
   */
  public void selectLastElement();

  public void selectCurrentElement();
}