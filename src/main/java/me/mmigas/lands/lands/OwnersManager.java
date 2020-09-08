package me.mmigas.lands.lands;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OwnersManager {
    private final List<Owner> owners;
    // private final LandsManager landsManager;

    private final Map<Player, Integer> seeChunkMap;
    private final Map<Player, Integer> seeAreaMap;

    public OwnersManager() {
        //landsManager = LandsPlugin.getInstance().getLandsManager();
        owners = new ArrayList<>();
        seeChunkMap = new HashMap<>();
        seeAreaMap = new HashMap<>();
    }

    public Map<Player, Integer> getSeeChunkMap() {
        return seeChunkMap;
    }

    public Map<Player, Integer> getSeeLandMap() {
        return seeAreaMap;
    }

    public boolean containsOwner(Player player) {
        for (Owner owner : owners) {
            if (owner.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public Owner getOwner(Player player) {
        for (Owner owner : owners) {
            if (owner.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                return owner;
            }
        }
        return null;
    }

    public List<Owner> getOwners() {
        return owners;
    }
}
