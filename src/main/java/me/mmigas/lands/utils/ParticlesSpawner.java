package me.mmigas.lands.utils;

import me.mmigas.lands.LandsPlugin;
import me.mmigas.lands.lands.Land;
import me.mmigas.lands.lands.Owner;
import me.mmigas.lands.lands.OwnersManager;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.*;

public class ParticlesSpawner {
    private final LandsPlugin plugin;
    private final OwnersManager ownersManager;

    public ParticlesSpawner() {
        this.plugin = LandsPlugin.getInstance();
        ownersManager = plugin.getOwnersManager();
    }

    public void showChunksEffect(Player player, Chunk chunk, Color color) {
        if (ownersManager.getSeeChunkMap().containsKey(player) && Bukkit.getScheduler().isQueued(ownersManager.getSeeChunkMap().get(player))) {
            Bukkit.getScheduler().cancelTask(ownersManager.getSeeChunkMap().get(player));
        }
        int task = showChunk(player, chunk, color);
        ownersManager.getSeeLandMap().put(player, task);
    }

    public void showLandEffect(Player player, Color color) {
        if (ownersManager.getSeeLandMap().containsKey(player) && Bukkit.getScheduler().isQueued(ownersManager.getSeeLandMap().get(player))) {
            Bukkit.getScheduler().cancelTask(ownersManager.getSeeLandMap().get(player));
        }
        int task = showLand(player, color);
        ownersManager.getSeeLandMap().put(player, task);
    }

    private int showLand(Player player, Color color) {
        Owner owner = ownersManager.getOwner(player);
        Land land = owner.getLand();
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Map.Entry<Integer, Set<Integer>> entry : land.getChunks().getMap().entrySet()) {
                for (Integer integer : entry.getValue()) {
                    showUnreaptingChunk(player, entry.getKey(), integer, color);
                }
            }
        }, 0L, 10L);
    }

    private void showChunk(Player player, int x, int z, Color color) {
        World world = player.getWorld();
        Chunk chunk = world.getChunkAt(x, z);
        showChunk(player, chunk, color);
    }

    private int showChunk(Player player, Chunk chunk, Color color) {
        List<Location> chunkCorners = new ArrayList<>();
        chunkCorners.add(chunk.getBlock(0, 0, 0).getLocation());
        chunkCorners.add(chunk.getBlock(15, 0, 0).getLocation().add(1, 0, 0));
        chunkCorners.add(chunk.getBlock(0, 0, 15).getLocation().add(0, 0, 1));
        chunkCorners.add(chunk.getBlock(15, 0, 15).getLocation().add(1, 0, 1));
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Location location : chunkCorners) {
                spawnParticles(player, location, color);
            }
        }, 0L, 10L);
    }

    private void showUnreaptingChunk(Player player, int x, int z, Color color) {
        World world = player.getWorld();
        Chunk chunk = world.getChunkAt(x, z);
        List<Location> chunkCorners = new ArrayList<>();
        chunkCorners.add(chunk.getBlock(0, 0, 0).getLocation());
        chunkCorners.add(chunk.getBlock(15, 0, 0).getLocation().add(1, 0, 0));
        chunkCorners.add(chunk.getBlock(0, 0, 15).getLocation().add(0, 0, 1));
        chunkCorners.add(chunk.getBlock(15, 0, 15).getLocation().add(1, 0, 1));
        for (Location location : chunkCorners) {
            spawnParticles(player, location, color);
        }
    }

    private void spawnParticles(Player player, Location loc, Color color) {
        Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1);
        Location location = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        int y = location.getWorld().getHighestBlockYAt(location);
        player.spawnParticle(Particle.REDSTONE, location.add(0, y, 0), 1, dustOptions);
        for (int i = y; i < 100; i++) {
            player.spawnParticle(Particle.REDSTONE, location.add(0, 1, 0), 1, dustOptions);
        }
    }
}
