package com.thepinkhacker.apollo.world.feature;

import com.thepinkhacker.apollo.Apollo;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ApolloOrePlacedFeatures {
    public static final RegistryKey<PlacedFeature> LUNAR_ORE_IRON = of("lunar_ore_iron");

    public static void bootstrap(Registerable<PlacedFeature> registerable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> configuredFeatureLookup = registerable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        PlacedFeatures.register(
                registerable,
                LUNAR_ORE_IRON,
                configuredFeatureLookup.getOrThrow(ApolloOreConfiguredFeatures.LUNAR_ORE_IRON),
                modifiersWithCount(
                        15,
                        HeightRangePlacementModifier.uniform(
                                YOffset.fixed(0),
                                YOffset.fixed(100)
                        )
                )
        );
    }

    private static RegistryKey<PlacedFeature> of(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Apollo.getIdentifier(id));
    }

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }

    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
    }
}
