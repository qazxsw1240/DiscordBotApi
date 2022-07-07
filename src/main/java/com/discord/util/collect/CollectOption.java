package com.discord.util.collect;

import java.util.function.Predicate;

public class CollectOption<T> {
  public static final long DEFAULT_TIMEOUT = 30000L;
  public static final int DEFAULT_MAX_COUNT = 1;
  public static final Predicate<?> DEFAULT_PREDICATE = item -> true;
  public static final CollectOption<?> DEFAULT = new CollectOption<>(DEFAULT_TIMEOUT, DEFAULT_MAX_COUNT, DEFAULT_PREDICATE);

  protected final long timeout;
  protected final int maxCount;
  protected final Predicate<T> predicate;

  public CollectOption(long timeout, int maxCount, Predicate<T> predicate) {
    this.timeout = timeout;
    this.maxCount = maxCount;
    this.predicate = predicate;
  }

  public Predicate<T> getPredicate() {
    return this.predicate;
  }

  public long getTimeout() {
    return this.timeout;
  }

  public int getMaxCount() {
    return this.maxCount;
  }
}