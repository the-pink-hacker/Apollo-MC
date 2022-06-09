package com.ryangar46.apollo.world.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import net.minecraft.world.gen.placementmodifier.*;

public class ApolloMiscPlacedFeatures {
    public static final RegistryEntry<PlacedFeature> LAKE_OIL_UNDERGROUND = PlacedFeatures.register(
            "lake_oil_underground",
            ApolloMiscConfiguredFeatures.LAKE_OIL,
            RarityFilterPlacementModifier.of(3),
            SquarePlacementModifier.of(),
            HeightRangePlacementModifier.of(
                    UniformHeightProvider.create(
                            YOffset.fixed(0),
                            YOffset.getTop()
                    )
            ),
            EnvironmentScanPlacementModifier.of(
                    Direction.DOWN,
                    BlockPredicate.bothOf(
                            BlockPredicate.not(BlockPredicate.IS_AIR),
                            BlockPredicate.insideWorldBounds(new BlockPos(0, -5, 0))
                    ),
                    32
            ),
            SurfaceThresholdFilterPlacementModifier.of(
                    Heightmap.Type.OCEAN_FLOOR_WG,
                    Integer.MIN_VALUE, -5
            ),
            BiomePlacementModifier.of()
    );
    public static final RegistryEntry<PlacedFeature> LAKE_OIL_SURFACE = PlacedFeatures.register(
            "lake_oil_surface",
            ApolloMiscConfiguredFeatures.LAKE_OIL,
            RarityFilterPlacementModifier.of(50),
            SquarePlacementModifier.of(),
            PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
            BiomePlacementModifier.of()
    );
    public static final RegistryEntry<PlacedFeature> SPRING_OIL = PlacedFeatures.register(
            "spring_oil",
            ApolloMiscConfiguredFeatures.SPRING_OIL,
            CountPlacementModifier.of(25),
            SquarePlacementModifier.of(),
            HeightRangePlacementModifier.uniform(
                    YOffset.getBottom(),
                    YOffset.fixed(192)
            ),
            BiomePlacementModifier.of()
    );
}
