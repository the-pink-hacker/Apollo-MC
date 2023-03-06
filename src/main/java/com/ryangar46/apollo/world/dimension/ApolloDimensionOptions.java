package com.ryangar46.apollo.world.dimension;

import com.ryangar46.apollo.Apollo;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionOptions;

public class ApolloDimensionOptions {
    public static final RegistryKey<DimensionOptions> MOON = RegistryKey.of(RegistryKeys.DIMENSION, new Identifier(Apollo.MOD_ID, "moon"));
}
