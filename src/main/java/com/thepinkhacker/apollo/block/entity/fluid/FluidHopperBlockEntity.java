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

public class FluidHopperBlockEntity extends BlockEntity implements FluidCarrier<FluidHopperBlockEntity> {
    public final FluidCarrierStorage<FluidHopperBlockEntity> FLUID_CARRIER_STORAGE = new FluidCarrierStorage<>(this);

    public FluidHopperBlockEntity(BlockPos pos, BlockState state) {
        super(ApolloBlockEntityTypes.FLUID_HOPPER, pos, state);
    }

    @Override
    public FluidCarrierStorage<FluidHopperBlockEntity> getMainCarrierStorage() {
        return FLUID_CARRIER_STORAGE;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        writeFluidCarrier(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        readFluidCarrier(nbt);
    }

    @Override
    public boolean checkFluidCarrierDirection(Direction direction) {
        return direction != Direction.UP;
    }

    public static void tick(World world, BlockPos pos, BlockState state, FluidHopperBlockEntity blockEntity) {
        if (world instanceof ServerWorld serverWorld) blockEntity.tickCarrier(serverWorld, pos);
    }
}
