package com.thepinkhacker.apollo.block.entity.fluid;

import com.thepinkhacker.apollo.block.entity.ApolloBlockEntityTypes;
import com.thepinkhacker.apollo.block.fluid.FluidPipeBlock;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

@SuppressWarnings("UnstableApiUsage")
public class FluidPipeBlockEntity extends BlockEntity implements FluidCarrier {
    private static final long CAPACITY = FluidConstants.BUCKET;
    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return CAPACITY;
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            if (!(world instanceof ServerWorld)) return;

            // TODO: Figure out what this does
//            var buffer = PacketByteBufs.create();
//            PlayerLookup.tracking(StorageTankBlockEntity.this).forEach(player -> {
//                ServerPlayNetworking.send(player, IDENTIFIER, buffer);
//            });
        }
    };

    public FluidPipeBlockEntity(BlockPos pos, BlockState state) {
        super(ApolloBlockEntityTypes.FLUID_PIPE, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, FluidPipeBlockEntity blockEntity) {
        if (world instanceof ServerWorld serverWorld) blockEntity.tickCarrier(serverWorld, pos, state);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        writeFluidCarrier(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        readFluidCarrier(nbt);
    }

    @Override
    public SingleVariantStorage<FluidVariant> getFluidCarrierStorage() {
        return fluidStorage;
    }

    @Override
    public boolean checkFluidCarrierDirection(Direction direction) {
        if (world == null) return false;
        return FluidPipeBlock.getConnections(this.world, this.pos, this.world.getBlockState(this.pos)).contains(direction);
    }
}
