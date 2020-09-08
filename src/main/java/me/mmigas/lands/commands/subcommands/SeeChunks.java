package me.mmigas.lands.commands.subcommands;

import me.mmigas.lands.LandsPlugin;
import me.mmigas.lands.commands.CMD;
import me.mmigas.lands.lands.OwnersManager;
import me.mmigas.lands.utils.ParticlesSpawner;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SeeChunks extends CMD {
    private final OwnersManager ownersManager;

    public SeeChunks() {
        ownersManager = LandsPlugin.getInstance().getOwnersManager();
    }

    @Override
    public void execute(CommandSender sender, String... args) {
        if (!(sender instanceof Player)) {
            return;
        }

        Bukkit.getLogger().info("HELLO");

        Player player = (Player) sender;
        if (ownersManager.getSeeChunkMap().containsKey(player)) {
            Bukkit.getScheduler().cancelTask(ownersManager.getSeeChunkMap().get(player));
            ownersManager.getSeeChunkMap().remove(player);
        } else {
            new ParticlesSpawner().showChunksEffect(player, player.getLocation().getChunk(), Color.BLUE);
        }
    }

    @Override
    public String label() {
        return "seechunks";
    }

    @Override
    public String usage() {
        return "/lands seechunks";
    }
}
