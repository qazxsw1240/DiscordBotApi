package com.discord.example.command;

import com.discord.annotation.command.*;
import com.discord.command.*;
import com.discord.command.type.*;
import com.discord.util.collect.message.*;
import org.javacord.api.entity.message.*;

import java.util.*;

@CommandInfo(name = "message", aliases = "m")
public class CommandMessage implements Command {
  @Override
  public void exec(CommandParameter parameter) {
    Message message = parameter.sendMessage("Await message(s)!").join();
    AwaitMessageManger manager = new AwaitMessageManger(parameter.getTextChannel());
    Collection<Message> emojis = manager.collect().join();

    message.edit(Arrays.toString(emojis.stream()
                                       .map(Message::getIdAsString)
                                       .toArray())).join();
  }
}