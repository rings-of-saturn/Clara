package net.tls.tlsbot.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.tls.tlsbot.TLSBot;
import net.tls.tlsbot.data.user.util.UserDataUtil;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "create_proxy" -> {
                event.deferReply().queue();
                String proxy_name = event.getOption("name", OptionMapping::getAsString);
                String proxy = event.getOption("proxy", OptionMapping::getAsString);
                File data = UserDataUtil.getUserStorage(event.getMember().getUser().getName());
                File proxyData = UserDataUtil.getProxyData(data, proxy_name);
                UserDataUtil.setProxyName(proxyData, proxy_name);
                event.getHook().sendMessage("Created new Proxy " + proxy_name + "!" + "\n" + "With proxy: “" + proxy + ": text“").queue();
            }
            case "leave" -> {
                event.reply("I'm leaving the server now!")
                        .setEphemeral(true) // this message is only visible to the command user
                        .flatMap(m -> event.getGuild().leave()) // append a follow-up action using flatMap
                        .queue(); // enqueue both actions to run in sequence (send message -> leave guild)
            }
            case "ping" -> {
                event.reply("PONG!").queue();
            }
            case "send_as_proxy" ->{
                String proxy_name = event.getOption("proxy", OptionMapping::getAsString);
                String message = event.getOption("message", OptionMapping::getAsString);
                File data = UserDataUtil.getUserStorage(event.getMember().getUser().getName());
                File proxyData = UserDataUtil.getProxyData(data, proxy_name);
                String dn = UserDataUtil.getProxyDisplayName(proxyData, proxy_name);
                EmbedBuilder embed = new EmbedBuilder();
                embed.setAuthor(dn);
                embed.setTitle(message);
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
            }
            default -> {
                return;
            }
        }
    }
}
