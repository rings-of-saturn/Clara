package net.tls.tlsbot.listeners.commandUtil;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.tls.tlsbot.data.user.util.UserDataUtil;

import java.io.Console;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AutoCompleter extends ListenerAdapter {

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        if (event.getName().equals("send_as_proxy") && event.getFocusedOption().getName().equals("proxy")) {
            Path pData = UserDataUtil.getUserStorage(UserDataUtil.getUserStorage(event.getMember().getUser().getName()) + "/ProxyDatas").toPath();
            String[] words = new String[]{""};
            for (int i = 0; i < pData.getNameCount(); i++) {
                words[i] = String.valueOf(pData.getName(i));
            }
            List<Command.Choice> options = Stream.of(words)
                    .filter(word -> word.startsWith(event.getFocusedOption().getValue())) // only display words that start with the user's current input
                    .map(word -> new Command.Choice(word, word)) // map the words to choices
                    .collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
    }
}
