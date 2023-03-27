package TinyYana.YanaCommunityBot;

import TinyYana.YanaCommunityBot.Commands.Test;
import TinyYana.YanaCommunityBot.Manager.CommandHandler;
import TinyYana.YanaCommunityBot.Manager.CommandManager;
import TinyYana.YanaCommunityBot.Service.CountMember;
import TinyYana.YanaCommunityBot.Service.DynamicVoiceChannel;
import TinyYana.YanaCommunityBot.Service.EmbedSender;
import TinyYana.YanaCommunityBot.Service.WelcomeMember;
import TinyYana.YanaCommunityBot.Util.BotUtil;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Listener extends ListenerAdapter {
    public static Logger logger = LoggerFactory.getLogger(Listener.class);
    private final CommandManager commandManager = BotCore.getCommandManager();

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        super.onReady(event);
        BotCore.getJDA().addEventListener(new CommandHandler());
        registerServiceListener();
        registerCommands();
        logger.info(BotUtil.getStringFromConfig("Bot_Startup_Message"));
    }

    private void registerServiceListener() {
        BotCore.getJDA().addEventListener(new WelcomeMember());
        BotCore.getJDA().addEventListener(new CountMember());
        BotCore.getJDA().addEventListener(new DynamicVoiceChannel());
        BotCore.getJDA().addEventListener(new EmbedSender());
    }

    private void registerCommands() {
        addCommands();
        commandManager.registerCommands(BotCore.getJDA().getGuildById(BotUtil.getStringFromConfig("Guild_ID")));
    }

    private void addCommands() {
        commandManager.addCommand(new Test());
    }
}
