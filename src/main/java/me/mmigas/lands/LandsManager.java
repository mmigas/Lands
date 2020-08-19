package me.mmigas.lands;

import me.mmigas.exceptions.ClaimedChunkException;
import me.mmigas.exceptions.InvalidBuyLandException;
import me.mmigas.exceptions.UnsellableChunkException;
import me.mmigas.utils.Pair;
import org.bukkit.Chunk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LandsManager {

    private final Map<Pair<Integer, Integer>, List<Area>> areasMap;

    public LandsManager() {
        areasMap = new HashMap<>();
    }

    public void buyLand(Owner owner, Chunk chunk) {
        if (isChunkFree(chunk)) {
            Pair<Integer, Integer> groupCoords = chunkCoordsToGroupsCoords(chunk.getX(), chunk.getZ());
            if (isNewGroup(groupCoords)) {
                List<Area> areas = new ArrayList<>();
                updateOwnersLands(owner, areas, chunk);
                areasMap.put(groupCoords, areas);
            } else {
                List<Area> areas = areasMap.get(groupCoords);
                updateOwnersLands(owner, areas, chunk);
            }
        } else {
            throw new ClaimedChunkException();
        }
    }

    private void updateOwnersLands(Owner owner, List<Area> areas, Chunk chunk) {
        List<Area> ownerAreas = getOwnerAreasNextToChunk(owner, areas, chunk);
        if (ownerAreas.size() > 1) {
            mergeAreas(owner, ownerAreas, chunk);
        } else if (ownerAreas.size() == 1) {
            expandArea(ownerAreas, chunk, areas);
        } else {
            Area area = new Area(owner, chunk);
            areas.add(area);
        }
    }

    private void mergeAreas(Owner owner, List<Area> ownerAreas, Chunk chunk) {
        Area area = new Area(owner);
        area.mergeAreas(ownerAreas, owner);
        deleteAreas(ownerAreas, chunk);
        ownerAreas.clear();
    }

    private void deleteAreas(List<Area> ownerAreas, Chunk initialChunk) {
        int x = initialChunk.getX();
        int z = initialChunk.getZ();
        boolean contains = false;
        int level = 0;
        while (!contains) {
            contains = false;
            for (int xOffset = 0; xOffset <= level; xOffset++) {
                for (int zOffset = 0; zOffset <= level; zOffset++) {
                    Pair<Integer, Integer> coordsOffset = new Pair<>(x + xOffset, z + zOffset);
                    List<Area> allAreasInGroup = getAreasInGroup(coordsOffset);
                    for (Area area : ownerAreas) {
                        if (allAreasInGroup.contains(area)) {
                            allAreasInGroup.removeAll(ownerAreas);
                            contains = true;
                            break;
                        }
                    }
                }
            }
            level++;
        }
    }

    private void expandArea(List<Area> ownerAreas, Chunk chunk, List<Area> areas) {
        Area area = ownerAreas.get(0);
        int x = chunk.getX();
        int z = chunk.getZ();
        if (area.isExpanding(x, z)) {
            area.expand(x, z);
            areas.add(area);
        } else {
            throw new InvalidBuyLandException();
        }
    }

    public void sellLand(Owner owner, Chunk chunk) {
        if (!isChunkFree(chunk)) {
            Pair<Integer, Integer> groupCoords = chunkCoordsToGroupsCoords(chunk.getX(), chunk.getZ());
            if (areasMap.containsKey(groupCoords)) {
                List<Area> areas = areasMap.get(groupCoords);
                List<Area> ownerAreas = getOwnerAreasNextToChunk(owner, areas, chunk);
                int x = chunk.getX();
                int z = chunk.getZ();
                for (Area area : ownerAreas) {
                    if (area.canRemove(x, z)) {
                        area.remove(x, z);
                    } else {
                        throw new UnsellableChunkException();
                    }
                }
            }
        }
    }

    private boolean isChunkFree(Chunk chunk) {
        Pair<Integer, Integer> groupCoords = chunkCoordsToGroupsCoords(chunk.getX(), chunk.getZ());
        List<Area> localAreas = areasMap.get(groupCoords);
        for (Area area : localAreas) {
            if (area.containsChunk(chunk.getX(), chunk.getZ()))
                return false;
        }
        return true;
    }

    private List<Area> getAreasInGroup(Pair<Integer, Integer> groupCoords) {
        for (Map.Entry<Pair<Integer, Integer>, List<Area>> entry : areasMap.entrySet()) {
            if (entry.getKey().getFirst().equals(groupCoords.getFirst()) && entry.getKey().getSecond().equals(groupCoords.getSecond())) {
                return entry.getValue();
            }
        }
        return new ArrayList<>();
    }

    private List<Area> getOwnerAreasNextToChunk(Owner owner, List<Area> areas, Chunk chunk) {
        List<Area> ownerAreas = new ArrayList<>();
        int x = chunk.getX();
        int z = chunk.getZ();
        for (Area area : areas) {
            if (area.getOwner().equals(owner) && (area.containsChunk(x + 1, z) || area.containsChunk(x - 1, z) || area.containsChunk(x, z + 1) || area.containsChunk(x, z - 1))) {
                ownerAreas.add(area);
            }
        }
        return ownerAreas;
    }

    private Pair<Integer, Integer> chunkCoordsToGroupsCoords(int x, int z) {
        return new Pair<>(x >> 4, z >> 4);
    }

    private boolean isNewGroup(Pair<Integer, Integer> coords) {
        for (Map.Entry<Pair<Integer, Integer>, List<Area>> entry : areasMap.entrySet()) {
            if (entry.getKey().getFirst().equals(coords.getFirst()) && entry.getKey().getSecond().equals(coords.getSecond())) {
                return true;
            }

        }
        return false;
    }
}
