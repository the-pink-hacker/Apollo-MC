package com.thepinkhacker.apollo.block.entity.fluid;

import com.thepinkhacker.apollo.block.entity.ApolloBlockEntityTypes;
import com.thepinkhacker.apollo.block.fluid.FluidValvePipeBlock;
import com.thepinkhacker.apollo.fluid.FluidCarrier;
import com.thepinkhacker.apollo.fluid.FluidCarrierStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FluidValvePipeBlockEntity extends BlockEntity implements FluidCarrier<FluidValvePipeBlockEntity> {
    public final FluidValvePipeStorage FLUID_CARRIER_STORAGE_POSITIVE = new FluidValvePipeStorage(this);
    public final FluidValvePipeStorage FLUID_CARRIER_STORAGE_NEGATIVE = new FluidValvePipeStorage(this);

    private static final String POSITIVE_STORAGE_VARIANT_NBT_TAG = VARIANT_NBT_TAG + "_positive";
    private static final String POSITIVE_STORAGE_AMOUNT_NBT_TAG = AMOUNT_NBT_TAG + "_positive";

    private static final String NEGATIVE_STORAGE_VARIANT_NBT_TAG = VARIANT_NBT_TAG + "_negative";
    private static final String NEGATIVE_STORAGE_AMOUNT_NBT_TAG = AMOUNT_NBT_TAG + "_negative";

    public FluidValvePipeBlockEntity(BlockPos pos, BlockState state) {
        super(ApolloBlockEntityTypes.FLUID_VALVE_PIPE, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, FluidValvePipeBlockEntity blockEntity) {
        if (world instanceof ServerWorld serverWorld) {
            blockEntity.tickCarrier(serverWorld, pos);
            blockEntity.tickValve(state);
        }
    }

    private void tickValve(BlockState state) {
        if (!FluidValvePipeBlock.isOpen(state)) return;
        long amountDifference = FLUID_CARRIER_STORAGE_POSITIVE.amount - FLUID_CARRIER_STORAGE_NEGATIVE.amount;
        if (amountDifference == 0) return;

        boolean positiveIsHighest = amountDifference > 0;
        FluidValvePipeStorage highestStorage;
        FluidValvePipeStorage lowestStorage;

        if (positiveIsHighest) {
            highestStorage = FLUID_CARRIER_STORAGE_POSITIVE;
            lowestStorage = FLUID_CARRIER_STORAGE_NEGATIVE;
        } else {
            highestStorage = FLUID_CARRIER_STORAGE_NEGATIVE;
            lowestStorage = FLUID_CARRIER_STORAGE_POSITIVE;
            // Keep difference positive for fluid transfer
            amountDifference = -amountDifference;
        }

        try (Transaction transaction = Transaction.openOuter()) {
            StorageUtil.move(
                    highestStorage,
                    lowestStorage,
                    variant -> true,
                    amountDifference / 2,
                    transaction
            );
            transaction.commit();
        }
    }

    @Nullable
    @Override
    public FluidCarrierStorage<FluidValvePipeBlockEntity> getFluidCarrierStorage(Direction direction) {
        return isDirectionPositive(direction) ? FLUID_CARRIER_STORAGE_POSITIVE : FLUID_CARRIER_STORAGE_NEGATIVE;
    }

    private static boolean isDirectionPositive(Direction direction) {
        return switch (direction) {
            case UP, SOUTH, EAST -> true;
            case DOWN, NORTH, WEST -> false;
        };
    }

    @Override
    public FluidCarrierStorage<FluidValvePipeBlockEntity> getMainCarrierStorage() {
        return FLUID_CARRIER_STORAGE_POSITIVE;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        writeFluidCarrier(nbt);
    }

    @Override
    public void writeFluidCarrier(NbtCompound nbt) {
        nbt.put(NEGATIVE_STORAGE_VARIANT_NBT_TAG, FLUID_CARRIER_STORAGE_NEGATIVE.variant.toNbt());
        nbt.putLong(NEGATIVE_STORAGE_AMOUNT_NBT_TAG, FLUID_CARRIER_STORAGE_NEGATIVE.amount);
        nbt.put(POSITIVE_STORAGE_VARIANT_NBT_TAG, FLUID_CARRIER_STORAGE_POSITIVE.variant.toNbt());
        nbt.putLong(POSITIVE_STORAGE_AMOUNT_NBT_TAG, FLUID_CARRIER_STORAGE_POSITIVE.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        readFluidCarrier(nbt);
    }

    @Override
    public void readFluidCarrier(NbtCompound nbt) {
        FLUID_CARRIER_STORAGE_NEGATIVE.variant = FluidVariant.fromNbt(nbt.getCompound(NEGATIVE_STORAGE_VARIANT_NBT_TAG));
        FLUID_CARRIER_STORAGE_NEGATIVE.amount = nbt.getLong(NEGATIVE_STORAGE_AMOUNT_NBT_TAG);
        FLUID_CARRIER_STORAGE_POSITIVE.variant = FluidVariant.fromNbt(nbt.getCompound(POSITIVE_STORAGE_VARIANT_NBT_TAG));
        FLUID_CARRIER_STORAGE_POSITIVE.amount = nbt.getLong(POSITIVE_STORAGE_AMOUNT_NBT_TAG);
    }

    @Override
    public boolean checkFluidCarrierDirection(Direction direction) {
        if (world == null) return false;
        return world.getBlockState(pos).get(FluidValvePipeBlock.AXIS_STATE) == direction.getAxis();
    }

    private static class FluidValvePipeStorage extends FluidCarrierStorage<FluidValvePipeBlockEntity> {
        public FluidValvePipeStorage(FluidValvePipeBlockEntity parent) {
            super(parent);
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return CAPACITY / 2;
        }
    }
}
