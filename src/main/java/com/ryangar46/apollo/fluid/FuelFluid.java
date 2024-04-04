package com.ryangar46.apollo.fluid;

import com.ryangar46.apollo.block.ApolloBlocks;
import com.ryangar46.apollo.item.ItemManager;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.world.World;

public abstract class FuelFluid extends AbstractFluid {
    @Override
    public Fluid getStill() {
        return FluidManager.STILL_FUEL;
    }

    @Override
    public Fluid getFlowing() {
        return FluidManager.FLOWING_FUEL;
    }

    @Override
    public Item getBucketItem() {
        return ItemManager.FUEL_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        return ApolloBlocks.FUEL.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
    }

    @Override
    public float getBlastResistance() {
        return 100.0f;
    }

    @Override
    public boolean isInfinite(World world) {
        return false;
    }

    public static class Flowing extends FuelFluid {
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

    public static class Still extends FuelFluid {
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
