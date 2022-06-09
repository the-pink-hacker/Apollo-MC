package com.ryangar46.apollo.world.feature;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;

public class ApolloDefaultBiomeFeatures {
    public static void addOilLakes(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LAKES, ApolloMiscPlacedFeatures.LAKE_OIL_UNDERGROUND);
        builder.feature(GenerationStep.Feature.LAKES, ApolloMiscPlacedFeatures.LAKE_OIL_SURFACE);
    }
    public static void addOilSprings(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.FLUID_SPRINGS, ApolloMiscPlacedFeatures.SPRING_OIL);
    }
}
