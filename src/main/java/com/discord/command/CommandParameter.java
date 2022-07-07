package com.discord.command;

import com.discord.core.DiscordBot;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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

  public CompletableFuture<Message> reply(EmbedBuilder embedBuilder) {
    return this.event.getMessage().reply(embedBuilder);
  }

  public CompletableFuture<Message> reply(String messageContent) {
    return this.event.getMessage().reply(messageContent);
  }

  public CompletableFuture<Message> sendMessage(String content, EmbedBuilder embed, boolean tts, String nonce, InputStream stream, String fileName) {
    return new MessageBuilder().append(content == null ? "" : content)
                               .setEmbed(embed)
                               .setTts(tts)
                               .setNonce(nonce)
                               .addAttachment(stream, fileName)
                               .send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(String content, EmbedBuilder embed, boolean tts, String nonce, File... files) {
    MessageBuilder messageBuilder = new MessageBuilder().append(content == null ? "" : content)
                                                        .setEmbed(embed)
                                                        .setTts(tts)
                                                        .setNonce(nonce);
    for (File file : files)
      messageBuilder.addAttachment(file);
    return messageBuilder.send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(String content, EmbedBuilder embed, boolean tts, String nonce) {
    return new MessageBuilder().append(content == null ? "" : content)
                               .setEmbed(embed)
                               .setTts(tts)
                               .setNonce(nonce)
                               .send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(String content, EmbedBuilder embed) {
    return new MessageBuilder().append(content == null ? "" : content).addEmbed(embed).send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(String content, EmbedBuilder embed, HighLevelComponent... components) {
    return sendMessage(content, Collections.singletonList(embed), components);
  }

  public CompletableFuture<Message> sendMessage(String content, EmbedBuilder embed, File... files) {
    return sendMessage(content, Collections.singletonList(embed), files);
  }

  public CompletableFuture<Message> sendMessage(String content, List<EmbedBuilder> embeds) {
    return new MessageBuilder().append(content == null ? "" : content).setEmbeds(embeds).send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(String content, List<EmbedBuilder> embeds, HighLevelComponent... components) {
    return new MessageBuilder().append(content == null ? "" : content)
                               .setEmbeds(embeds)
                               .addComponents(components)
                               .send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(String content, List<EmbedBuilder> embeds, File... files) {
    MessageBuilder messageBuilder = new MessageBuilder().append(content == null ? "" : content).setEmbeds(embeds);
    for (File file : files)
      messageBuilder.addAttachment(file);
    return messageBuilder.send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(String content) {
    return new MessageBuilder().append(content == null ? "" : content).send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(String content, EmbedBuilder... embeds) {
    return new MessageBuilder().append(content == null ? "" : content).addEmbeds(embeds).send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(String content, HighLevelComponent... components) {
    return new MessageBuilder().append(content == null ? "" : content).addComponents(components).send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(String content, File... files) {
    MessageBuilder messageBuilder = new MessageBuilder().append(content == null ? "" : content);
    for (File file : files)
      messageBuilder.addAttachment(file);
    return messageBuilder.send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(File... files) {
    MessageBuilder messageBuilder = new MessageBuilder();
    for (File file : files)
      messageBuilder.addAttachment(file);
    return messageBuilder.send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(InputStream stream, String fileName) {
    return new MessageBuilder().addAttachment(stream, fileName).send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(String content, InputStream stream, String fileName) {
    return new MessageBuilder().append(content == null ? "" : content)
                               .addAttachment(stream, fileName)
                               .send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(String content, EmbedBuilder embed, InputStream stream, String fileName) {
    return new MessageBuilder().append(content == null ? "" : content)
                               .setEmbed(embed)
                               .addAttachment(stream, fileName)
                               .send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(String content, List<EmbedBuilder> embeds, InputStream stream, String fileName) {
    return new MessageBuilder().append(content == null ? "" : content)
                               .setEmbeds(embeds)
                               .addAttachment(stream, fileName)
                               .send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(EmbedBuilder embed) {
    return sendMessage(Collections.singletonList(embed));
  }

  public CompletableFuture<Message> sendMessage(EmbedBuilder... embeds) {
    return sendMessage(Arrays.asList(embeds));
  }

  public CompletableFuture<Message> sendMessage(EmbedBuilder embed, File... files) {
    return sendMessage(Collections.singletonList(embed), files);
  }

  public CompletableFuture<Message> sendMessage(EmbedBuilder embed, HighLevelComponent... components) {
    return sendMessage(Collections.singletonList(embed), components);
  }

  public CompletableFuture<Message> sendMessage(EmbedBuilder embed, InputStream stream, String fileName) {
    return sendMessage(Collections.singletonList(embed), stream, fileName);
  }

  public CompletableFuture<Message> sendMessage(List<EmbedBuilder> embeds) {
    return new MessageBuilder().setEmbeds(embeds).send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(List<EmbedBuilder> embeds, HighLevelComponent... components) {
    return new MessageBuilder().setEmbeds(embeds).addComponents(components).send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(List<EmbedBuilder> embeds, File... files) {
    MessageBuilder messageBuilder = new MessageBuilder().setEmbeds(embeds);
    for (File file : files)
      messageBuilder.addAttachment(file);
    return messageBuilder.send(getTextChannel());
  }

  public CompletableFuture<Message> sendMessage(List<EmbedBuilder> embeds, InputStream stream, String fileName) {
    return new MessageBuilder().setEmbeds(embeds).addAttachment(stream, fileName).send(getTextChannel());
  }
}