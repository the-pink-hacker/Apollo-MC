package com.thepinkhacker.apollo.block.entity.fluid;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

/**
 * <p>
 * The part of the fluid system that move liquid from one block to another.
 * </p>
 * <p>
 * Pipes only have a carrier, while storage containers also have another fluid container.
 * This is so they determine how much to release out to other blocks
 * </p>
 */
public interface FluidCarrier {
    SingleVariantStorage<FluidVariant> getFluidCarrierStorage();

    default void tickCarrier(ServerWorld world, BlockPos pos, BlockState state) {
        SingleVariantStorage<FluidVariant> storage = getFluidCarrierStorage();
        for (Direction direction : Direction.values()) {
            if (!checkFluidCarrierDirection(direction)) continue;
            Storage<FluidVariant> storageViews = FluidStorage.SIDED.find(world, pos.offset(direction), direction.getOpposite());
            if (storageViews == null) continue;
            if (storageViews instanceof SingleVariantStorage<FluidVariant> carrier) {
                world.getPlayers().get(0).sendMessage(Text.of(carrier.getResource() + ": "+ carrier.getAmount()));
            }
        }
    }

    default void writeFluidCarrier(NbtCompound nbt) {
        SingleVariantStorage<FluidVariant> storage = getFluidCarrierStorage();
        nbt.put("fluid_carrier_variant", storage.variant.toNbt());
        nbt.putLong("fluid_carrier_amount", storage.amount);
    }

    default void readFluidCarrier(NbtCompound nbt) {
        SingleVariantStorage<FluidVariant> storage = getFluidCarrierStorage();
        storage.variant = FluidVariant.fromNbt(nbt.getCompound("fluid_carrier_variant"));
        storage.amount = nbt.getLong("fluid_carrier_amount");
    }

    default boolean checkFluidCarrierDirection(Direction direction) {
        return true;
    }
}
