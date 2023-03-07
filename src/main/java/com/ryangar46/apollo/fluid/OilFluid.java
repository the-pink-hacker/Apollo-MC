package com.ryangar46.apollo.fluid;

import com.ryangar46.apollo.block.ApolloBlocks;
import com.ryangar46.apollo.item.ApolloItems;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.world.World;

public abstract class OilFluid extends AbstractFluid {
    @Override
    public Fluid getStill() {
        return ApolloFluids.STILL_OIL;
    }

    @Override
    public Fluid getFlowing() {
        return ApolloFluids.FLOWING_OIL;
    }

    @Override
    public Item getBucketItem() {
        return ApolloItems.OIL_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        return ApolloBlocks.OIL.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
    }

    @Override
    public float getBlastResistance() {
        return 100.0f;
    }

    @Override
    public boolean isInfinite(World world) {
        return false;
    }

    public static class Flowing extends OilFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends OilFluid {
        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}
