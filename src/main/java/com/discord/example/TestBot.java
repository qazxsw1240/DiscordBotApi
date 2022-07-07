package com.discord.example;

import com.discord.core.DiscordBot;
import com.discord.core.listener.DefaultMessageCreateListener;
import com.discord.example.command.CommandPing;
import com.discord.example.command.CommandReaction;
import com.discord.example.command.CommandTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;

public class TestBot {
  public static final String PREFIX = "An-Awesome-Prefix";
  public static final String TOKEN = "An-Awesome-Token";

  public static final Logger LOGGER = LogManager.getLogger(TestBot.class.getName());

  public static void main(String[] args) {
    FallbackLoggerConfiguration.setDebug(true);
    FallbackLoggerConfiguration.setTrace(true);

    DiscordBot discordBot = DiscordBot.createInstance(PREFIX).setToken(TOKEN).registerCommandSet();

    discordBot.getCommandSet()
              .addCommand(new CommandPing())
              .addCommand(new CommandTest())
              .addCommand(new CommandReaction());

    DiscordApi api = discordBot.addMessageCreateListener(new DefaultMessageCreateListener(discordBot)).login().join();

    LOGGER.atInfo().log("Logged as " + api.getYourself().getName());
  }
}