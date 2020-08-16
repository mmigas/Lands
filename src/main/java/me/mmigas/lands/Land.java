package me.mmigas.lands;

import me.mmigas.utils.Pair;
import org.bukkit.Chunk;

import java.util.ArrayList;
import java.util.List;

public class Land {
    private final LandsManager landsManager;

    private final List<Area> areas;

    public Land(LandsManager landsManager) {
        this.landsManager = landsManager;
        areas = new ArrayList<>();
    }


    private boolean canCreateLand() {
        return areas.size() > 3;
    }

    public int buyLand(Chunk chunk) {
        Pair<Integer, Integer> coords = new Pair<>(chunk.getX(), chunk.getZ());
        for (Area area : areas) {

            if (area.isExpanding(coords)) {
                area.expand(coords);
                return 0;
            }
        }
        if (canCreateLand()) {
            areas.add(new Area(this, chunk));
            return 0;
        } else {
            return -1;
        }
    }
}