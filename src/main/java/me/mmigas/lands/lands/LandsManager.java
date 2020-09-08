package me.mmigas.lands.lands;

import me.mmigas.lands.exceptions.ClaimedChunkException;
import me.mmigas.lands.exceptions.InvalidBuyLandException;
import me.mmigas.lands.exceptions.UnsellableChunkException;
import me.mmigas.lands.utils.Pair;
import org.bukkit.Chunk;

import java.util.*;

public class LandsManager {

    private final Map<Pair<Integer, Integer>, List<Land>> landsMap;

    public LandsManager() {
        landsMap = new HashMap<>();
    }

    public void buyLand(Owner owner, Chunk chunk) {
        if (isChunkFree(chunk)) {
            Pair<Integer, Integer> groupCoords = chunkCoordsToGroupsCoords(chunk.getX(), chunk.getZ());
            if (isNewGroup(groupCoords)) {
                List<Land> lands = new ArrayList<>();
                updateOwnersLands(owner, lands, chunk);
                landsMap.put(groupCoords, lands);
            } else {
                List<Land> lands = getLandsInGroup(groupCoords);
                updateOwnersLands(owner, lands, chunk);
            }
        } else {
            throw new ClaimedChunkException();
        }
    }

    private void updateOwnersLands(Owner owner, List<Land> lands, Chunk chunk) {
        if (owner.getLand() != null) {
            if (owner.getLand().isExpanding(chunk.getX(), chunk.getZ())) {
                int x = chunk.getX();
                int z = chunk.getZ();
                owner.getLand().expand(x, z);
            } else {
                throw new InvalidBuyLandException();
            }
        } else {
            Land land = new Land(owner, chunk);
            lands.add(land);
            owner.setLand(land);
        }

    }

    private void deleteLands(List<Land> ownerLands, Chunk initialChunk) {
        int x = initialChunk.getX();
        int z = initialChunk.getZ();
        boolean contains = true;
        int level = 0;
        while (contains) {
            contains = false;
            for (int xOffset = 0; xOffset <= level; xOffset++) {
                for (int zOffset = 0; zOffset <= level; zOffset++) {
                    Pair<Integer, Integer> coordsOffset = new Pair<>(x + xOffset, z + zOffset);
                    List<Land> allLandsInGroup = getLandsInGroup(coordsOffset);
                    for (Land land : ownerLands) {
                        if (allLandsInGroup.contains(land)) {
                            allLandsInGroup.removeAll(ownerLands);
                            contains = true;
                            break;
                        }
                    }
                }
            }
            level++;
        }
    }

    public void sellLand(Owner owner, Chunk chunk) {
        if (!isChunkFree(chunk)) {
            Pair<Integer, Integer> groupCoords = chunkCoordsToGroupsCoords(chunk.getX(), chunk.getZ());
            if (landsMap.containsKey(groupCoords)) {
                List<Land> lands = landsMap.get(groupCoords);
                //Land ownerLand = getOwnerLandsNextToChunk(owner, lands, chunk);
                Land ownerLand = owner.getLand();
                int x = chunk.getX();
                int z = chunk.getZ();
                if (ownerLand.canRemove(x, z)) {
                    ownerLand.remove(x, z);
                } else {
                    throw new UnsellableChunkException();
                }
            }
        }
    }

    private boolean isChunkFree(Chunk chunk) {
        Pair<Integer, Integer> groupCoords = chunkCoordsToGroupsCoords(chunk.getX(), chunk.getZ());
        List<Land> localLands = landsMap.get(groupCoords);
        if (localLands == null) {
            return true;
        } else {
            for (Land land : localLands) {
                if (land.containsChunk(chunk.getX(), chunk.getZ()))
                    return false;
            }
            return true;
        }
    }

    private List<Land> getLandsInGroup(Pair<Integer, Integer> groupCoords) {
        for (Map.Entry<Pair<Integer, Integer>, List<Land>> entry : landsMap.entrySet()) {
            if (entry.getKey().getFirst().equals(groupCoords.getFirst()) && entry.getKey().getSecond().equals(groupCoords.getSecond())) {
                return entry.getValue();
            }
        }
        return new ArrayList<>();
    }

    private Land getOwnerLandsNextToChunk(Owner owner, List<Land> lands, Chunk chunk) {
        int x = chunk.getX();
        int z = chunk.getZ();
        for (Land land : lands) {
            if (land.getOwner().equals(owner) && (land.containsChunk(x + 1, z) || land.containsChunk(x - 1, z) || land.containsChunk(x, z + 1) || land.containsChunk(x, z - 1))) {
                return land;
            }
        }
        return null;
    }

    private Pair<Integer, Integer> chunkCoordsToGroupsCoords(int x, int z) {
        return new Pair<>(x >> 4, z >> 4);
    }

    private boolean isNewGroup(Pair<Integer, Integer> coords) {
        for (Map.Entry<Pair<Integer, Integer>, List<Land>> entry : landsMap.entrySet()) {
            if (entry.getKey().getFirst().equals(coords.getFirst()) && entry.getKey().getSecond().equals(coords.getSecond())) {
                return false;
            }

        }
        return true;
    }
}