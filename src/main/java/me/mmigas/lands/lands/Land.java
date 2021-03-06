package me.mmigas.lands.lands;

import me.mmigas.lands.utils.SortedMap;
import org.bukkit.Chunk;

import java.util.Map;
import java.util.Set;

public class Land {
    private final Owner owner;
    //Key = x, Value = Y's set
    private final SortedMap<Integer, Set<Integer>> chunks;

    Land(Owner owner) {
        this.owner = owner;
        chunks = new SortedMap<>();
    }

    Land(Owner owner, int x, int z) {
        this.owner = owner;
        chunks = new SortedMap<>();
        chunks.add(x, z);
    }

    Land(Owner owner, Chunk chunk) {
        this.owner = owner;
        chunks = new SortedMap<>();
        chunks.add(chunk.getX(), chunk.getZ());
    }

    boolean isExpanding(int x, int z) {
        Map<Integer, Set<Integer>> map = chunks.getMap();
        Set<Integer> ySet;

        if (map.containsKey(x)) {
            ySet = map.get(x);
            return ySet.contains(z - 1) || ySet.contains(z + 1) || !ySet.contains(z);
        } else if (map.containsKey(x - 1)) {
            ySet = map.get(x - 1);
            return ySet.contains(z);
        } else if (map.containsKey(x + 1)) {
            ySet = map.get(x + 1);
            return ySet.contains(z);
        }
        return false;
    }

    boolean canRemove(int x, int z) {
        return containsChunk(x, z) && (checkXAxisToRemove(x, z) || checkZAxisToRemove(x, z));
    }

    private boolean checkXAxisToRemove(int x, int z) {
        Map<Integer, Set<Integer>> map = chunks.getMap();
        Set<Integer> ySetRight;
        Set<Integer> ySetLeft;

        boolean hasRight = false;
        boolean hasLeft = false;
        if (map.containsKey(x + 1)) {
            ySetRight = map.get(x + 1);
            hasRight = ySetRight.contains(z);
        }
        if (map.containsKey(x - 1)) {
            ySetLeft = map.get(x - 1);
            hasLeft = ySetLeft.contains(z);
        }
        return !hasRight && hasLeft || hasRight && !hasLeft;
    }

    private boolean checkZAxisToRemove(int x, int z) {
        Map<Integer, Set<Integer>> map = chunks.getMap();
        if (map.containsKey(x)) {
            Set<Integer> ySet = map.get(x);
            boolean hasBottom = ySet.contains(z - 1);
            boolean hasTop = ySet.contains(z + 1);
            return !hasBottom && hasTop || hasBottom && !hasTop;
        }
        return false;
    }

    boolean containsChunk(int x, int z) {
        Map<Integer, Set<Integer>> map = chunks.getMap();
        if (map.containsKey(x)) {
            return map.get(x).contains(z);
        }
        return false;
    }

    void expand(int x, int z) {
        chunks.add(x, z);
    }

    void remove(int x, int z) {
        chunks.remove(x, z);
    }

    public Owner getOwner() {
        return owner;
    }

    public SortedMap<Integer, Set<Integer>> getChunks() {
        return chunks;
    }
}
