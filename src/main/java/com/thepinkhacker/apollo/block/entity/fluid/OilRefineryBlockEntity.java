package com.thepinkhacker.apollo.block.entity.fluid;

import com.thepinkhacker.apollo.block.entity.ApolloBlockEntityTypes;
import com.thepinkhacker.apollo.fluid.ApolloFluidConstants;
import com.thepinkhacker.apollo.fluid.FluidCarrierStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class OilRefineryBlockEntity extends InputOutputFluidCarrier<OilRefineryBlockEntity> {
    private static final String STORAGE_VARIANT_NBT_TAG = "storage_fluid_variant";
    private static final String STORAGE_AMOUNT_NBT_TAG = "storage_fluid_amount";
    private static final String INPUT_STORAGE_VARIANT_NBT_TAG = STORAGE_VARIANT_NBT_TAG + "_input";
    private static final String INPUT_STORAGE_AMOUNT_NBT_TAG = STORAGE_AMOUNT_NBT_TAG + "_input";
    private static final String OUTPUT_STORAGE_VARIANT_NBT_TAG = STORAGE_VARIANT_NBT_TAG + "_output";
    private static final String OUTPUT_STORAGE_AMOUNT_NBT_TAG = STORAGE_AMOUNT_NBT_TAG + "_output";
    public final FluidCarrierStorage<OilRefineryBlockEntity> FLUID_CARRIER_STORAGE_INPUT = new FluidCarrierStorage<>(this);
    public final FluidCarrierStorage<OilRefineryBlockEntity> FLUID_CARRIER_STORAGE_OUTPUT = new FluidCarrierStorage<>(this);
    public final FluidStorage INPUT_STORAGE = new FluidStorage(this);
    public final FluidStorage OUTPUT_STORAGE = new FluidStorage(this);

    public OilRefineryBlockEntity(BlockPos pos, BlockState state) {
        super(ApolloBlockEntityTypes.OIL_REFINERY, pos, state);
    }

    @Override
    public FluidCarrierStorage<OilRefineryBlockEntity> getInputCarrier() {
        return FLUID_CARRIER_STORAGE_INPUT;
    }

    @Override
    public FluidCarrierStorage<OilRefineryBlockEntity> getOutputCarrier() {
        return FLUID_CARRIER_STORAGE_OUTPUT;
    }

    @Override
    public SingleVariantStorage<FluidVariant> getInputStorage() {
        return INPUT_STORAGE;
    }

    @Override
    public SingleVariantStorage<FluidVariant> getOutputStorage() {
        return OUTPUT_STORAGE;
    }

    @Override
    protected void writeStorage(NbtCompound nbt) {
        nbt.put(INPUT_STORAGE_VARIANT_NBT_TAG, INPUT_STORAGE.variant.toNbt());
        nbt.putLong(INPUT_STORAGE_AMOUNT_NBT_TAG, INPUT_STORAGE.amount);
        nbt.put(OUTPUT_STORAGE_VARIANT_NBT_TAG, OUTPUT_STORAGE.variant.toNbt());
        nbt.putLong(OUTPUT_STORAGE_AMOUNT_NBT_TAG, OUTPUT_STORAGE.amount);
    }

    @Override
    protected void readStorage(NbtCompound nbt) {
        INPUT_STORAGE.variant = FluidVariant.fromNbt(nbt.getCompound(INPUT_STORAGE_VARIANT_NBT_TAG));
        INPUT_STORAGE.amount = nbt.getLong(INPUT_STORAGE_AMOUNT_NBT_TAG);
        OUTPUT_STORAGE.variant = FluidVariant.fromNbt(nbt.getCompound(OUTPUT_STORAGE_VARIANT_NBT_TAG));
        OUTPUT_STORAGE.amount = nbt.getLong(OUTPUT_STORAGE_AMOUNT_NBT_TAG);
    }

    private static class FluidStorage extends SingleVariantStorage<FluidVariant> {
        private final OilRefineryBlockEntity parent;

        public FluidStorage(OilRefineryBlockEntity parent) {
            this.parent = parent;
        }

        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return ApolloFluidConstants.BLOCK_CAPACITY;
        }

        @Override
        protected void onFinalCommit() {
            this.parent.markDirty();
        }
    };
}
