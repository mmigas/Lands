package me.mmigas.lands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Owner {
    public Player player;

    public Owner(String UUID) {
        player = Bukkit.getPlayer(UUID);
    }

    public Player getPlayer() {
        return player;
    }
}
