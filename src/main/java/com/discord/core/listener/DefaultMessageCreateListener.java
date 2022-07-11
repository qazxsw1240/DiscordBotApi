package com.discord.core.listener;

import com.discord.command.*;
import com.discord.core.*;
import org.javacord.api.event.message.*;
import org.javacord.api.listener.message.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;

public class DefaultMessageCreateListener implements MessageCreateListener {
  private final DiscordBot bot;

  public DefaultMessageCreateListener(DiscordBot bot) {
    this.bot = bot;
  }

  @Override
  public void onMessageCreate(MessageCreateEvent event) {
    if (!event.isServerMessage() || event.getMessageAuthor().isBotUser()) {
      return;
    }

    CompletableFuture.runAsync(() -> {
      if (this.bot.getCommandSet().isEmpty()) {
        return;
      }

      CommandSet commandSet = this.bot.getCommandSet();

      try {
        CommandParameter parameter = new CommandParameter(this.bot, event);
        commandSet.get(parameter.getCommandTokenizer().getCommand()).exec(parameter);
      }
      catch (NoSuchElementException e) {
        Logger.getLogger(this.bot.getClass().getName()).info("Invalid Command Requested.");
      }
      catch (IllegalArgumentException ignored) {
      }
    });
  }
}