package com.ryangar46.apollo.world.spawner;

import java.util.ArrayList;
import java.util.List;

public class GenericSpawnerManager {
    public static final List<GenericSpawner> spawners = new ArrayList<>();

    public static void register() {
        addSpawner(new MeteoriteSpawner());
    }

    private static void addSpawner(GenericSpawner spawner) {
        spawners.add(spawner);
    }
}
