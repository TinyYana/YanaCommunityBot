package TinyYana.YanaCommunityBot.Commands;

import TinyYana.YanaCommunityBot.Interfaces.Command;
import TinyYana.YanaCommunityBot.Util.BotUtil;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class Test implements Command {
    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getDescription() {
        return "開發人員測試用指令";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (!checkPermission(event)) {
            event.reply(BotUtil.getStringFromConfig("Permission_Message")).setEphemeral(true).queue();
            return;
        }
        event.reply("運作正常").setEphemeral(true).queue();
    }

    @Override
    public boolean checkPermission(SlashCommandInteractionEvent event) {
        return BotUtil.containRole(event.getMember(), event.getGuild().getRoleById(BotUtil.getStringFromConfig("Guild_Moderator_Role_ID")));
    }
}
