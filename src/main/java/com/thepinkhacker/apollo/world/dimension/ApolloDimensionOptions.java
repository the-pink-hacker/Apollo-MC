package com.thepinkhacker.apollo.world.dimension;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionOptions;

public class ApolloDimensionOptions {
    public static final RegistryKey<DimensionOptions> MOON = of(ApolloDimensionTypes.MOON_ID);

    private static RegistryKey<DimensionOptions> of(Identifier id) {
        return RegistryKey.of(RegistryKeys.DIMENSION, id);
    }
}
