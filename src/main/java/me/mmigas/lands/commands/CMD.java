package me.mmigas.lands.commands;

import me.mmigas.lands.LandsPlugin;
import org.bukkit.command.CommandSender;

public abstract class CMD {
    public LandsPlugin landsPlugin;

    public CMD() {
        this.landsPlugin = LandsPlugin.getInstance();
    }

    public abstract void execute(CommandSender sender, String... args);

    public boolean hasPreRequisites(CommandSender sender) {
        return sender.hasPermission(permission());
    }

    public abstract String label();

    public abstract String usage();

    public String permission() {
        return LandsCMD.PERMISSION_PREFIX + label();
    }
}
