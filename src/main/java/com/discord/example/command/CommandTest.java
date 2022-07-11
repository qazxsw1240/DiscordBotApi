package com.discord.example.command;

import com.discord.annotation.command.*;
import com.discord.command.*;
import com.discord.command.type.*;

import java.util.*;

@CommandInfo(name = "test")
public class CommandTest implements Command {
  @Override
  public void exec(CommandParameter parameter) {
    parameter.sendMessage(Arrays.toString(parameter.getCommandTokenizer().getParameters())).join();
  }
}