package com.ryangar46.apollo.world.surfacerule;

import com.ryangar46.apollo.world.biome.BiomeManager;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ApolloSurfaceRules {
    public static MaterialRules.MaterialRule makeRules() {
        return MaterialRules.sequence(
                /*MaterialRules.condition(
                        MaterialRules.verticalGradient("bedrock_floor", YOffset.getBottom(), YOffset.aboveBottom(5)),
                        getBlockRule(Blocks.BEDROCK)
                ),
                MaterialRules.condition(
                        MaterialRules.biome(BiomeManager.OIL_DESERT),
                        MaterialRules.sequence(
                                // Sandstone that supports the sand on ledges
                                MaterialRules.condition(
                                        MaterialRules.STONE_DEPTH_CEILING,
                                        getBlockRule(Blocks.SANDSTONE)
                                ),
                                // Above ground sand
                                MaterialRules.condition(
                                        MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH_RANGE_6,
                                        getBlockRule(Blocks.SAND)
                                ),
                                // Underground sandstone
                                MaterialRules.condition(
                                        MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH_RANGE_30,
                                        getBlockRule(Blocks.SANDSTONE)
                                ),
                                getBlockRule(Blocks.STONE)
                        )
                )*/
        );
    }

    private static MaterialRules.MaterialRule getBlockRule(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
