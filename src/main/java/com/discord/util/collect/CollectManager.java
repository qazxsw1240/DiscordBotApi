package com.discord.util.collect;

import org.javacord.api.entity.*;
import org.javacord.api.listener.*;
import org.javacord.api.util.event.*;
import org.javacord.api.util.logging.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

public class CollectManager<T extends DiscordEntity, U> {
  protected final T collectBase;
  protected final CollectOption<U> option;
  protected final ExecutorService reactionCollectorService;
  protected final AtomicLong lastEventTimeMillis;
  protected Collection<U> collection;

  @SuppressWarnings("unchecked")
  public CollectManager(T collectBase) {
    this(collectBase, (CollectOption<U>) CollectOption.DEFAULT);
  }

  public CollectManager(T collectBase, CollectOption<U> option) {
    this.collectBase = collectBase;
    this.option = option;
    this.reactionCollectorService = Executors.newFixedThreadPool(2);
    this.collection = null;
    this.lastEventTimeMillis = new AtomicLong();
  }

  public T getCollectBase() {
    return this.collectBase;
  }

  public CollectOption<U> getOption() {
    return this.option;
  }

  public <E extends GloballyAttachableListener> CompletableFuture<Collection<U>> collect(Supplier<? extends Collection<U>> supplier, ListenerManager<E> manager) {
    CompletableFuture<Collection<U>> future = new CompletableFuture<>();

    this.collection = supplier.get();
    this.lastEventTimeMillis.set(System.currentTimeMillis());

    return future.completeAsync(() -> {
      try {
        this.reactionCollectorService.invokeAny(List.of(new CollectionAwaiterCallable(), new TimeAwaiterCallable()));
      } catch (InterruptedException | ExecutionException e) {
        ExceptionLogger.get(e.getClass());
      }

      manager.remove();

      return this.collection;
    });
  }

  private class CollectionAwaiterCallable implements Callable<Boolean> {
    @Override
    public Boolean call() throws InterruptedException {
      synchronized (CollectManager.this) {
        while (CollectManager.this.collection.size() != getOption().getMaxCount()) {
          CollectManager.this.wait();
        }
        return true;
      }
    }
  }

  private class TimeAwaiterCallable implements Callable<Boolean> {
    private boolean isTimeout() {
      return System.currentTimeMillis() - CollectManager.this.lastEventTimeMillis.get() > getOption().getTimeout();
    }

    @Override
    public Boolean call() {
      while (!isTimeout()) {
        continue;
      }
      return true;
    }
  }
}