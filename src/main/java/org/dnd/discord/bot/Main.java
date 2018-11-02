package org.dnd.discord.bot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.dnd.discord.utility.Configure;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Main extends ListenerAdapter {
    private static Configure configure = new Configure();

    public Main() {
    }

    public static void main(String[] args) throws LoginException {
        var jdaBuilder = new JDABuilder(AccountType.BOT);
        try {
            jdaBuilder.setToken(configure.getBotToken());
            jdaBuilder.addEventListener(new Main());
            jdaBuilder.buildAsync();
        } catch(IOException exception) {
            System.out.println(exception.getMessage());
            System.exit(0);
        }
    }

//    public void ListenerAda() {
//        event.getGuild().getRoles().stream().filter(x -> x.getName().contains("players")).findFirst().orElse(null);
//    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().toLowerCase().contains("who is the best player")) {
            event.getChannel().sendMessageFormat("<@%s> is!",event.getGuild().getOwner().getUser().getId()).queue();
        }
    }
}
