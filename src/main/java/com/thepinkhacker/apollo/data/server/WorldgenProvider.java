package com.thepinkhacker.apollo.data.server;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.world.biome.ApolloBiomeKeys;
import com.thepinkhacker.apollo.world.dimension.ApolloDimensionTypes;
import com.thepinkhacker.apollo.world.gen.feature.ApolloMiscConfiguredFeatures;
import com.thepinkhacker.apollo.world.gen.feature.ApolloMiscPlacedFeatures;
import com.thepinkhacker.apollo.world.gen.feature.ApolloOreConfiguredFeatures;
import com.thepinkhacker.apollo.world.gen.feature.ApolloOrePlacedFeatures;
import com.thepinkhacker.apollo.world.gen.noise.ApolloNoiseParametersKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.concurrent.CompletableFuture;

public class WorldgenProvider extends FabricDynamicRegistryProvider {
    public WorldgenProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        /* === Biomes === */
        addBiome(registries, entries, ApolloBiomeKeys.LUNAR_DITCHES);
        addBiome(registries, entries, ApolloBiomeKeys.LUNAR_HIGHLANDS);
        addBiome(registries, entries, ApolloBiomeKeys.LUNAR_MARIA);

        addBiome(registries, entries, ApolloBiomeKeys.OIL_DESERT);

        /* === Dimension Types === */
        addDimensionType(registries, entries, ApolloDimensionTypes.MOON);

        /* === Noise Parameters === */
        addNoiseParameters(registries, entries, ApolloNoiseParametersKeys.MARIA);

        /* === Features === */
        addFeature(registries, entries, ApolloOreConfiguredFeatures.LUNAR_ORE_IRON, ApolloOrePlacedFeatures.LUNAR_ORE_IRON);
        addConfiguredFeature(registries, entries, ApolloMiscConfiguredFeatures.LAKE_OIL);
        addPlacedFeature(registries, entries, ApolloMiscPlacedFeatures.LAKE_OIL_SURFACE);
        addPlacedFeature(registries, entries, ApolloMiscPlacedFeatures.LAKE_OIL_UNDERGROUND);
        addFeature(registries, entries, ApolloMiscConfiguredFeatures.SPRINGS_OIL, ApolloMiscPlacedFeatures.SPRINGS_OIL);
    }

    private static void addBiome(RegistryWrapper.WrapperLookup registries, Entries entries, RegistryKey<Biome> biome) {
        addEntry(registries, entries, biome, RegistryKeys.BIOME);
    }

    private static void addDimensionType(RegistryWrapper.WrapperLookup registries, Entries entries, RegistryKey<DimensionType> dimensionType) {
        addEntry(registries, entries, dimensionType, RegistryKeys.DIMENSION_TYPE);
    }

    private static void addNoiseParameters(RegistryWrapper.WrapperLookup registries, Entries entries, RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> noiseParameter) {
        addEntry(registries, entries, noiseParameter, RegistryKeys.NOISE_PARAMETERS);
    }

    private static void addConfiguredFeature(RegistryWrapper.WrapperLookup registries, Entries entries, RegistryKey<ConfiguredFeature<?, ?>> configured) {
        addEntry(registries, entries, configured, RegistryKeys.CONFIGURED_FEATURE);
    }

    private static void addPlacedFeature(RegistryWrapper.WrapperLookup registries, Entries entries, RegistryKey<PlacedFeature> placed) {
        addEntry(registries, entries, placed, RegistryKeys.PLACED_FEATURE);
    }

    private static void addFeature(RegistryWrapper.WrapperLookup registries, Entries entries, RegistryKey<ConfiguredFeature<?, ?>> configured, RegistryKey<PlacedFeature> placed) {
        addConfiguredFeature(registries, entries, configured);
        addPlacedFeature(registries, entries, placed);
    }

    private static <T> void addEntry(RegistryWrapper.WrapperLookup registries, Entries entries, RegistryKey<T> key, RegistryKey<Registry<T>> type) {
        entries.add(key, registries.getWrapperOrThrow(type).getOrThrow(key).value());
    }

    @Override
    public String getName() {
        return Apollo.MOD_ID;
    }
}
