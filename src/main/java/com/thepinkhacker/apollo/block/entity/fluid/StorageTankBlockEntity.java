package com.thepinkhacker.apollo.block.entity.fluid;

import com.thepinkhacker.apollo.block.entity.ApolloBlockEntityTypes;
import com.thepinkhacker.apollo.fluid.ApolloFluidConstants;
import com.thepinkhacker.apollo.fluid.FluidCarrier;
import com.thepinkhacker.apollo.fluid.FluidCarrierStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StorageTankBlockEntity extends BlockEntity implements FluidCarrier<StorageTankBlockEntity> {
    public final FluidCarrierStorage<StorageTankBlockEntity> FLUID_CARRIER_STORAGE_INPUT = new FluidCarrierStorage<>(this);
    public final FluidCarrierStorage<StorageTankBlockEntity> FLUID_CARRIER_STORAGE_OUTPUT = new FluidCarrierStorage<>(this);
    private static final long TANK_CAPACITY = 8 * ApolloFluidConstants.BLOCK_CAPACITY;
    private static final String TANK_STORAGE_VARIANT_NBT_TAG = "tank_fluid_variant";
    private static final String TANK_STORAGE_AMOUNT_NBT_TAG = "tank_fluid_amount";
    private static final String INPUT_STORAGE_VARIANT_NBT_TAG = VARIANT_NBT_TAG + "_input";
    private static final String INPUT_STORAGE_AMOUNT_NBT_TAG = AMOUNT_NBT_TAG + "_input";
    private static final String OUTPUT_STORAGE_VARIANT_NBT_TAG = VARIANT_NBT_TAG + "_output";
    private static final String OUTPUT_STORAGE_AMOUNT_NBT_TAG = AMOUNT_NBT_TAG + "_output";
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
        if (world instanceof ServerWorld serverWorld) {
            blockEntity.tickCarrier(serverWorld, pos);
            blockEntity.tickTank();
        }
    }

    private void tickTank() {
        storeIntoTank();
        removeFromTank();
    }

    private void removeFromTank() {
        try (Transaction transaction = Transaction.openOuter()) {
            StorageUtil.move(
                    TANK_STORAGE,
                    FLUID_CARRIER_STORAGE_OUTPUT,
                    (variant) -> true,
                    ApolloFluidConstants.TRANSFER,
                    transaction
            );
            transaction.commit();
        }
    }

    private void storeIntoTank() {
        try (Transaction transaction = Transaction.openOuter()) {
            StorageUtil.move(
                    FLUID_CARRIER_STORAGE_INPUT,
                    TANK_STORAGE,
                    (variant) -> true,
                    ApolloFluidConstants.TRANSFER,
                    transaction
            );
            transaction.commit();
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        writeFluidCarrier(nbt);
        nbt.put(TANK_STORAGE_VARIANT_NBT_TAG, TANK_STORAGE.variant.toNbt());
        nbt.putLong(TANK_STORAGE_AMOUNT_NBT_TAG, TANK_STORAGE.amount);
    }

    @Override
    public void writeFluidCarrier(NbtCompound nbt) {
        nbt.put(INPUT_STORAGE_VARIANT_NBT_TAG, FLUID_CARRIER_STORAGE_INPUT.variant.toNbt());
        nbt.putLong(INPUT_STORAGE_AMOUNT_NBT_TAG, FLUID_CARRIER_STORAGE_INPUT.amount);
        nbt.put(OUTPUT_STORAGE_VARIANT_NBT_TAG, FLUID_CARRIER_STORAGE_OUTPUT.variant.toNbt());
        nbt.putLong(OUTPUT_STORAGE_AMOUNT_NBT_TAG, FLUID_CARRIER_STORAGE_OUTPUT.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        readFluidCarrier(nbt);
        TANK_STORAGE.variant = FluidVariant.fromNbt(nbt.getCompound(TANK_STORAGE_VARIANT_NBT_TAG));
        TANK_STORAGE.amount = nbt.getLong(TANK_STORAGE_AMOUNT_NBT_TAG);
    }

    @Override
    public void readFluidCarrier(NbtCompound nbt) {
        FLUID_CARRIER_STORAGE_INPUT.variant = FluidVariant.fromNbt(nbt.getCompound(INPUT_STORAGE_VARIANT_NBT_TAG));
        FLUID_CARRIER_STORAGE_INPUT.amount = nbt.getLong(INPUT_STORAGE_AMOUNT_NBT_TAG);
        FLUID_CARRIER_STORAGE_OUTPUT.variant = FluidVariant.fromNbt(nbt.getCompound(OUTPUT_STORAGE_VARIANT_NBT_TAG));
        FLUID_CARRIER_STORAGE_OUTPUT.amount = nbt.getLong(OUTPUT_STORAGE_AMOUNT_NBT_TAG);
    }

    @Nullable
    @Override
    public FluidCarrierStorage<StorageTankBlockEntity> getFluidCarrierStorage(Direction direction) {
        return switch (direction) {
            case UP -> FLUID_CARRIER_STORAGE_INPUT;
            case DOWN -> FLUID_CARRIER_STORAGE_OUTPUT;
            default -> null;
        };
    }

    @Override
    public FluidCarrierStorage<StorageTankBlockEntity> getMainCarrierStorage() {
        return FLUID_CARRIER_STORAGE_INPUT;
    }

    @Override
    public boolean checkFluidCarrierDirection(Direction direction) {
        return switch (direction) {
            case UP, DOWN -> true;
            default -> false;
        };
    }
}
