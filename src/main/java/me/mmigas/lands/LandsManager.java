package me.mmigas.lands;

import java.util.HashMap;
import java.util.Map;

public class LandsManager {

    private final Map<Owner, Land> lands;

    public LandsManager() {
        lands = new HashMap<>();
    }


    public Map<Owner, Land> getLands() {
        return lands;
    }
}
