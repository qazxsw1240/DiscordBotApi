package com.discord.command.type;

import com.discord.command.*;
import org.javacord.api.entity.channel.*;

/**
 * This interface represents the command execution with voice channels. All
 * commands are executed with each {@link CommandParameter} instances created
 * when the message event occurred.
 *
 * @author qazxsw1240
 * @see Command
 * @see CommandParameter
 * @see VoiceChannel
 */
public interface VoiceConnectionCommand extends Command {
  /**
   * Executes the commands with voice channels. You should be aware of thread
   * managements.
   *
   * @param parameter The command parameters that will be used for the
   *                  execution.
   */
  void exec(CommandParameter parameter, VoiceChannel voiceChannel);
}