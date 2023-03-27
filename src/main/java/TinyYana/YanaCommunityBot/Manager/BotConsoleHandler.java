package TinyYana.YanaCommunityBot.Manager;

import net.dv8tion.jda.api.JDA;

import java.util.Scanner;

public class BotConsoleHandler {
    public static void consoleHandler(JDA jda) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                jda.shutdown();
                System.exit(0);
            }
        }
    }
}
