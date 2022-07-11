package com.discord.command.type;

import com.discord.annotation.command.*;
import com.discord.command.*;

import java.util.*;
import java.util.stream.*;

/**
 * This interface represents the basic of the command execution. All commands
 * are executed with each {@link CommandParameter} instances created when the
 * message event occurred.
 *
 * @author qazxsw1240
 * @see CommandParameter
 */
public interface Command {
  /**
   * Tests if the character sequence is compatible with the execution of this
   * command. The information of this command must be annotated with
   * {@link CommandInfo}.
   *
   * @param s The character sequence
   *
   * @return Whether the character sequence is compatible with the execution of
   * this command.
   *
   * @throws UnsupportedOperationException If the command is not annotated with
   *                                       {@link CommandInfo}
   * @see CommandInfo
   */
  default boolean isCalled(String s) throws UnsupportedOperationException {
    CommandInfo commandInfo = getClass().getAnnotation(CommandInfo.class);

    if (commandInfo == null) {
      throw new UnsupportedOperationException();
    }

    Stream<String> aliasStream = Arrays.stream(commandInfo.aliases());

    return s.equalsIgnoreCase(commandInfo.name()) || aliasStream.anyMatch(s::equalsIgnoreCase);
  }

  /**
   * Executes the commands. You should be aware of thread managements.
   *
   * @param parameter The command parameters that will be used for the
   *                  execution.
   */
  void exec(CommandParameter parameter);
}