package com.thepinkhacker.apollo.world.biome;

import com.thepinkhacker.apollo.Apollo;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;

public class ApolloBiomeKeys {
    public static final RegistryKey<Biome> LUNAR_HIGHLANDS = registerBiome("lunar_highlands");
    public static final RegistryKey<Biome> LUNAR_MARIA = registerBiome("lunar_maria");
    public static final RegistryKey<Biome> LUNAR_DITCHES = registerBiome("lunar_ditches");
    public static final RegistryKey<Biome> OIL_DESERT = registerBiome("oil_desert");

    public static void register() {
        Apollo.LOGGER.info("Registering biomes");
    }

    public static RegistryKey<Biome> registerBiome(String id) {
        return RegistryKey.of(RegistryKeys.BIOME, Apollo.getIdentifier(id));
    }
}
