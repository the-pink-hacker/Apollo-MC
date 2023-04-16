package com.thepinkhacker.apollo.block.entity.fluid;

import com.thepinkhacker.apollo.block.entity.ApolloBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class OilRefineryBlockEntity extends BlockEntity {
    public OilRefineryBlockEntity(BlockPos pos, BlockState state) {
        super(ApolloBlockEntityTypes.OIL_REFINERY, pos, state);
    }
}
