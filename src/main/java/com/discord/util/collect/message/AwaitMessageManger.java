package com.discord.util.collect.message;

import com.discord.util.collect.*;
import org.javacord.api.entity.channel.*;
import org.javacord.api.entity.message.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

/**
 * This class represents a message collector.
 *
 * @see CollectManager
 * @see CollectOption
 * @see TextChannel
 * @see Message
 */
public class AwaitMessageManger extends CollectManager<TextChannel, Message> {
  public AwaitMessageManger(TextChannel textChannel) {
    super(textChannel);
  }

  public AwaitMessageManger(TextChannel textChannel, CollectOption<Message> option) {
    super(textChannel, option);
  }

  public CompletableFuture<Collection<Message>> collect() {
    return collect(HashSet::new, getCollectBase().addMessageCreateListener(event -> {
      synchronized (this) {
        Message message = event.getMessage();
        Predicate<Message> predicate = getOption().getPredicate();

        if (predicate.test(message)) {
          this.lastEventTimeMillis.set(System.currentTimeMillis());
          this.collection.add(message);
          notify();
        }
      }
    }));
  }
}