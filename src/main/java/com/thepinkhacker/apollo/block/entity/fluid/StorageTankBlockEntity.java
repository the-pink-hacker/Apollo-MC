package com.thepinkhacker.apollo.block.entity.fluid;

import com.thepinkhacker.apollo.block.entity.ApolloBlockEntityTypes;
import com.thepinkhacker.apollo.fluid.FluidCarrier;
import com.thepinkhacker.apollo.fluid.FluidCarrierStorage;
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

public class StorageTankBlockEntity extends BlockEntity implements FluidCarrier<StorageTankBlockEntity> {
    public final FluidCarrierStorage<StorageTankBlockEntity> FLUID_CARRIER_STORAGE = new FluidCarrierStorage<>(this);
    private static final long TANK_CAPACITY = 8 * FluidConstants.BUCKET;
    private static final String TANK_STORAGE_VARIANT_NBT_TAG = "tank_fluid_variant";
    private static final String TANK_STORAGE_AMOUNT_NBT_TAG = "tank_fluid_amount";
    public final SingleVariantStorage<FluidVariant> TANK_STORAGE = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return TANK_CAPACITY;
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    public StorageTankBlockEntity(BlockPos pos, BlockState state) {
        super(ApolloBlockEntityTypes.STORAGE_TANK, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, StorageTankBlockEntity blockEntity) {
        if (world instanceof ServerWorld serverWorld) blockEntity.tickCarrier(serverWorld, pos);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        writeFluidCarrier(nbt);
        nbt.put(TANK_STORAGE_VARIANT_NBT_TAG, TANK_STORAGE.variant.toNbt());
        nbt.putLong(TANK_STORAGE_AMOUNT_NBT_TAG, TANK_STORAGE.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        readFluidCarrier(nbt);
        TANK_STORAGE.variant = FluidVariant.fromNbt(nbt.getCompound(TANK_STORAGE_VARIANT_NBT_TAG));
        TANK_STORAGE.amount = nbt.getLong(TANK_STORAGE_AMOUNT_NBT_TAG);
    }

    @Override
    public FluidCarrierStorage<StorageTankBlockEntity> getFluidCarrierStorage() {
        return FLUID_CARRIER_STORAGE;
    }

    @Override
    public boolean checkFluidCarrierDirection(Direction direction) {
        return switch (direction) {
            case UP, DOWN -> true;
            default -> false;
        };
    }
}
