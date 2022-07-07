package com.discord.command.type;

import com.discord.command.CommandParameter;

import org.javacord.api.entity.channel.ServerVoiceChannel;

public interface VoiceConnectionCommand extends Command {
  void exec(CommandParameter parameter, ServerVoiceChannel voiceChannel);
}