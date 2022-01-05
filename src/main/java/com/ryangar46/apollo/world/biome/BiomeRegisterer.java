package com.ryangar46.apollo.world.biome;

import com.ryangar46.apollo.Apollo;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.dimension.DimensionOptions;

public class BiomeRegisterer {
    private static final RegistryKey<Biome> BIOME_KEY = RegistryKey.of(Registry.BIOME_KEY,
            new Identifier(Apollo.MOD_ID, "lunar_highlands"));

    public static void register() {
        Apollo.LOGGER.info("Registering biomes");
    }
}
