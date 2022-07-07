package com.discord.util.collect;

import java.util.function.Predicate;

public class CollectOptionBuilder<T> {
  protected Predicate<T> predicate;
  protected long timeout;
  protected int maxCount;

  @SuppressWarnings("unchecked")
  public CollectOptionBuilder() {
    this.timeout = CollectOption.DEFAULT_TIMEOUT;
    this.maxCount = CollectOption.DEFAULT_MAX_COUNT;
    this.predicate = (Predicate<T>) CollectOption.DEFAULT_PREDICATE;
  }

  public CollectOptionBuilder<T> setPredicate(Predicate<T> predicate) {
    this.predicate = predicate;
    return this;
  }

  public CollectOptionBuilder<T> setTimeout(long timeout) {
    this.timeout = timeout;
    return this;
  }

  public CollectOptionBuilder<T> setMaxCount(int maxCount) {
    this.maxCount = maxCount;
    return this;
  }

  public CollectOption<T> build() {
    return new CollectOption<>(this.timeout, this.maxCount, this.predicate);
  }
}