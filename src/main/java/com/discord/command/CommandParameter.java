package com.discord.command;

import com.discord.core.*;
import org.javacord.api.entity.channel.*;
import org.javacord.api.entity.message.*;
import org.javacord.api.entity.message.embed.*;
import org.javacord.api.entity.user.*;
import org.javacord.api.event.message.*;

import java.util.*;
import java.util.concurrent.*;

public class CommandParameter {
  private final MessageCreateEvent event;
  private final String messageContent;
  private final MessageAuthor messageAuthor;

  private final CommandTokenizer commandTokenizer;

  public CommandParameter(DiscordBot bot, MessageCreateEvent event) throws IllegalArgumentException {
    this.event = event;
    this.messageContent = this.event.getMessageContent();
    this.messageAuthor = this.event.getMessageAuthor();
    this.commandTokenizer = new CommandTokenizer(bot.getPrefix(), this.messageContent);
  }

  public String getMessageContent() {
    return this.messageContent;
  }

  public MessageAuthor getMessageAuthor() {
    return this.messageAuthor;
  }

  public Optional<User> getAuthorAsUser() {
    return getMessageAuthor().asUser();
  }

  public TextChannel getTextChannel() {
    return this.event.getChannel();
  }

  public Optional<ServerTextChannel> getTextChannelAsServerTextChannel() {
    return getTextChannel().asServerTextChannel();
  }

  public CommandTokenizer getCommandTokenizer() {
    return this.commandTokenizer;
  }

  public CompletableFuture<Void> delete() {
    return this.event.getMessage().delete();
  }

  public CompletableFuture<Void> delete(String reason) {
    return this.event.getMessage().delete(reason);
  }

  public CompletableFuture<Message> reply(String messageContent) {
    return this.event.getMessage().reply(messageContent);
  }

  public CompletableFuture<Message> reply(EmbedBuilder embedBuilder) {
    return this.event.getMessage().reply(embedBuilder);
  }

  public CompletableFuture<Message> sendMessage(String content) {
    return new MessageBuilder().setContent(content).send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(String content, boolean isTts) {
    return new MessageBuilder().setContent(content)
                               .setTts(isTts)
                               .send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(String content, EmbedBuilder embedBuilder) {
    return new MessageBuilder().setContent(content)
                               .setEmbed(embedBuilder)
                               .send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(EmbedBuilder embedBuilder) {
    return new MessageBuilder().setEmbed(embedBuilder).send(getTextChannel());
  }
}