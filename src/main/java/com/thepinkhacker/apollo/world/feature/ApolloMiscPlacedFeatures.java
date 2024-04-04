package com.thepinkhacker.apollo.world.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;

public class ApolloMiscPlacedFeatures {
    public static final RegistryKey<PlacedFeature> SPRINGS_OIL = ApolloPlacedFeatures.of("springs_oil");

    public static void bootstrap(Registerable<PlacedFeature> registerable) {
        PlacedFeatures.register(
                registerable,
                SPRINGS_OIL,
                registerable
                        .getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE)
                        .getOrThrow(ApolloMiscConfiguredFeatures.SPRINGS_OIL),
                ApolloPlacedFeatures.modifiersWithCount(
                        25,
                        HeightRangePlacementModifier.uniform(
                                YOffset.getBottom(),
                                YOffset.fixed(192)
                        )
                )
        );
    }
}
