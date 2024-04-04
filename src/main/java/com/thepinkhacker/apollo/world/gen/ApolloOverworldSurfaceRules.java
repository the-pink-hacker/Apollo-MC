package com.thepinkhacker.apollo.world.gen;

import com.thepinkhacker.apollo.world.biome.ApolloBiomeKeys;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ApolloOverworldSurfaceRules {
    private static final MaterialRules.MaterialRule SAND = block(Blocks.SAND);
    private static final MaterialRules.MaterialRule SANDSTONE = block(Blocks.SANDSTONE);
    private static final MaterialRules.MaterialRule BEDROCK = block(Blocks.BEDROCK);
    private static final MaterialRules.MaterialCondition OIL_DESERT = MaterialRules.biome(ApolloBiomeKeys.OIL_DESERT);

    public static MaterialRules.MaterialRule createRules() {
        return MaterialRules.sequence(
                MaterialRules.condition(
                        MaterialRules.verticalGradient("bedrock_floor",
                                YOffset.getBottom(),
                                YOffset.aboveBottom(5)
                        ),
                        BEDROCK
                ),
                MaterialRules.condition(
                        MaterialRules.aboveY(YOffset.fixed(52), 0),
                        MaterialRules.condition(
                                MaterialRules.stoneDepth(
                                        0,
                                        true,
                                        30,
                                        VerticalSurfaceType.FLOOR
                                ),
                                MaterialRules.condition(
                                        OIL_DESERT,
                                        MaterialRules.sequence(
                                                MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, SANDSTONE),
                                                MaterialRules.condition(
                                                        MaterialRules.stoneDepth(
                                                                0,
                                                                true,
                                                                5,
                                                                VerticalSurfaceType.FLOOR
                                                        ),
                                                        SAND
                                                ),
                                                SANDSTONE
                                        )
                                )
                        )
                )
        );
    }

    private static MaterialRules.MaterialRule block(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
