package net.tls.tlsbot.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.tls.tlsbot.data.user.util.UserDataUtil;

import java.io.File;
import java.nio.file.Path;

public class MessageReceiveListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        event.getChannel().asTextChannel().sendMessage("testtt").queue();
        System.out.println(event.getMessage().getContentDisplay());
        String member = event.getMember().getUser().getName();
        String message = event.getMessage().toString();
        String[] proxies = new String[]{""};
        Path pData = UserDataUtil.getUserStorage(UserDataUtil.getUserStorage(member) + "/ProxyDatas").toPath();
        for (int i = 0; i < pData.getNameCount(); i++) {
            proxies[i] = String.valueOf(pData.getName(i));
        }
        for (String proxy : proxies) {
            if (message.startsWith(proxy)) {
                File data = UserDataUtil.getUserStorage(event.getMember().getUser().getName());
                File proxyData = UserDataUtil.getProxyData(data, proxy);
                String dn = UserDataUtil.getProxyDisplayName(proxyData, proxy);
                EmbedBuilder embed = new EmbedBuilder();
                embed.setAuthor(dn);
                embed.setTitle(message);
                event.getChannel().sendMessageEmbeds(embed.build()).queue();

            }
        }
    }
}