package com.discord.command.type;

import com.discord.annotation.command.CommandInfo;
import com.discord.command.CommandParameter;

import java.util.Arrays;

public interface Command {
  default boolean isCalled(String s) throws UnsupportedOperationException {
    CommandInfo commandInfo = getClass().getAnnotation(CommandInfo.class);

    if (commandInfo == null)
      throw new UnsupportedOperationException();

    return s.equalsIgnoreCase(commandInfo.name()) || Arrays.stream(commandInfo.aliases()).anyMatch(s::equalsIgnoreCase);
  }

  void exec(CommandParameter parameter);
}