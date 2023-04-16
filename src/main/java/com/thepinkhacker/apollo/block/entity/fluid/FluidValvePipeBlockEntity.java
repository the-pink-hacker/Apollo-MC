package com.thepinkhacker.apollo.block.entity.fluid;

import com.thepinkhacker.apollo.block.entity.ApolloBlockEntityTypes;
import com.thepinkhacker.apollo.fluid.FluidCarrier;
import com.thepinkhacker.apollo.fluid.FluidCarrierStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FluidValvePipeBlockEntity extends BlockEntity implements FluidCarrier<FluidValvePipeBlockEntity> {
    public final FluidCarrierStorage<FluidValvePipeBlockEntity> FLUID_CARRIER_STORAGE = new FluidCarrierStorage<>(this);

    public FluidValvePipeBlockEntity(BlockPos pos, BlockState state) {
        super(ApolloBlockEntityTypes.FLUID_VALVE_PIPE, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, FluidValvePipeBlockEntity blockEntity) {
        if (world instanceof ServerWorld serverWorld) blockEntity.tickCarrier(serverWorld, pos);
    }

    @Nullable
    @Override
    public FluidCarrierStorage<FluidValvePipeBlockEntity> getFluidCarrierStorage(Direction direction) {
        return FluidCarrier.super.getFluidCarrierStorage(direction);
    }

    @Override
    public FluidCarrierStorage<FluidValvePipeBlockEntity> getMainCarrierStorage() {
        return FLUID_CARRIER_STORAGE;
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
