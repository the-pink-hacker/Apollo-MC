package com.thepinkhacker.apollo.block.entity.fluid;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

/**
 * <p>
 * The part of the fluid system that move liquid from one block to another.
 * </p>
 * <p>
 * Pipes only have a carrier, while storage containers also have another fluid container.
 * This is so they determine how much to release out to other blocks.
 * </p>
 */
public interface FluidCarrier {
    long TICK_TRANSFER_AMOUNT = 1;
    String VARIANT_NBT_TAG = "fluid_carrier_variant";
    String AMOUNT_NBT_TAG = "fluid_carrier_amount";

    SingleVariantStorage<FluidVariant> getFluidCarrierStorage();

    default void tickCarrier(ServerWorld world, BlockPos pos) {
        SingleVariantStorage<FluidVariant> storage = getFluidCarrierStorage();
        for (Direction direction : Direction.values()) {
            if (!checkFluidCarrierDirection(direction)) continue;
            Storage<FluidVariant> storageView = FluidStorage.SIDED.find(world, pos.offset(direction), direction.getOpposite());
            if (storageView == null) continue;
            if (storageView instanceof SingleVariantStorage<FluidVariant> carrier) {
                if (!hasMoreFluid(storage, carrier)) continue;
                try (Transaction transaction = Transaction.openOuter()) {
                    StorageUtil.move(storage, carrier, (variant) -> true, TICK_TRANSFER_AMOUNT, transaction);
                    transaction.commit();
                }
            }
        }
    }

    private static boolean hasMoreFluid(SingleVariantStorage<FluidVariant> current, SingleVariantStorage<FluidVariant> other) {
        if (current.variant.isBlank()) return !other.variant.isBlank();
        if (other.variant.isBlank()) return true;
        if (!current.variant.equals(other.variant)) return false;

        return current.amount > other.amount;
    }

    default void writeFluidCarrier(NbtCompound nbt) {
        SingleVariantStorage<FluidVariant> storage = getFluidCarrierStorage();
        nbt.put(VARIANT_NBT_TAG, storage.variant.toNbt());
        nbt.putLong(AMOUNT_NBT_TAG, storage.amount);
    }

    default void readFluidCarrier(NbtCompound nbt) {
        SingleVariantStorage<FluidVariant> storage = getFluidCarrierStorage();
        storage.variant = FluidVariant.fromNbt(nbt.getCompound(VARIANT_NBT_TAG));
        storage.amount = nbt.getLong(AMOUNT_NBT_TAG);
    }

    default boolean checkFluidCarrierDirection(Direction direction) {
        return true;
    }
}
