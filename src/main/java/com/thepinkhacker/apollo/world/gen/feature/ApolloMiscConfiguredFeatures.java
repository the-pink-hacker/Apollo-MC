package com.thepinkhacker.apollo.world.gen.feature;

import com.thepinkhacker.apollo.block.ApolloBlocks;
import com.thepinkhacker.apollo.fluid.ApolloFluids;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class ApolloMiscConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> LAKE_OIL = ApolloConfiguredFeatures.of("lake_oil");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SPRINGS_OIL = ApolloConfiguredFeatures.of("springs_oil");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> registerable) {
        ConfiguredFeatures.register(
                registerable,
                LAKE_OIL,
                Feature.LAKE,
                new LakeFeature.Config(
                        BlockStateProvider.of(ApolloBlocks.OIL),
                        BlockStateProvider.of(ApolloBlocks.OILED_SAND)
                )
        );

        ConfiguredFeatures.register(
                registerable,
                SPRINGS_OIL,
                Feature.SPRING_FEATURE,
                new SpringFeatureConfig(
                        ApolloFluids.STILL_OIL.getDefaultState(),
                        true,
                        4,
                        1,
                        RegistryEntryList.of(
                                Block::getRegistryEntry,
                                Blocks.STONE,
                                Blocks.GRANITE,
                                Blocks.DIORITE,
                                Blocks.ANDESITE,
                                Blocks.DEEPSLATE,
                                Blocks.TUFF,
                                Blocks.CALCITE,
                                Blocks.DIRT,
                                Blocks.SNOW_BLOCK,
                                Blocks.POWDER_SNOW,
                                Blocks.PACKED_ICE
                        )
                )
        );
    }
}
