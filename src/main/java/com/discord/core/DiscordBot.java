package com.discord.core;

import com.discord.command.*;
import org.javacord.api.*;
import org.javacord.api.listener.*;
import org.javacord.api.listener.message.*;

import java.util.*;
import java.util.concurrent.*;

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