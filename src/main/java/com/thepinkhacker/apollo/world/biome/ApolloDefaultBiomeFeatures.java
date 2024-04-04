package com.thepinkhacker.apollo.world.biome;

import com.thepinkhacker.apollo.world.gen.feature.ApolloMiscPlacedFeatures;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;

public class ApolloDefaultBiomeFeatures {
    public static void addOilSprings(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.FLUID_SPRINGS, ApolloMiscPlacedFeatures.SPRINGS_OIL);
    }
}
