package com.discord.util.collect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.entity.DiscordEntity;
import org.javacord.api.listener.GloballyAttachableListener;
import org.javacord.api.util.event.ListenerManager;
import org.javacord.api.util.logging.ExceptionLogger;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class CollectManager<T extends DiscordEntity, U> {
  public static final Logger LOGGER = LogManager.getLogger(CollectManager.class.getName());
  protected final T collectBase;
  protected final CollectOption<U> option;
  protected final AtomicLong lastEventTimeMillis;
  protected final ExecutorService reactionCollectorService;

  @SuppressWarnings("unchecked")
  public CollectManager(Class<T> collectBaseClass, T collectBase) {
    this(collectBaseClass, collectBase, (CollectOption<U>) CollectOption.DEFAULT);
  }

  public CollectManager(Class<T> collectBaseClass, T collectBase, CollectOption<U> option) {
    this.collectBase = collectBase;
    this.option = option;
    this.lastEventTimeMillis = new AtomicLong();
    this.reactionCollectorService = Executors.newFixedThreadPool(2, runnable -> new Thread(runnable, collectBaseClass.getName() + " Collector-" + collectBase.getIdAsString()));
  }

  public T getCollectBase() {
    return this.collectBase;
  }

  public CollectOption<U> getOption() {
    return this.option;
  }

  public <E extends GloballyAttachableListener> CompletableFuture<Collection<U>> collect(Collection<U> collection, ListenerManager<E> manager) {
    CompletableFuture<Collection<U>> future = new CompletableFuture<>();

    this.lastEventTimeMillis.set(System.currentTimeMillis());

    return future.completeAsync(() -> {
      try {
        Callable<Boolean> emojiCountAwaiter = () -> {
          while (collection.size() != this.option.getMaxCount())
            continue;
          return true;
        };
        Callable<Boolean> timeAwaiter = () -> {
          while (System.currentTimeMillis() - this.lastEventTimeMillis.get() <= this.option.getTimeout())
            continue;
          return true;
        };

        this.reactionCollectorService.invokeAny(List.of(emojiCountAwaiter, timeAwaiter));
      }
      catch (ExecutionException | InterruptedException e) {
        ExceptionLogger.get(e.getClass());
      }

      manager.remove();

      return collection;
    });
  }
}