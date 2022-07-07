package com.discord.core.listener;

import com.discord.command.CommandParameter;
import com.discord.command.CommandSet;
import com.discord.core.DiscordBot;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class DefaultMessageCreateListener implements MessageCreateListener {
  private final DiscordBot bot;

  public DefaultMessageCreateListener(DiscordBot bot) {
    this.bot = bot;
  }

  @Override
  public void onMessageCreate(MessageCreateEvent event) {
    if (!event.isServerMessage() || event.getMessageAuthor().isBotUser())
      return;

    CompletableFuture.runAsync(() -> {
      if (this.bot.getCommandSet().isEmpty())
        return;

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