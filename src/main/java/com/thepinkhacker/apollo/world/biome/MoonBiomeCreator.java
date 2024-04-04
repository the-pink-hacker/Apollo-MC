package com.thepinkhacker.apollo.world.biome;

import com.thepinkhacker.apollo.sound.ApolloSoundEvents;
import com.thepinkhacker.apollo.world.gen.feature.ApolloOrePlacedFeatures;
import net.minecraft.client.sound.MusicType;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.jetbrains.annotations.Nullable;

public class MoonBiomeCreator {
    private static final int DEFAULT_WATER_COLOR = 4159204;
    private static final int DEFAULT_WATER_FOG_COLOR = 329011;
    private static final int DEFAULT_FOG_COLOR = 0;
    private static final int DEFAULT_SKY_COLOR = 0;

    public static Biome createLunarBiome(RegistryEntryLookup<PlacedFeature> placedFeatureLookup, RegistryEntryLookup<ConfiguredCarver<?>> configuredCarverLookup) {
        GenerationSettings.LookupBackedBuilder lookupBackedBuilder = new GenerationSettings.LookupBackedBuilder(placedFeatureLookup, configuredCarverLookup);
        addDefaultOres(lookupBackedBuilder);
        return createBiome(
                false,
                0.0f,
                Biome.TemperatureModifier.FROZEN,
                0.0f,
                new SpawnSettings.Builder(),
                lookupBackedBuilder,
                MusicType.createIngameMusic(ApolloSoundEvents.MUSIC_MOON)
        );
    }


    private static void addDefaultOres(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, ApolloOrePlacedFeatures.LUNAR_ORE_IRON);
    }

    private static Biome createBiome(boolean hasPrecipitation, float temperature, Biome.TemperatureModifier temperatureModifier, float downfall, SpawnSettings.Builder spawnSettings, GenerationSettings.LookupBackedBuilder generationSettings, @Nullable MusicSound music) {
        return createBiome(hasPrecipitation, temperature, temperatureModifier, downfall, DEFAULT_WATER_COLOR, DEFAULT_WATER_FOG_COLOR, spawnSettings, generationSettings, music);
    }

    private static Biome createBiome(boolean hasPrecipitation, float temperature, Biome.TemperatureModifier temperatureModifier, float downfall, int waterColor, int waterFogColor, SpawnSettings.Builder spawnSettings, GenerationSettings.LookupBackedBuilder generationSettings, @Nullable MusicSound music) {
        return new Biome.Builder()
                .precipitation(hasPrecipitation)
                .temperature(temperature)
                .temperatureModifier(temperatureModifier)
                .downfall(downfall)
                .effects(new BiomeEffects.Builder()
                        .waterColor(waterColor)
                        .waterFogColor(waterFogColor)
                        .fogColor(DEFAULT_FOG_COLOR)
                        .skyColor(DEFAULT_SKY_COLOR)
                        .moodSound(BiomeMoodSound.CAVE)
                        .music(music)
                        .build()
                )
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }
}
