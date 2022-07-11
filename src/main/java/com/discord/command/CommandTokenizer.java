package com.discord.command;

import java.util.*;

public class CommandTokenizer {
  private final String prefix;
  private final String content;
  private final String command;
  private final String rawParameterString;
  private final String[] parameters;

  public CommandTokenizer(String prefix, String content) throws IllegalArgumentException {
    this.prefix = prefix;
    this.content = content;

    if (!startsWithIgnoreCase()) {
      throw new IllegalArgumentException();
    }

    String trimmed = this.content.substring(this.prefix.length()).trim();
    String splitString = splitUntilWhitespace(trimmed);

    if (splitString.isEmpty()) {
      throw new IllegalArgumentException();
    }

    this.command = splitString;
    this.rawParameterString = trimmed.substring(splitString.length()).trim();
    this.parameters = splitWithWhitespace(this.rawParameterString);
  }

  /**
   * Tests if the content starts with the specified prefix, ignoring case
   * considerations.
   *
   * @return {@code true} if the content is an empty string or starts with the
   * prefix, otherwise {@code false}
   */
  private boolean startsWithIgnoreCase() {
    return this.content.regionMatches(false, 0, this.prefix, 0, this.prefix.length());
  }

  /**
   * Splits the content with whitespaces. If the content is an empty string,
   * then returns a new empty array.
   *
   * @param content The content that will be split by whitespaces
   *
   * @return A new array of the substrings split by whitespaces
   */
  private String[] splitWithWhitespace(String content) {
    String s = content;
    int count = 0;

    while (!s.isEmpty()) {
      s = s.substring(splitUntilWhitespace(s).length()).trim();
      count++;
    }

    String[] splits = new String[count];
    s = content;

    for (int i = 0;
         !s.isEmpty();
         s = s.substring(splits[i].length()).trim(), i++) {
      splits[i] = splitUntilWhitespace(s);
    }

    return splits;
  }

  /**
   * Gets a substring that is the first char sequence until whitespaces or line
   * breaks come out.
   *
   * @param content The content that will be split by whitespaces
   *
   * @return The first substring of the content split by whitespaces
   */
  private String splitUntilWhitespace(String content) {
    if (content.isEmpty()) {
      return content;
    }

    return content.chars()
                  .takeWhile(c -> " \n".indexOf(c) < 0)
                  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                  .toString();
  }

  /**
   * Returns the prefix of the command line.
   *
   * @return The prefix
   */
  public String getPrefix() {
    return this.prefix;
  }

  /**
   * Returns the raw content of the command line.
   *
   * @return The raw content
   */
  public String getContent() {
    return this.content;
  }

  /**
   * Returns the command string after the prefix.
   *
   * @return The command
   */
  public String getCommand() {
    return this.command;
  }

  /**
   * Returns the raw string after the command.
   *
   * @return The raw string after the command
   */
  public String getRawParameterString() {
    return this.rawParameterString;
  }

  /**
   * Returns all strings split by whitespace after the command.
   *
   * @return The array of the strings of the raw parameter string split by
   * whitespace
   */
  public String[] getParameters() {
    return this.parameters;
  }

  /**
   * Returns of an {@link Iterator} of the parameter strings.
   *
   * @return An iterator of the parameter strings.
   *
   * @see Iterator
   */
  public Iterator<String> getParameterIterator() {
    return new ParameterIterator();
  }

  private class ParameterIterator implements Iterator<String> {
    private int start;

    public ParameterIterator() {
      this.start = 0;
    }

    @Override
    public boolean hasNext() {
      return this.start < CommandTokenizer.this.parameters.length;
    }

    @Override
    public String next() {
      return CommandTokenizer.this.parameters[this.start++];
    }
  }
}