package com.thepinkhacker.apollo.fluid;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.ResourceAmount;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.entity.BlockEntity;

public class FluidCarrierStorage<T extends BlockEntity> extends SingleVariantStorage<FluidVariant> {
    public static final long CAPACITY = ApolloFluidConstants.BLOCK_CAPACITY;
    private final T parent;

    public FluidCarrierStorage(T parent) {
        this.parent = parent;
    }

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
        parent.markDirty();
    }

    @Override
    public ResourceAmount<FluidVariant> createSnapshot() {
        return super.createSnapshot();
    }
}
