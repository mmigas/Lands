package me.mmigas.lands.commands.subcommands;

import me.mmigas.lands.commands.CMD;
import me.mmigas.lands.exceptions.ExceptionHandler;
import me.mmigas.lands.lands.LandsManager;
import me.mmigas.lands.lands.Owner;
import me.mmigas.lands.lands.OwnersManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public class Buy extends CMD {
    private final LandsManager landsManager;
    private final OwnersManager ownersManager;

    public Buy() {
        this.landsManager = landsPlugin.getLandsManager();
        this.ownersManager = landsPlugin.getOwnersManager();
    }

    @Override
    public void execute(CommandSender sender, String... args) {
        if (!(sender instanceof Player)) {
            return;
        }
        Player player = (Player) sender;
        Owner owner;
        if (!ownersManager.containsOwner(player)) {
            owner = new Owner(player);
            ownersManager.getOwners().add(owner);
        } else {
            owner = ownersManager.getOwner(player);
        }

        if (ExceptionHandler.handle(() -> landsManager.buyLand(owner, player.getLocation().getChunk()), player)) {
            return;
        }

        player.sendMessage("CLAIMED");
    }

    @Override
    public String label() {
        return "buy";
    }

    @Override
    public String usage() {
        return "/lands buy";
    }
}
