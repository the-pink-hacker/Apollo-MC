package com.thepinkhacker.apollo.resource;

import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Optional;

public class SpaceBodyManager {
    private static final SpaceBodyManager INSTANCE = new SpaceBodyManager();
    private static final SpaceBody DEFAULT = SpaceBody.defaultSpaceBody();
    private final HashMap<Identifier, SpaceBody> spaceBodies = new HashMap<>();

    public void addSpaceBody(Identifier identifier,SpaceBody body) {
        spaceBodies.put(identifier, body);
    }

    public void clearSpaceBodies() {
        spaceBodies.clear();
    }

    public SpaceBody getSpaceBodyOrDefault(World world) {
        return getSpaceBody(world).orElse(DEFAULT);
    }

    public SpaceBody getSpaceBodyOrDefault(Identifier id) {
        return getSpaceBody(id).orElse(DEFAULT);
    }

    public Optional<SpaceBody> getSpaceBody(World world) {
        return getSpaceBody(world.getDimensionKey().getValue());
    }

    public Optional<SpaceBody> getSpaceBody(Identifier id) {
        return Optional.ofNullable(spaceBodies.get(id));
    }

    public static SpaceBodyManager getInstance() {
        return INSTANCE;
    }
}
