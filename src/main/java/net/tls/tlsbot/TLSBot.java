package net.tls.tlsbot;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.tls.tlsbot.listeners.CommandListener;
import net.tls.tlsbot.listeners.MessageReceiveListener;

import java.util.EnumSet;

public class TLSBot {
    private static final Dotenv config = Dotenv.configure().ignoreIfMissing().load();
    public static JDA bot;

    public static void main(String[] args) throws InterruptedException {
        bot = JDABuilder.create(config.get("TOKEN"), EnumSet.of(GatewayIntent.DIRECT_MESSAGES, GatewayIntent.MESSAGE_CONTENT))
                .addEventListeners(new MessageReceiveListener())
                .addEventListeners(new CommandListener())
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.watching("How to code a Discord Bot"))
                .build().awaitReady();

        bot.updateCommands().addCommands(
                Commands.slash("ping", "pong")
                        .setGuildOnly(false),
                Commands.slash("send_as_proxy", "Sends a message as your selected proxy!")
                        .addOption(OptionType.STRING, "proxy", "The Proxy's Name", true, true)
                        .addOption(OptionType.STRING, "message", "The Message to be sent as the proxy", true, false)
                        .setGuildOnly(false),
                Commands.slash("create_proxy", "Creates a proxy!")
                        .addOption(OptionType.STRING, "name", "The Proxy's Name", true, false)
                        .setGuildOnly(false)

                        //.addOption(OptionType.STRING, "proxy", "The Proxy's Proxy", true, false)
        ).queue();
    }}