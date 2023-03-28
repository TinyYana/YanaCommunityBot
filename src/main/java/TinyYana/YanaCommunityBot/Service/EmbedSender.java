package TinyYana.YanaCommunityBot.Service;

import TinyYana.YanaCommunityBot.BotCore;
import TinyYana.YanaCommunityBot.Util.BotUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class EmbedSender extends ListenerAdapter {
    // [0] channelID
    // [1] boolean
    // [2] title
    // [3] content
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);
        if (!event.getChannelType().isGuild()) return;
        TextChannel textChannel = event.getChannel().asTextChannel();
        Guild guild = BotCore.getJDA().getGuildById(BotUtil.getStringFromConfig("Guild_ID"));
        Member member = event.getMember();
        Role notificationRole = guild.getRoleById("964122410402054174");
        assert notificationRole != null;
        if (textChannel.getId().equals(BotCore.getConfig().get("Embed_Sender_No_Image"))) {
            String[] msgContent = event.getMessage().getContentRaw().split("\n", 4);
            if (msgContent.length < 3) return;
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder
                    .setTitle(msgContent[2])
                    .setDescription(msgContent[3])
                    .setColor(BotUtil.getTopRoleColor(member));
            if (guild.getNewsChannelById(msgContent[0]) != null) {
                if (msgContent[1].equalsIgnoreCase("true")) {
                    guild.getNewsChannelById(msgContent[0]).sendMessage(notificationRole.getAsMention()).queue();
                }
                guild.getNewsChannelById(msgContent[0]).sendMessageEmbeds(embedBuilder.build()).queue();
            }
            if (guild.getTextChannelById(msgContent[0]) != null) {
                if (msgContent[1].equalsIgnoreCase("true")) {
                    guild.getTextChannelById(msgContent[0]).sendMessage(notificationRole.getAsMention()).queue();
                }
                guild.getTextChannelById(msgContent[0]).sendMessageEmbeds(embedBuilder.build()).queue();
            }
            embedBuilder.clear();
            return;
        }

        if (textChannel.getId().equals(BotCore.getConfig().get("Embed_Sender_With_Image"))) {
            String[] content = event.getMessage().getContentRaw().split("\n", 5);
            if (content.length < 3) return;
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder
                    .setTitle(content[3])
                    .setDescription(content[4])
                    .setColor(BotUtil.getTopRoleColor(member))
                    .setImage(content[2]);
            if (guild.getNewsChannelById(content[0]) != null) {
                if (content[1].equalsIgnoreCase("true")) {
                    guild.getNewsChannelById(content[0]).sendMessage(notificationRole.getAsMention()).queue();
                }
                guild.getNewsChannelById(content[0]).sendMessageEmbeds(embedBuilder.build()).queue();
            }
            if (guild.getTextChannelById(content[0]) != null) {
                if (content[1].equalsIgnoreCase("true")) {
                    guild.getTextChannelById(content[0]).sendMessage(notificationRole.getAsMention()).queue();
                }
                guild.getTextChannelById(content[0]).sendMessageEmbeds(embedBuilder.build()).queue();
            }
            embedBuilder.clear();
        }
    }

}
