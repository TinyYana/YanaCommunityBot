package TinyYana.YanaCommunityBot;

import TinyYana.YanaCommunityBot.ConfigFile.BotConfig;
import TinyYana.YanaCommunityBot.Manager.CommandManager;
import TinyYana.YanaCommunityBot.Manager.BotConsoleHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.EnumSet;

public class BotCore {
    private static JDA jda;
    private static BotConfig config;
    private static CommandManager commandManager;

    public static void main(String[] args) {
        config = new BotConfig();
        commandManager = new CommandManager();

        String token = config.get("Token").toString();
        String activity = config.get("Activity").toString();

        jda = JDABuilder.createDefault(token,
                        GatewayIntent.GUILD_MODERATION,
                        GatewayIntent.GUILD_INVITES,
                        GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.SCHEDULED_EVENTS,
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.DIRECT_MESSAGE_TYPING,
                        GatewayIntent.GUILD_MESSAGE_TYPING,
                        GatewayIntent.GUILD_VOICE_STATES).disableCache(EnumSet.of(
                        CacheFlag.CLIENT_STATUS,
                        CacheFlag.ACTIVITY,
                        CacheFlag.EMOJI
                )).enableCache(CacheFlag.VOICE_STATE)
                .addEventListeners(new Listener())
                .setActivity(Activity.listening(activity))
                .setStatus(OnlineStatus.IDLE)
                .build();

        BotConsoleHandler.consoleHandler(jda);
    }

    public static JDA getJDA() {
        return jda;
    }

    public static BotConfig getConfig() {
        return config;
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }
}