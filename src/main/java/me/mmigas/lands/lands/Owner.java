package me.mmigas.lands.lands;

import me.mmigas.lands.utils.ParticlesSpawner;
import org.bukkit.Color;
import org.bukkit.entity.Player;

public class Owner {
    public Player player;
    private String ID;

    private Land land;

    public Owner(Player player) {
        this.player = player;
    }

    public Owner(String ID) {
        this.ID = ID;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public void removeLand() {
        this.land = null;
    }

    public Player getPlayer() {
        return player;
    }

    public Land getLand() {
        return land;
    }

    public void showLand() {
        ParticlesSpawner ps = new ParticlesSpawner();
        ps.showLandEffect(getPlayer(), Color.YELLOW);
    }
}

