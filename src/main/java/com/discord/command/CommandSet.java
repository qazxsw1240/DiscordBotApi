package com.discord.command;

import com.discord.command.type.Command;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class CommandSet implements Set<Command> {
  private static final int DEFAULT_CAPACITY = 8;

  private int capacity;
  private int size;
  private Command[] commands;

  public CommandSet() {
    this.capacity = DEFAULT_CAPACITY;
    this.size = 0;
    this.commands = new Command[this.capacity];
  }

  public Command get(String s) throws NoSuchElementException {
    for (int i = 0; i < this.size; i++)
      if (this.commands[i].isCalled(s))
        return this.commands[i];

    throw new NoSuchElementException();
  }

  @Override
  public int size() {
    return this.size;
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public boolean contains(Object o) {
    return Arrays.asList(this.commands).contains(o);
  }

  @Override
  public Iterator<Command> iterator() {
    return new CommandSetIterator();
  }

  @Override
  public Object[] toArray() {
    return toArray(new Object[0]);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T[] toArray(T[] a) {
    T[] array;

    if (a.length >= size())
      array = a;
    else
      array = (T[]) new Object[size()];

    //noinspection SuspiciousSystemArraycopy
    System.arraycopy(this.commands, 0, array, 0, size());

    return array;
  }

  @Override
  public boolean add(Command command) {
    for (int i = 0; i < size(); i++)
      if (this.commands[i].equals(command))
        return false;

    if (size() == this.capacity)
      increaseSet();

    this.commands[this.size++] = command;

    return true;
  }

  @Override
  public boolean remove(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    if (c.isEmpty())
      return true;
    return new HashSet<>(List.of(this.commands)).containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends Command> c) {
    if (c.isEmpty())
      return false;

    c.forEach(this::add);

    return true;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    if (c.isEmpty())
      return false;
    return Arrays.asList(this.commands).retainAll(c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    if (c.isEmpty())
      return false;
    return Arrays.asList(this.commands).removeAll(c);
  }

  @Override
  public void clear() {
    Arrays.fill(this.commands, null);
    this.capacity = DEFAULT_CAPACITY;
    this.size = 0;
    this.commands = new Command[this.capacity];
  }

  public CommandSet addCommand(Command c) {
    add(c);
    return this;
  }

  private void increaseSet() {
    this.capacity <<= 1;
    Command[] newArray = new Command[this.capacity];
    System.arraycopy(this.commands, 0, newArray, 0, size());
    this.commands = newArray;
  }

  private class CommandSetIterator implements Iterator<Command> {
    private int start;

    public CommandSetIterator() {
      this.start = 0;
    }

    @Override
    public boolean hasNext() {
      return this.start < size();
    }

    @Override
    public Command next() {
      return CommandSet.this.commands[this.start++];
    }
  }
}