package com.thepinkhacker.apollo.world.biome;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ApolloBuiltinBiomes {
    public static void bootstrap(Registerable<Biome> registerable) {
        RegistryEntryLookup<PlacedFeature> placedFeatureLookup = registerable.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        RegistryEntryLookup<ConfiguredCarver<?>> configuredCarverLookup = registerable.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);

        registerable.register(ApolloBiomeKeys.LUNAR_DITCHES, MoonBiomeCreator.createLunarBiome(placedFeatureLookup, configuredCarverLookup));
        registerable.register(ApolloBiomeKeys.LUNAR_HIGHLANDS, MoonBiomeCreator.createLunarBiome(placedFeatureLookup, configuredCarverLookup));
        registerable.register(ApolloBiomeKeys.LUNAR_MARIA, MoonBiomeCreator.createLunarBiome(placedFeatureLookup, configuredCarverLookup));

        registerable.register(ApolloBiomeKeys.OIL_DESERT, ApolloOverworldBiomeCreator.createOilDesert(placedFeatureLookup, configuredCarverLookup));
    }
}
