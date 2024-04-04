package com.thepinkhacker.apollo.world.biome;

import net.minecraft.client.sound.MusicType;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.OverworldBiomeCreator;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ApolloOverworldBiomeCreator {
    public static Biome createOilDesert(
            RegistryEntryLookup<PlacedFeature> featureLookup,
            RegistryEntryLookup<ConfiguredCarver<?>> carverLookup
    ) {
        SpawnSettings.Builder spawnSettingsBuilder = new SpawnSettings.Builder();

        DefaultBiomeFeatures.addDesertMobs(spawnSettingsBuilder);

        GenerationSettings.LookupBackedBuilder lookupBackedBuilder = new GenerationSettings.LookupBackedBuilder(
                featureLookup,
                carverLookup
        );

        DefaultBiomeFeatures.addFossils(lookupBackedBuilder);
        OverworldBiomeCreator.addBasicFeatures(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultOres(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultDisks(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultFlowers(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultGrass(lookupBackedBuilder);
        DefaultBiomeFeatures.addDesertDeadBushes(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultMushrooms(lookupBackedBuilder);
        DefaultBiomeFeatures.addDesertVegetation(lookupBackedBuilder);
        DefaultBiomeFeatures.addDesertFeatures(lookupBackedBuilder);

        ApolloDefaultBiomeFeatures.addOil(lookupBackedBuilder);

        return OverworldBiomeCreator.createBiome(
                false,
                2.0f,
                0.0f,
                spawnSettingsBuilder,
                lookupBackedBuilder,
                MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_DESERT)
        );
    }
}
