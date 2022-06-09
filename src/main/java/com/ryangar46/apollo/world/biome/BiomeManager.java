package com.ryangar46.apollo.world.biome;

import com.ryangar46.apollo.Apollo;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class BiomeManager {
    public static final RegistryKey<Biome> LUNAR_HIGHLANDS = registerBiome("lunar_highlands");
    public static final RegistryKey<Biome> LUNAR_MARIA = registerBiome("lunar_maria");
    public static final RegistryKey<Biome> LUNAR_DITCHES = registerBiome("lunar_ditches");
    public static final RegistryKey<Biome> OIL_DESERT = registerBiome("oil_desert");

    public static void register() {
        Apollo.LOGGER.info("Registering biomes");
        registerBuiltinBiome(OIL_DESERT, ApolloOverworldBiomeCreator.oilFields());
    }

    private static RegistryKey<Biome> registerBiome(String id) {
        return RegistryKey.of(Registry.BIOME_KEY, new Identifier(Apollo.MOD_ID, id));
    }

    private static void registerBuiltinBiome(RegistryKey<Biome> key, Biome biome) {
        BuiltinRegistries.add(BuiltinRegistries.BIOME, key, biome);
    }
}
