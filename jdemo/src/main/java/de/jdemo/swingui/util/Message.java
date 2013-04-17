package de.jdemo.swingui.util;

/**
 * @author Markus Gebhard
 */
public class Message {

  private final String text;
  private final boolean isError;

  public Message(String text) {
    this(text, false);
  }

  public Message(String text, boolean isError) {
    this.text = text;
    this.isError = isError;
  }

  public boolean isError() {
    return isError;
  }

  public String getText() {
    return text;
  }

}