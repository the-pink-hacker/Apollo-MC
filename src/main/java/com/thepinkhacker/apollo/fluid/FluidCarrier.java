package com.thepinkhacker.apollo.fluid;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

/**
 * <p>
 * The part of the fluid system that move liquid from one block to another.
 * </p>
 * <p>
 * Pipes only have a carrier, while storage containers also have another fluid container.
 * This is so they determine how much to release out to other blocks.
 * </p>
 */
public interface FluidCarrier<T extends BlockEntity> {
    long TICK_TRANSFER = FluidConstants.BOTTLE / 20;
    String VARIANT_NBT_TAG = "fluid_carrier_variant";
    String AMOUNT_NBT_TAG = "fluid_carrier_amount";

    @Nullable
    default FluidCarrierStorage<T> getFluidCarrierStorage(Direction direction) {
        return getMainCarrierStorage();
    }

    FluidCarrierStorage<T> getMainCarrierStorage();

    default void tickCarrier(ServerWorld world, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            FluidCarrierStorage<T> storage = getFluidCarrierStorage(direction);
            if (storage == null || storage.isResourceBlank() || !checkFluidCarrierDirection(direction)) continue;
            Storage<FluidVariant> storageView = FluidStorage.SIDED.find(world, pos.offset(direction), direction.getOpposite());
            if (storageView instanceof FluidCarrierStorage<?> carrier) {
                if (!hasMoreFluid(storage, carrier)) continue;
                try (Transaction transaction = Transaction.openOuter()) {
                    StorageUtil.move(
                            storage,
                            carrier,
                            (variant) -> true,
                            TICK_TRANSFER,
                            transaction
                    );
                    transaction.commit();
                }
            }
        }
    }

    private static boolean hasMoreFluid(FluidCarrierStorage<?> current, FluidCarrierStorage<?> other) {
        if (other.isResourceBlank()) return true;
        if (!current.variant.equals(other.variant)) return false;

        return current.amount > other.amount;
    }

    default void writeFluidCarrier(NbtCompound nbt) {
        FluidCarrierStorage<T> storage = getMainCarrierStorage();
        nbt.put(VARIANT_NBT_TAG, storage.variant.toNbt());
        nbt.putLong(AMOUNT_NBT_TAG, storage.amount);
    }

    default void readFluidCarrier(NbtCompound nbt) {
        FluidCarrierStorage<T> storage = getMainCarrierStorage();
        storage.variant = FluidVariant.fromNbt(nbt.getCompound(VARIANT_NBT_TAG));
        storage.amount = nbt.getLong(AMOUNT_NBT_TAG);
    }

    default boolean checkFluidCarrierDirection(Direction direction) {
        return true;
    }
}
