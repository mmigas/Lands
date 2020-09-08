package me.mmigas.lands.commands;

import me.mmigas.lands.commands.subcommands.Buy;
import me.mmigas.lands.commands.subcommands.SeeLand;
import me.mmigas.lands.commands.subcommands.SeeChunks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class LandsCMD implements CommandExecutor, TabCompleter {
    private final List<CMD> commands = new ArrayList<>();

    public static final String PERMISSION_PREFIX = "lands.";

    public LandsCMD() {
        commands.add(new SeeChunks());
        commands.add(new Buy());
        commands.add(new SeeLand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (CMD cmd : commands) {
            if (cmd.label().equalsIgnoreCase(args[0])) {
                cmd.execute(sender, args);
                return true;
            }
        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
