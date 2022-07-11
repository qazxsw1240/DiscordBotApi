package com.discord.util.collect.reaction;

import com.discord.util.collect.*;
import org.javacord.api.entity.message.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

public class AwaitReactionManger extends CollectManager<Message, Reaction> {
  public AwaitReactionManger(Message message) {
    super(message);
  }

  public AwaitReactionManger(Message message, CollectOption<Reaction> option) {
    super(message, option);
  }

  public CompletableFuture<Collection<Reaction>> collect() {
    return collect(HashSet::new, getCollectBase().addReactionAddListener(event -> {
      synchronized (this) {
        Reaction reaction = event.getReaction().orElseThrow();
        Predicate<Reaction> predicate = getOption().getPredicate();

        if (predicate.test(reaction)) {
          this.lastEventTimeMillis.set(System.currentTimeMillis());
          this.collection.add(reaction);
          notify();
        }
      }
    }));
  }
}