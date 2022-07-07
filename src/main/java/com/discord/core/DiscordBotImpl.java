package com.discord.core;

import com.discord.command.CommandSet;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.listener.GloballyAttachableListener;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.listener.message.MessageDeleteListener;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

class DiscordBotImpl implements DiscordBot {
  private final DiscordApiBuilder builder;
  private final Map<Class<? extends GloballyAttachableListener>, List<GloballyAttachableListener>> listeners;
  private String prefix;
  private DiscordApi api;
  private CommandSet commandSet;

  public DiscordBotImpl(String prefix) {
    this.prefix = prefix;
    this.builder = new DiscordApiBuilder();
    this.listeners = new ConcurrentHashMap<>();
    this.api = null;
    this.commandSet = null;
  }

  @Override
  public CompletableFuture<DiscordApi> login() {
    return CompletableFuture.supplyAsync(() -> {
      this.api = this.builder.login().join();
      for (List<GloballyAttachableListener> listenerList : this.listeners.values())
        for (GloballyAttachableListener listener : listenerList)
          this.api.addListener(listener);
      this.listeners.clear();
      return this.api;
    });
  }

  @Override
  public boolean isCached() {
    return this.api != null;
  }

  @Override
  public String getPrefix() {
    return this.prefix;
  }

  @Override
  public DiscordBot setPrefix(String prefix) {
    this.prefix = prefix;
    return this;
  }

  @Override
  public Optional<String> getToken() {
    return this.builder.getToken();
  }

  @Override
  public DiscordBot setToken(String token) {
    this.builder.setToken(token);
    return this;
  }

  @Override
  public synchronized Optional<DiscordApi> getApi() {
    return Optional.ofNullable(this.api);
  }

  @Override
  public DiscordBot registerCommandSet() {
    this.commandSet = new CommandSet();
    return this;
  }

  @Override
  public CommandSet getCommandSet() throws NoSuchElementException {
    if (this.commandSet == null)
      throw new NoSuchElementException();
    return this.commandSet;
  }

  @Override
  public synchronized <T extends GloballyAttachableListener> DiscordBot addListener(T listener) {
    prepareListener(listener.getClass(), listener);

    if (!isCached())
      this.listeners.get(listener.getClass()).add(listener);
    else {
      List<GloballyAttachableListener> listenerList = this.listeners.remove(listener.getClass());

      for (GloballyAttachableListener globallyAttachableListener : listenerList)
        this.api.addListener(globallyAttachableListener);
    }

    return this;
  }

  @Override
  public synchronized <T extends MessageCreateListener> DiscordBot addMessageCreateListener(T listener) {
    return addListener(listener);
  }

  public synchronized <T extends MessageDeleteListener> DiscordBot addMessageAttachableListener(T listener) {
    return addListener(listener);
  }

  private <T extends GloballyAttachableListener> void prepareListener(Class<? extends T> listenerClass, T listener) {
    if (!this.listeners.containsKey(listenerClass))
      this.listeners.put(listenerClass, new CopyOnWriteArrayList<>());
    this.listeners.get(listenerClass).add(listener);
  }
}