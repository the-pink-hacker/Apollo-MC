package com.thepinkhacker.apollo.block.entity.fluid;

import com.thepinkhacker.apollo.block.entity.ApolloBlockEntityTypes;
import com.thepinkhacker.apollo.fluid.FluidCarrier;
import com.thepinkhacker.apollo.fluid.FluidCarrierStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class OilRefineryBlockEntity extends BlockEntity implements FluidCarrier<OilRefineryBlockEntity> {
    public OilRefineryBlockEntity(BlockPos pos, BlockState state) {
        super(ApolloBlockEntityTypes.OIL_REFINERY, pos, state);
    }

    @Nullable
    @Override
    public FluidCarrierStorage<OilRefineryBlockEntity> getFluidCarrierStorage(Direction direction) {
        return FluidCarrier.super.getFluidCarrierStorage(direction);
    }

    @Override
    public FluidCarrierStorage<OilRefineryBlockEntity> getMainCarrierStorage() {
        return null;
    }

    @Override
    public void writeFluidCarrier(NbtCompound nbt) {
        FluidCarrier.super.writeFluidCarrier(nbt);
    }

    @Override
    public void readFluidCarrier(NbtCompound nbt) {
        FluidCarrier.super.readFluidCarrier(nbt);
    }

    @Override
    public boolean checkFluidCarrierDirection(Direction direction) {
        return FluidCarrier.super.checkFluidCarrierDirection(direction);
    }
}
