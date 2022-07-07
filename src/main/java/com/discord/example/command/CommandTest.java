package com.discord.example.command;

import com.discord.annotation.command.CommandInfo;
import com.discord.command.CommandParameter;
import com.discord.command.type.Command;

import java.util.Arrays;

@CommandInfo(name = "test")
public class CommandTest implements Command {
  @Override
  public void exec(CommandParameter parameter) {
    parameter.sendMessage(Arrays.toString(parameter.getCommandTokenizer().getParameters())).join();
  }
}