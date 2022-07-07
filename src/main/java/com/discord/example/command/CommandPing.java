package com.discord.example.command;

import com.discord.annotation.command.CommandInfo;
import com.discord.command.CommandParameter;
import com.discord.command.type.Command;

@CommandInfo(name = "ping")
public class CommandPing implements Command {
  @Override
  public void exec(CommandParameter parameter) {
    parameter.reply("pong!").join();
  }
}