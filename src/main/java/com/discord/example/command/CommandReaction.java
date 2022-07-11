package com.discord.example.command;

import com.discord.annotation.command.*;
import com.discord.command.*;
import com.discord.command.type.*;
import com.discord.util.collect.*;
import com.discord.util.collect.reaction.*;
import org.javacord.api.entity.message.*;

import java.util.*;

@CommandInfo(name = "reaction", aliases = "r")
public class CommandReaction implements Command {
  @Override
  public void exec(CommandParameter parameter) {
    Message message = parameter.sendMessage("React here!").join();
    CollectOption<Reaction> option = new CollectOptionBuilder<Reaction>().setPredicate(reaction -> reaction.getUsers()
                                                                                                           .join()
                                                                                                           .contains(parameter.getAuthorAsUser()
                                                                                                                              .orElseThrow()))
                                                                         .setTimeout(3000)
                                                                         .setMaxCount(3)
                                                                         .build();
    AwaitReactionManger manager = new AwaitReactionManger(message, option);
    Collection<Reaction> reactions = manager.collect().join();

    message.edit(Arrays.toString(reactions.stream()
                                          .map(Reaction::toString)
                                          .toArray())).join();
  }
}