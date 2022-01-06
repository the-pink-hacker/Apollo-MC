package com.ryangar46.apollo.world.dimension;

import com.ryangar46.apollo.Apollo;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

public class DimensionManager {
    public static void register() {
        Apollo.LOGGER.info("Registering dimensions");
        registerDimension("moon");
    }

    private static void registerDimension(String id) {
        RegistryKey.of(Registry.WORLD_KEY, RegistryKey.of(Registry.DIMENSION_KEY,
                new Identifier(Apollo.MOD_ID, id)).getValue());
        RegistryKey.of(Registry.DIMENSION_TYPE_KEY, new Identifier(Apollo.MOD_ID, id));
    }
}