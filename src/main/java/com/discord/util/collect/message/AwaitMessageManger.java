package com.discord.util.collect.message;

import com.discord.util.collect.CollectManager;
import com.discord.util.collect.CollectOption;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class AwaitMessageManger extends CollectManager<TextChannel, Message> {
  @SuppressWarnings("unchecked")
  public AwaitMessageManger(TextChannel textChannel) {
    this(textChannel, (CollectOption<Message>) CollectOption.DEFAULT);
  }

  public AwaitMessageManger(TextChannel textChannel, CollectOption<Message> option) {
    super(TextChannel.class, textChannel, option);
  }

  public CompletableFuture<Collection<Message>> collect() {
    Collection<Message> collection = new HashSet<>();

    return collect(collection, getCollectBase().addMessageCreateListener(event -> {
      Message message = event.getMessage();
      Predicate<Message> predicate = getOption().getPredicate();

      if (predicate.test(message)) {
        this.lastEventTimeMillis.set(System.currentTimeMillis());
        collection.add(message);
      }
    }));
  }
}