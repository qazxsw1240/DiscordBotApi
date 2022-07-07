package com.discord.core;

import com.discord.command.CommandSet;

import org.javacord.api.DiscordApi;
import org.javacord.api.listener.GloballyAttachableListener;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.listener.message.MessageDeleteListener;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface DiscordBot {
  static DiscordBot createInstance(String prefix) {
    return new DiscordBotImpl(prefix);
  }

  CompletableFuture<DiscordApi> login();

  boolean isCached();

  String getPrefix();

  DiscordBot setPrefix(String prefix);

  Optional<String> getToken();

  DiscordBot setToken(String token);

  Optional<DiscordApi> getApi();

  DiscordBot registerCommandSet();

  CommandSet getCommandSet() throws NoSuchElementException;

  <T extends GloballyAttachableListener> DiscordBot addListener(T listener);

  <T extends MessageCreateListener> DiscordBot addMessageCreateListener(T listener);

  <T extends MessageDeleteListener> DiscordBot addMessageAttachableListener(T listener);
}