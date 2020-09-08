package me.mmigas.lands.commands.subcommands;

import me.mmigas.lands.commands.CMD;
import me.mmigas.lands.lands.Owner;
import me.mmigas.lands.lands.OwnersManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SeeLand extends CMD {
    private final OwnersManager ownersManager;

    public SeeLand() {
        this.ownersManager = landsPlugin.getOwnersManager();
    }

    @Override
    public void execute(CommandSender sender, String... args) {
        if (!(sender instanceof Player)) {
            return;
        }

        Player player = (Player) sender;
        Owner owner = null;
        for (Owner o : ownersManager.getOwners()) {
            if (o.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                owner = o;
            }
        }
        if (owner != null) {
            owner.showLand();
        }
    }

    @Override
    public String label() {
        return "seearea";
    }

    @Override
    public String usage() {
        return "/lands seearea";
    }
}
