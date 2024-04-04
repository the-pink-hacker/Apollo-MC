package com.thepinkhacker.apollo.world.gen.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;

public class ApolloMiscPlacedFeatures {
    public static final RegistryKey<PlacedFeature> LAKE_OIL_UNDERGROUND = ApolloPlacedFeatures.of("lake_oil_underground");
    public static final RegistryKey<PlacedFeature> LAKE_OIL_SURFACE = ApolloPlacedFeatures.of("lake_oil_surface");
    public static final RegistryKey<PlacedFeature> SPRINGS_OIL = ApolloPlacedFeatures.of("springs_oil");

    public static void bootstrap(Registerable<PlacedFeature> registerable) {
        PlacedFeatures.register(
                registerable,
                LAKE_OIL_UNDERGROUND,
                registerable
                        .getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE)
                        .getOrThrow(ApolloMiscConfiguredFeatures.LAKE_OIL),
                ApolloPlacedFeatures.modifiersWithRarity(
                        9,
                        HeightRangePlacementModifier.of(
                                UniformHeightProvider.create(
                                        YOffset.fixed(0),
                                        YOffset.getTop()
                                )
                        )
                )
        );

        PlacedFeatures.register(
                registerable,
                LAKE_OIL_SURFACE,
                registerable
                        .getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE)
                        .getOrThrow(ApolloMiscConfiguredFeatures.LAKE_OIL),
                ApolloPlacedFeatures.modifiersWithRarity(
                        20,
                        PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP
                )
        );

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
