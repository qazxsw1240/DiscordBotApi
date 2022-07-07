package com.discord.command;

import java.util.Iterator;

public class CommandTokenizer {
  private final String prefix;
  private final String content;
  private final String command;
  private final String rawParameterString;
  private final String[] parameters;

  public CommandTokenizer(String prefix, String content) throws IllegalArgumentException {
    this.prefix = prefix;
    this.content = content;

    if (!startsWithIgnoreCase())
      throw new IllegalArgumentException();

    String trimmed = this.content.substring(this.prefix.length()).trim();
    String splitString = splitUntilWhitespace(trimmed);

    if (splitString.isEmpty())
      throw new IllegalArgumentException();

    this.command = splitString;
    this.rawParameterString = trimmed.substring(splitString.length()).trim();
    this.parameters = splitWithWhitespace(this.rawParameterString);
  }

  private boolean startsWithIgnoreCase() {
    return this.content.regionMatches(false, 0, this.prefix, 0, this.prefix.length());
  }

  private String[] splitWithWhitespace(String content) {
    String s = content;
    int count = 0;

    while (!s.isEmpty()) {
      s = s.substring(splitUntilWhitespace(s).length()).trim();
      count++;
    }

    String[] splits = new String[count];
    s = content;

    for (int i = 0; !s.isEmpty(); s = s.substring(splits[i].length()).trim(), i++)
      splits[i] = splitUntilWhitespace(s);

    return splits;
  }

  private String splitUntilWhitespace(String content) {
    if (content.isEmpty())
      return content;
    return content.chars()
                  .takeWhile(c -> " \n".indexOf(c) < 0)
                  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                  .toString();
  }

  public String getPrefix() {
    return this.prefix;
  }

  public String getContent() {
    return this.content;
  }

  public String getCommand() {
    return this.command;
  }

  public String getRawParameterString() {
    return this.rawParameterString;
  }

  public String[] getParameters() {
    return this.parameters;
  }

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