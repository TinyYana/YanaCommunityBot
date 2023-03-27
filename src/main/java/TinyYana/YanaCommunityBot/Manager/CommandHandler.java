package TinyYana.YanaCommunityBot.Manager;

import TinyYana.YanaCommunityBot.BotCore;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandHandler extends ListenerAdapter {
    private final CommandManager commandManager = BotCore.getCommandManager();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
        commandManager.handle(event);
    }
}
