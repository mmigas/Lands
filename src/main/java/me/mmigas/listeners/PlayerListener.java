package me.mmigas.listeners;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener {

    @EventHandler
    public void onPlayerMoveListener(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Chunk chunk = player.getLocation().getChunk();
        World world = chunk.getWorld();
        Location lowerRight = chunk.getBlock(0, 0, 0).getLocation();
        Location lowerLeft = chunk.getBlock(15, 0, 0).getLocation();
        Location upperRight = chunk.getBlock(0, 0, 15).getLocation();
        Location upperLeft = chunk.getBlock(15, 0, 15).getLocation();

        for (int i = world.getHighestBlockYAt(lowerRight); i < 255; i++) {
            player.spawnParticle(Particle.REDSTONE, lowerRight.add(0, i, 0), 1);
        }

        for (int i = world.getHighestBlockYAt(lowerLeft); i < 255; i++) {
            player.spawnParticle(Particle.REDSTONE, lowerLeft.add(0, i, 0), 1);
        }

        for (int i = world.getHighestBlockYAt(upperRight); i < 255; i++) {
            player.spawnParticle(Particle.REDSTONE, upperRight.add(0, i, 0), 1);
        }

        for (int i = world.getHighestBlockYAt(upperLeft); i < 255; i++) {
            player.spawnParticle(Particle.REDSTONE, upperLeft.add(0, i, 0), 1);
        }
    }
}
