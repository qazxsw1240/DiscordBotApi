package com.discord.util.collect;

import java.util.function.*;

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

  /**
   * Sets the predicate that filters entities.
   *
   * @param predicate The predicate that filters entities
   *
   * @return The current instance to chain call methods
   */
  public CollectOptionBuilder<T> setPredicate(Predicate<T> predicate) {
    this.predicate = predicate;
    return this;
  }

  /**
   * Sets the timeout to stop the collector after inactivity in milliseconds.
   *
   * @param timeout The timeout to stop the collector after inactivity in
   *                milliseconds
   *
   * @return The current instance to chain call methods
   */
  public CollectOptionBuilder<T> setTimeout(long timeout) {
    this.timeout = timeout;
    return this;
  }

  /**
   * Sets the maximum total amount of reactions to collect.
   *
   * @param maxCount The maximum total amount of reactions to collect
   *
   * @return The current instance to chain call methods
   */
  public CollectOptionBuilder<T> setMaxCount(int maxCount) {
    this.maxCount = maxCount;
    return this;
  }

  public CollectOption<T> build() {
    return new CollectOption<>(this.timeout, this.maxCount, this.predicate);
  }
}