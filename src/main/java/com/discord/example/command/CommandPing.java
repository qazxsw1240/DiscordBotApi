package com.discord.example.command;

import com.discord.annotation.command.*;
import com.discord.command.*;
import com.discord.command.type.*;

@CommandInfo(name = "ping")
public class CommandPing implements Command {
  @Override
  public void exec(CommandParameter parameter) {
    parameter.reply("pong!").join();
  }
}