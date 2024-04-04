package com.ryangar46.apollo.block.entity;

import com.ryangar46.apollo.block.FluidPipeBlock;
import com.ryangar46.apollo.entity.ApolloEntityTypes;
import com.ryangar46.apollo.fluid.PipeStorableFluid;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class FluidPipeBlockEntity extends BlockEntity {
    public FluidPipeBlockEntity(BlockPos pos, BlockState state) {
        super(ApolloEntityTypes.FLUID_PIPE_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, FluidPipeBlockEntity blockEntity) {
        int level = FluidPipeBlock.getFluidLevel(state);
        PipeStorableFluid fluid = FluidPipeBlock.getFluidType(state);

        for (Direction direction : FluidPipeBlock.getConnections(world, pos, state)) {
            if (level > 0 && !fluid.isEmpty()) {
                BlockPos otherPos = pos.offset(direction);
                BlockState otherState = world.getBlockState(otherPos);
                int otherLevel = FluidPipeBlock.getFluidLevel(otherState);
                PipeStorableFluid otherFluid = FluidPipeBlock.getFluidType(otherState);

                if (fluid.canMerge(otherFluid)) {
                    if (level > otherLevel && otherLevel < 7) {
                        level--;
                        otherLevel++;
                        otherState = FluidPipeBlock.setFluidLevel(otherState, otherLevel);
                        otherState = FluidPipeBlock.setFluidType(otherState, fluid);
                        world.setBlockState(otherPos, otherState);
                    }
                }
            } else break;
        }

        if (level == 0) state = FluidPipeBlock.setFluidType(state, PipeStorableFluid.EMPTY);
        if (fluid.isEmpty()) level = 0;

        state = FluidPipeBlock.setFluidLevel(state, level);
        world.setBlockState(pos, state);
    }
}
