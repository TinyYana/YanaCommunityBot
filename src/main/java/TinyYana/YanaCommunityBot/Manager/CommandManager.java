package TinyYana.YanaCommunityBot.Manager;

import TinyYana.YanaCommunityBot.Interfaces.Command;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.HashMap;

public class CommandManager {
    private final HashMap<String, Command> commands;

    public CommandManager() {
        this.commands = new HashMap<>();
    }

    public void registerCommands(Guild guild) { // Register global commands for JDA instance
        for (Command command : commands.values()) { // Loop through all commands
            if (command.getOptions() != null) {
                guild.upsertCommand(command.getName(), command.getDescription())
                        .addOptions(command.getOptions())
                        .queue(); // Queue it to be registered
            }
            guild.upsertCommand(command.getName(), command.getDescription()).queue();
        }
    }

    public void handle(SlashCommandInteractionEvent event) { // Handle slash command events
        String name = event.getName(); // Get the name of the invoked command

        if (commands.containsKey(name)) { // Check if we have a matching command
            Command command = commands.get(name); // Get the corresponding command object
            command.execute(event); // Execute the command logic
        } else {
            event.reply("未知的指令").setEphemeral(true).queue(); // Reply with an error message
        }
    }

    public void addCommand(Command command) { // Add a new command to the manager
        commands.put(command.getName(), command); // Put the name and object in the map
    }
}
