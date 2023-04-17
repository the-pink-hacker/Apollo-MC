package com.thepinkhacker.apollo.block.entity.fluid;

import com.thepinkhacker.apollo.block.entity.ApolloBlockEntityTypes;
import com.thepinkhacker.apollo.block.fluid.FluidPipeBlock;
import com.thepinkhacker.apollo.fluid.FluidCarrier;
import com.thepinkhacker.apollo.fluid.FluidCarrierStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class FluidPipeBlockEntity extends BlockEntity implements FluidCarrier<FluidPipeBlockEntity> {
    public final FluidCarrierStorage<FluidPipeBlockEntity> FLUID_CARRIER_STORAGE = new FluidCarrierStorage<>(this);

    public FluidPipeBlockEntity(BlockPos pos, BlockState state) {
        super(ApolloBlockEntityTypes.FLUID_PIPE, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, FluidPipeBlockEntity blockEntity) {
        if (world instanceof ServerWorld serverWorld) blockEntity.tickCarrier(serverWorld, pos);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        writeFluidCarrier(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        readFluidCarrier(nbt);
    }

    @Override
    public FluidCarrierStorage<FluidPipeBlockEntity> getMainCarrierStorage() {
        return FLUID_CARRIER_STORAGE;
    }

    @Override
    public boolean checkFluidCarrierDirection(Direction direction) {
        if (world == null) return false;
        return FluidPipeBlock.getConnections(this.world, this.pos, this.world.getBlockState(this.pos)).contains(direction);
    }
}
