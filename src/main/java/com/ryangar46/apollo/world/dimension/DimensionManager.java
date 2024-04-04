package com.ryangar46.apollo.world.dimension;

import com.ryangar46.apollo.Apollo;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

public class DimensionManager {
    private static final RegistryKey<DimensionOptions> MOON_OPTIONS = RegistryKey.of(Registry.DIMENSION_KEY, new Identifier(Apollo.MOD_ID, "moon"));
    public static RegistryKey<World> MOON = RegistryKey.of(Registry.WORLD_KEY, MOON_OPTIONS.getValue());
    private static final RegistryKey<DimensionType> MOON_TYPE = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, new Identifier(Apollo.MOD_ID, "moon"));
}