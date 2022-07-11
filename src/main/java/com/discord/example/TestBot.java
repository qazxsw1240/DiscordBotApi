package com.discord.example;

import com.discord.core.*;
import com.discord.core.listener.*;
import com.discord.example.command.*;
import org.apache.logging.log4j.*;
import org.javacord.api.*;

import java.io.*;

public class TestBot {
  public static final Logger LOGGER = LogManager.getLogger(TestBot.class.getName());

  public static void main(String[] args) throws IOException {
    String prefix = "!";
    String token;

    System.out.print("Token : ");

    token = new BufferedReader(new InputStreamReader(System.in)).readLine();

    DiscordBot discordBot = DiscordBot.createInstance(prefix)
                                      .setToken(token)
                                      .registerCommandSet();

    discordBot.getCommandSet()
              .addCommand(new CommandPing())
              .addCommand(new CommandTest())
              .addCommand(new CommandReaction())
              .addCommand(new CommandMessage());

    DiscordApi api = discordBot.addMessageCreateListener(new DefaultMessageCreateListener(discordBot))
                               .login()
                               .join();

    LOGGER.atInfo().log("Logged as " + api.getYourself().getName());
  }
}