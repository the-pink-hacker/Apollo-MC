package com.thepinkhacker.apollo.block.entity.fluid;

import com.thepinkhacker.apollo.block.entity.ApolloBlockEntityTypes;
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

public class StorageTankBlockEntity extends BlockEntity implements FluidCarrier {
    private static final long CAPACITY = 8 * FluidConstants.BUCKET;
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
//                ServerPlayNetworking.send(player, new Identifier(Apollo.MOD_ID, "storage_tank"), buffer);
//            });
        }
    };

    public StorageTankBlockEntity(BlockPos pos, BlockState state) {
        super(ApolloBlockEntityTypes.STORAGE_TANK, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, StorageTankBlockEntity blockEntity) {
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
        return switch (direction) {
            case UP, DOWN -> true;
            default -> false;
        };
    }
}
