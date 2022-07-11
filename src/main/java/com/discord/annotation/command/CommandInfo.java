package com.discord.annotation.command;

import com.discord.command.type.*;

import java.lang.annotation.*;

/**
 * This annotation includes information of commands that the bot will execute.
 * All commands must have its command name, and some commands should have
 * aliases if its name is quite complex to type or too long. Thus, all command
 * class have to be annotated with this annotation.
 *
 * @author qazxsw1240
 * @see Command#isCalled(String)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {
  /**
   * Returns the name of this command.
   *
   * @return The name of this command.
   */
  String name() default "";

  /**
   * Returns the aliases of this command.
   *
   * @return The array of the aliases.
   */
  String[] aliases() default {};
}