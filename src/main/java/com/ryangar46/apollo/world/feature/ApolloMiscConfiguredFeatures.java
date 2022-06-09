package com.ryangar46.apollo.world.feature;

import com.ryangar46.apollo.block.BlockManager;
import com.ryangar46.apollo.fluid.FluidManager;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class ApolloMiscConfiguredFeatures {
    public static final RegistryEntry<ConfiguredFeature<LakeFeature.Config, ?>> LAKE_OIL = ConfiguredFeatures.register("lake_oil", Feature.LAKE, new LakeFeature.Config(BlockStateProvider.of(BlockManager.OIL), BlockStateProvider.of(Blocks.STONE.getDefaultState())));
    public static final RegistryEntry<ConfiguredFeature<SpringFeatureConfig, ?>> SPRING_OIL = ConfiguredFeatures.register("spring_oil", Feature.SPRING_FEATURE, new SpringFeatureConfig(FluidManager.STILL_OIL.getDefaultState(), true, 4, 1, RegistryEntryList.of(Block::getRegistryEntry, Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DEEPSLATE, Blocks.TUFF, Blocks.CALCITE, Blocks.DIRT, Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW, Blocks.PACKED_ICE)));
}
