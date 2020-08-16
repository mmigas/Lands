package me.mmigas.utils;

import java.util.*;

public class SortedMap<E extends Comparable<E>, T extends Set<E>> {

    private final Map<E, T> map;

    public SortedMap() {
        map = new TreeMap<>();
    }

    public SortedMap(E e1, E e2) {
        map = new TreeMap<>();
        add(new Pair<>(e1, e2));
    }

    public void add(Pair<E, E> values) {
        E first = values.first;
        E second = values.second;
        T tSet;
        tSet = map.containsKey(first) ? map.get(first) : (T) new TreeSet<E>();
        tSet.add(second);
        map.put(first, tSet);
    }

    public void remove(Pair<E, E> values) {
        E first = values.first;
        E second = values.second;
        T tSet = map.get(first);
        if (tSet.size() == 1) {
            map.remove(first);
        } else {
            tSet.remove(second);
        }
    }

    public Map<E, T> getMap() {
        return map;
    }
}
