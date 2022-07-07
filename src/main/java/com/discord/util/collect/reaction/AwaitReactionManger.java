package com.discord.util.collect.reaction;

import com.discord.util.collect.CollectManager;
import com.discord.util.collect.CollectOption;

import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class AwaitReactionManger extends CollectManager<Message, Emoji> {
  @SuppressWarnings("unchecked")
  public AwaitReactionManger(Message message) {
    this(message, (CollectOption<Emoji>) CollectOption.DEFAULT);
  }

  public AwaitReactionManger(Message message, CollectOption<Emoji> option) {
    super(Message.class, message, option);
  }

  public CompletableFuture<Collection<Emoji>> collect() {
    Collection<Emoji> collection = new HashSet<>();

    return collect(collection, getCollectBase().addReactionAddListener(event -> {
      Emoji emoji = event.getEmoji();
      Predicate<Emoji> predicate = getOption().getPredicate();

      if (predicate.test(emoji)) {
        this.lastEventTimeMillis.set(System.currentTimeMillis());
        collection.add(emoji);
      }
    }));
  }
}