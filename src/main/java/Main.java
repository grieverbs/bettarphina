import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        var jdaBuilder = new JDABuilder(AccountType.BOT);
        var token = "";
        jdaBuilder.setToken(token);
        jdaBuilder.addEventListener(new Main());
        jdaBuilder.buildAsync();
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
