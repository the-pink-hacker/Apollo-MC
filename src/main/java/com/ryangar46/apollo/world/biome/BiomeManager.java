package com.ryangar46.apollo.world.biome;

import com.ryangar46.apollo.Apollo;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class BiomeManager {
    public static void register() {
        Apollo.LOGGER.info("Registering biomes");
        registerBiome("lunar_highlands");
        registerBiome("lunar_maria");
        registerBiome("lunar_ditches");
    }

    private static void registerBiome(String id) {
        RegistryKey.of(Registry.BIOME_KEY, new Identifier(Apollo.MOD_ID, id));
    }
}
