package me.mmigas.lands.listeners;

import me.mmigas.lands.LandsPlugin;
import me.mmigas.lands.lands.OwnersManager;
import me.mmigas.lands.utils.ParticlesSpawner;
import org.bukkit.Color;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {

    private final OwnersManager ownersManager;

    public PlayerListener() {
        this.ownersManager = LandsPlugin.getInstance().getOwnersManager();
    }

    @EventHandler
    public void onPlayerMoveListener(PlayerMoveEvent event) {
        if (event.getTo() != null && event.getFrom().getChunk().getX() != event.getTo().getChunk().getX()
                || event.getFrom().getChunk().getZ() != event.getTo().getChunk().getZ()) {
            if (ownersManager.getSeeChunkMap().containsKey(event.getPlayer())) {
                new ParticlesSpawner().showChunksEffect(event.getPlayer(), event.getTo().getChunk(), Color.BLUE);
            }
        }
    }
}
