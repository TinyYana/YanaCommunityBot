package TinyYana.YanaCommunityBot.Service;

import TinyYana.YanaCommunityBot.BotCore;
import TinyYana.YanaCommunityBot.Listener;
import TinyYana.YanaCommunityBot.Util.BotUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class DynamicVoiceChannel extends ListenerAdapter {
    //離開語音不會自動刪除頻道
    //進入頻道不會觸發，移動才會
    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        super.onGuildVoiceUpdate(event);
        if (!BotUtil.getBooleanFromConfig("Dynamic_Voice_Channel_Enable")) return;
        Member member = event.getMember();
        VoiceChannel triggerChannel = event.getJDA().getVoiceChannelById(Long.parseLong(BotCore.getConfig().get("Dynamic_Voice_Channel_ID").toString()));

        //獲得更新前後的語音頻道
        AudioChannel oldChannel = event.getOldValue();
        AudioChannel newChannel = event.getNewValue();

        //判斷用戶是否加入了觸發頻道
        if (oldChannel == null && newChannel == triggerChannel) {
            System.out.println("true");
            try {
                createChannelAndMoveMember(
                        triggerChannel.getParentCategory(),
                        member.getEffectiveName() + " 的語音區",
                        event.getGuild(),
                        member
                );
            } catch (Exception e) {
                Listener.logger.info("創建語音被觸發");
            }
        }

        //判斷用戶是否離開了觸發頻道
        if (oldChannel == triggerChannel && newChannel == null) return;
        //判斷用戶是否離開了動態語音頻道
        if (oldChannel != null && newChannel == null && oldChannel.getParentCategory().getChannels().contains(triggerChannel)) {
            if (!event.getGuild().equals(BotCore.getJDA().getGuildById(BotUtil.getStringFromConfig("Guild_ID")))) return;
            if (oldChannel.getMembers().isEmpty()) {
                try {
                    oldChannel.delete().queue();
                } catch (Exception e) {
                    Listener.logger.info("delete語音被觸發");
                }
            }
        }
    }

    private void createChannelAndMoveMember(Category category, String channelName, Guild guild, Member member) {
        if (category == null) return;
        try {
            category
                    .createVoiceChannel(channelName)
                    .queue(
                            r -> {
                                guild.moveVoiceMember(member, guild.getVoiceChannelById(r.getId())).queue();
                                r.upsertPermissionOverride(member)
                                        .grant(Permission.MANAGE_CHANNEL)
                                        .grant(Permission.MANAGE_PERMISSIONS)
                                        .grant(Permission.ALL_VOICE_PERMISSIONS)
                                        .queue();
                            }
                    );
        } catch (Exception e) {
            Listener.logger.info("創建語音被觸發");
        }
    }
}
