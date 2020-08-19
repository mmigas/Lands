package me.mmigas.lands;

import org.bukkit.entity.Player;

public class Owner {
    public Player player;
    private String ID;

    private int areasOwned = 0;

    public Owner(Player player) {
        this.player = player;
    }

    public Owner(String ID) {
        this.ID = ID;
    }

    public Player getPlayer() {
        return player;
    }

    public int getAreasOwned() {
        return areasOwned;
    }

    public void addAreasOwned() {
        areasOwned++;
    }

    public void removeAreasOwner() {
        areasOwned--;
    }

    public void removeAreasOwner(int quantity) {
        areasOwned = -quantity;
    }
}

