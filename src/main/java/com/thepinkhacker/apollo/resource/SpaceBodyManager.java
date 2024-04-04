package com.thepinkhacker.apollo.resource;

import com.thepinkhacker.apollo.world.dimension.DefaultedSpaceBody;
import com.thepinkhacker.apollo.world.dimension.SpaceBody;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Optional;

public class SpaceBodyManager {
    private static final SpaceBodyManager INSTANCE = new SpaceBodyManager();
    private final HashMap<Identifier, SpaceBody> spaceBodies = new HashMap<>();

    public void addSpaceBody(Identifier identifier,SpaceBody body) {
        spaceBodies.put(identifier, body);
    }

    public void clearSpaceBodies() {
        spaceBodies.clear();
    }

    public SpaceBody getSpaceBodyOrDefault(World world) {
        return DefaultedSpaceBody.elseDefault(getSpaceBody(world));
    }

    public SpaceBody getSpaceBodyOrDefault(Identifier id) {
        return DefaultedSpaceBody.elseDefault(getSpaceBody(id));
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
