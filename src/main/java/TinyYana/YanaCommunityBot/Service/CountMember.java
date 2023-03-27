package TinyYana.YanaCommunityBot.Service;

import TinyYana.YanaCommunityBot.Util.BotUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CountMember extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        super.onGuildMemberJoin(event);
        if (!BotUtil.getBooleanFromConfig("MemberCount_Enable")) return;
        TextChannel memberCountChannel = event.getGuild().getTextChannelById(BotUtil.getStringFromConfig("MemberCount_Channel_ID"));
        if (memberCountChannel == null) return;
        EmbedBuilder embedBuilder = new EmbedBuilder();
        int memberCount = event.getGuild().getMemberCount();
        embedBuilder
                .setTitle("人數增加了！")
                .setColor(Color.GREEN)
                .setDescription("當前人數：\n" + memberCount);
        memberCountChannel.sendMessageEmbeds(embedBuilder.build()).queue();
        embedBuilder.clear();
    }
}
