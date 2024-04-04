package com.thepinkhacker.apollo.block.entity.fluid;

import com.thepinkhacker.apollo.fluid.ApolloFluidConstants;
import com.thepinkhacker.apollo.fluid.FluidCarrier;
import com.thepinkhacker.apollo.fluid.FluidCarrierStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public abstract class InputOutputFluidCarrier<T extends BlockEntity> extends BlockEntity implements FluidCarrier<T> {
    private static final String INPUT_STORAGE_VARIANT_NBT_TAG = VARIANT_NBT_TAG + "_input";
    private static final String INPUT_STORAGE_AMOUNT_NBT_TAG = AMOUNT_NBT_TAG + "_input";
    private static final String OUTPUT_STORAGE_VARIANT_NBT_TAG = VARIANT_NBT_TAG + "_output";
    private static final String OUTPUT_STORAGE_AMOUNT_NBT_TAG = AMOUNT_NBT_TAG + "_output";

    public InputOutputFluidCarrier(BlockEntityType<T> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public abstract FluidCarrierStorage<T> getInputCarrier();

    public abstract FluidCarrierStorage<T> getOutputCarrier();

    public abstract SingleVariantStorage<FluidVariant> getInputStorage();

    public abstract SingleVariantStorage<FluidVariant> getOutputStorage();

    public void tickInputOutput() {
        tickInput();
        tickOutput();
    }

    private void tickOutput() {
        try (Transaction transaction = Transaction.openOuter()) {
            StorageUtil.move(
                    getOutputStorage(),
                    getOutputCarrier(),
                    (variant) -> true,
                    ApolloFluidConstants.TRANSFER,
                    transaction
            );
            transaction.commit();
        }
    }

    private void tickInput() {
        try (Transaction transaction = Transaction.openOuter()) {
            StorageUtil.move(
                    getInputCarrier(),
                    getInputStorage(),
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
        writeStorage(nbt);
    }

    protected abstract void writeStorage(NbtCompound nbt);

    @Override
    public void writeFluidCarrier(NbtCompound nbt) {
        FluidCarrierStorage<T> inputCarrier = getInputCarrier();
        FluidCarrierStorage<T> outputCarrier = getOutputCarrier();
        nbt.put(INPUT_STORAGE_VARIANT_NBT_TAG, inputCarrier.variant.toNbt());
        nbt.putLong(INPUT_STORAGE_AMOUNT_NBT_TAG, inputCarrier.amount);
        nbt.put(OUTPUT_STORAGE_VARIANT_NBT_TAG, outputCarrier.variant.toNbt());
        nbt.putLong(OUTPUT_STORAGE_AMOUNT_NBT_TAG, outputCarrier.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        readFluidCarrier(nbt);
        readStorage(nbt);
    }

    protected abstract void readStorage(NbtCompound nbt);

    @Override
    public void readFluidCarrier(NbtCompound nbt) {
        FluidCarrierStorage<T> inputCarrier = getInputCarrier();
        FluidCarrierStorage<T> outputCarrier = getOutputCarrier();
        inputCarrier.variant = FluidVariant.fromNbt(nbt.getCompound(INPUT_STORAGE_VARIANT_NBT_TAG));
        inputCarrier.amount = nbt.getLong(INPUT_STORAGE_AMOUNT_NBT_TAG);
        outputCarrier.variant = FluidVariant.fromNbt(nbt.getCompound(OUTPUT_STORAGE_VARIANT_NBT_TAG));
        outputCarrier.amount = nbt.getLong(OUTPUT_STORAGE_AMOUNT_NBT_TAG);
    }

    @Override
    public FluidCarrierStorage<T> getMainCarrierStorage() {
        return getInputCarrier();
    }
}
