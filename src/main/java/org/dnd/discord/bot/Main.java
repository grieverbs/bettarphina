package org.dnd.discord.bot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.dnd.discord.utility.Configure;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.dnd.discord.Constants.*;
import static org.dnd.discord.utility.DiceUtils.*;

public class Main extends ListenerAdapter {
    private static Configure configure = new Configure();
    private static JDABuilder jdaBuilder;
    private final static String DUNGEONS_AND_DRAGONS_CHANNEL = "dungeons_and_dragons";
    private final static String DUNGEONS_AND_DRAGONS_ROLE = "players";
    private final static int NOON = 12;
    private final static int MINUTE_TO_NOON = 59;
    private final static int NEW_SECOND = 0;
    private final static int MONDAY = 1;
    private final static int WEDNESDAY = 3;
    private final static long PERIOD = 1;
    private final static long INITIAL_DELAY = 0;
    private final static int CORE_POOL_SIZE = 1;

    public static void main(String[] args) throws LoginException {
        jdaBuilder = new JDABuilder(AccountType.BOT);
        try {
            jdaBuilder.setToken(configure.getBotToken());
            jdaBuilder.addEventListener(new Main());
            jdaBuilder.buildAsync();
        } catch (final IOException exception) {
            System.out.println(exception.getMessage());
            System.exit(0);
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().toLowerCase().contains("who is the best player")) {
            event.getChannel().sendMessageFormat("<@%s> is!", event.getGuild().getOwner().getUser().getId()).queue();
        }
        else if (event.getMessage().getContentRaw().toLowerCase().contains("roll stats block")) {
            final List<Integer> stats = randomStatBlock(true);
            event.getChannel().sendMessageFormat("\nStats block: %s\nBuy in weight: %d", printStatBlock(stats),
                    stats.stream().mapToInt(x -> STAT_WEIGHT.get(x)).sum()).queue();
        }
        else if (event.getMessage().getContentRaw().toLowerCase().contains("4d6 stats block statistic")) {
            final int sampleSize = 10000000;
            final Map<Integer, AtomicInteger> map = getD4Statistics(sampleSize, true);
            event.getChannel().sendMessage(toStringStatistic(map, sampleSize)).queue();
        }
        else if (event.getMessage().getContentRaw().toLowerCase().contains("3d6 stats block statistic")) {
            final int sampleSize = 10000000;
            final Map<Integer, AtomicInteger> map = getD4Statistics(sampleSize, false);
            event.getChannel().sendMessage(toStringStatistic(map, sampleSize)).queue();
        }
    }

    @Override
    public void onReady(ReadyEvent event) {
        var myChannel = event.getJDA().getTextChannels().stream().filter(x -> x.getName().toLowerCase()
                .contains(DUNGEONS_AND_DRAGONS_CHANNEL)).findFirst().orElse(null);
        var myRole = event.getJDA().getRoles().stream().filter(x -> x.getName().toLowerCase()
                .contains(DUNGEONS_AND_DRAGONS_ROLE)).findFirst().orElse(null);
        if (myChannel != null && myRole != null) {
            Executors.newScheduledThreadPool(CORE_POOL_SIZE).scheduleAtFixedRate(() -> {
                if (LocalDateTime.now().getDayOfWeek().getValue() == WEDNESDAY - 1)
                    checkEveryHour(myChannel, myRole);
            }, INITIAL_DELAY, PERIOD, TimeUnit.DAYS);
        }
    }

    private static void checkEveryHour(final TextChannel myChannel, final Role myRole) {
        var executor = Executors.newScheduledThreadPool(CORE_POOL_SIZE);
        executor.scheduleAtFixedRate(() -> {
            if (LocalDateTime.now().getHour() == (NOON - 1)) {
                checkEveryMinute(myChannel, myRole);
                executor.shutdown();
            }
        }, INITIAL_DELAY, PERIOD, TimeUnit.HOURS);
    }

    private static void checkEveryMinute(final TextChannel myChannel, final Role myRole) {
        var executor = Executors.newScheduledThreadPool(CORE_POOL_SIZE);
        executor.scheduleAtFixedRate(() -> {
            if (LocalDateTime.now().getMinute() == MINUTE_TO_NOON) {
                checkEverySecond(myChannel, myRole);
                executor.shutdown();
            }

        }, INITIAL_DELAY, PERIOD, TimeUnit.MINUTES);
    }

    private static void checkEverySecond(final TextChannel myChannel, final Role myRole) {
        var executor = Executors.newScheduledThreadPool(CORE_POOL_SIZE);
        executor.scheduleAtFixedRate(() -> {
            if (LocalDateTime.now().getSecond() == NEW_SECOND) {
                myChannel.sendMessageFormat("Hey %s, are we continuing our quest this week?", myRole).queue();
                executor.shutdown();
            }
        }, INITIAL_DELAY, PERIOD, TimeUnit.SECONDS);
    }
}
