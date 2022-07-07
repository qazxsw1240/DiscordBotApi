package com.discord.example.command;

import com.discord.annotation.command.CommandInfo;
import com.discord.command.CommandParameter;
import com.discord.command.type.Command;
import com.discord.util.collect.CollectOption;
import com.discord.util.collect.CollectOptionBuilder;
import com.discord.util.collect.reaction.AwaitReactionManger;

import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;

import java.util.Arrays;
import java.util.Collection;

@CommandInfo(name = "reaction", aliases = "r")
public class CommandReaction implements Command {
  @Override
  public void exec(CommandParameter parameter) {
    Message message = parameter.sendMessage("React here!").join();
    CollectOption<Emoji> option = new CollectOptionBuilder<Emoji>().setTimeout(10000).setMaxCount(3).build();
    AwaitReactionManger manager = new AwaitReactionManger(message, option);
    Collection<Emoji> emojis = manager.collect().join();

    message.edit(Arrays.toString(emojis.stream().map(Emoji::toString).toArray())).join();
  }
}