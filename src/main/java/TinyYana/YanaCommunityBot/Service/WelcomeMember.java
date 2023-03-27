package TinyYana.YanaCommunityBot.Service;

import TinyYana.YanaCommunityBot.BotCore;
import TinyYana.YanaCommunityBot.Util.BotUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class WelcomeMember extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        super.onGuildMemberJoin(event);
        if (!BotUtil.getBooleanFromConfig("Welcome_Message_Enable")) return;
        if (BotUtil.notHuman(event.getUser())) return;
        Member member = event.getMember();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        Guild guild = BotCore.getJDA().getGuildById(BotUtil.getStringFromConfig("Guild_ID"));
        if (guild == null) return;
        TextChannel welcomeChannel = guild.getTextChannelById(BotUtil.getStringFromConfig("Welcome_Message_Channel_ID"));
        if (welcomeChannel == null) return;

        embedBuilder
                .setTitle(BotUtil.getStringFromConfig("Welcome_Message_Title"))
                .setDescription("""
                        這裡是一個可以讓大家聊，「ACG相關話題」的社群，
                        :thumbsup: 社群推薦文作者也會不定時推薦好看的動畫作品給大家。:speaking_head:
                        當然你也可以純閒聊，與這裡的大家一起閒話家常，相信你可以在這邊認識更多新朋友。
                        """)
                .setColor(Color.YELLOW)
                .setThumbnail(event.getUser().getAvatarUrl());

        welcomeChannel.sendMessage(member.getAsMention()).queue();
        welcomeChannel.sendMessageEmbeds(embedBuilder.build()).queue();

        try {
            BotCore.getJDA().openPrivateChannelById(member.getId()).queue(
                    privateChannel -> {
                        EmbedBuilder embed = new EmbedBuilder();
                        embed
                                .setTitle(BotUtil.getStringFromConfig("Welcome_Message_Title"))
                                .setDescription("""
                        這裡是一個可以讓大家聊，「ACG相關話題」的社群，
                        :thumbsup: 社群推薦文作者也會不定時推薦好看的動畫作品給大家。:speaking_head:
                        當然你也可以純閒聊，與這裡的大家一起閒話家常，相信你可以在這邊認識更多新朋友。
                        """)
                                .setColor(Color.YELLOW)
                                .setThumbnail(event.getUser().getAvatarUrl());
                        privateChannel.sendMessageEmbeds(embed.build()).queue();
                        privateChannel.sendMessage("【此訊息為系統訊息，如有事情請找社群管理】").queue();
                        embed.clear();
                    }
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        guild.addRoleToMember(member, guild.getRoleById("859107233702215690")).queue();
        guild.addRoleToMember(member, guild.getRoleById("872787716549529633")).queue();

        embedBuilder.clear();
    }
}
