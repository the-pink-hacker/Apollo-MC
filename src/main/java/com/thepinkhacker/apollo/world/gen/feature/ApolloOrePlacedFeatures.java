package com.thepinkhacker.apollo.world.gen.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;

public class ApolloOrePlacedFeatures {
    public static final RegistryKey<PlacedFeature> LUNAR_ORE_IRON = ApolloPlacedFeatures.of("lunar_ore_iron");

    public static void bootstrap(Registerable<PlacedFeature> registerable) {
        PlacedFeatures.register(
                registerable,
                LUNAR_ORE_IRON,
                registerable
                        .getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE)
                        .getOrThrow(ApolloOreConfiguredFeatures.LUNAR_ORE_IRON),
                ApolloPlacedFeatures.modifiersWithCount(
                        15,
                        HeightRangePlacementModifier.uniform(
                                YOffset.fixed(0),
                                YOffset.fixed(100)
                        )
                )
        );
    }
}
