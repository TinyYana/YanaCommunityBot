package TinyYana.YanaCommunityBot.Util;

import TinyYana.YanaCommunityBot.BotCore;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class BotUtil {
    public static boolean notHuman(User user) {
        return user.isBot() || user.isSystem();
    }

    public static String getStringFromConfig(String path) {
        return BotCore.getConfig().get(path).toString();
    }

    public static Boolean getBooleanFromConfig(String path) {
        return Boolean.parseBoolean(getStringFromConfig(path));
    }

    public static Color getTopRoleColor(@NotNull Member member) {
        return member.getRoles().get(0).getColor();
    }

    public static boolean containRole(@NotNull Member member, Role role) {
        return member.getRoles().contains(role);
    }

}
