package me.mmigas.lands;

import me.mmigas.utils.Pair;
import me.mmigas.utils.SortedMap;
import org.bukkit.Chunk;

import java.util.*;

public class Area {
    private Land land;
    //Key = x, Value = Y's set
    private final SortedMap<Integer, Set<Integer>> chunks;

    Area(Pair<Integer, Integer> coords) {
        chunks = new SortedMap<>();
        chunks.add(coords);
    }

    Area(Land land, Chunk chunk) {
        this.land = land;
        chunks = new SortedMap<>();
        chunks.add(new Pair<>(chunk.getX(), chunk.getZ()));
    }

    Area(Land land, Pair<Integer, Integer> coords) {
        this.land = land;
        chunks = new SortedMap<>();
        chunks.add(coords);
    }

    boolean isExpanding(Pair<Integer, Integer> coords) {
        int x = coords.first;
        int y = coords.second;
        Map<Integer, Set<Integer>> map = chunks.getMap();
        Set<Integer> ySet;

        if (map.containsKey(x)) {
            ySet = map.get(x);
            return ySet.contains(y - 1) || ySet.contains(y + 1) || !ySet.contains(y);
        } else if (map.containsKey(x - 1)) {
            ySet = map.get(x - 1);
            return ySet.contains(y);
        } else if (map.containsKey(x + 1)) {
            ySet = map.get(x + 1);
            return ySet.contains(y);
        }

        return false;
    }

    boolean canRemove(Pair<Integer, Integer> coords) {
        return containsChunk(coords) && (checkXAxisToRemove(coords) || checkZAxisToRemove(coords));
    }

    private boolean checkXAxisToRemove(Pair<Integer, Integer> coords) {
        int x = coords.first;
        int y = coords.second;
        Map<Integer, Set<Integer>> map = chunks.getMap();
        Set<Integer> ySetRight;
        Set<Integer> ySetLeft;

        boolean hasRight = false;
        boolean hasLeft = false;
        if (map.containsKey(x + 1)) {
            ySetRight = map.get(x + 1);
            hasRight = ySetRight.contains(y);
        }
        if (map.containsKey(x - 1)) {
            ySetLeft = map.get(x - 1);
            hasLeft = ySetLeft.contains(y);
        }
        return !hasRight && hasLeft || hasRight && !hasLeft;
    }

    private boolean checkZAxisToRemove(Pair<Integer, Integer> coords) {
        int x = coords.first;
        int z = coords.second;
        Map<Integer, Set<Integer>> map = chunks.getMap();
        if (map.containsKey(x)) {
            Set<Integer> ySet = map.get(x);
            boolean hasBottom = ySet.contains(z - 1);
            boolean hasTop = ySet.contains(z + 1);
            return !hasBottom && hasTop || hasBottom && !hasTop;
        }
        return false;
    }

    private boolean containsChunk(Pair<Integer, Integer> coords) {
        int x = coords.first;
        int z = coords.second;
        Map<Integer, Set<Integer>> map = chunks.getMap();
        if (map.containsKey(x)) {
            return map.get(x).contains(z);
        }
        return false;
    }

    void expand(Pair<Integer, Integer> coords) {
        chunks.add(coords);
    }

    void remove(Pair<Integer, Integer> coords) {
        chunks.remove(coords);
    }

    public Land getLand() {
        return land;
    }

    public SortedMap<Integer, Set<Integer>> getChunks() {
        return chunks;
    }
}
