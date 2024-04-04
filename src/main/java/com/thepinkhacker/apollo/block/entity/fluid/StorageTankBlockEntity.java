package com.thepinkhacker.apollo.block.entity.fluid;

import com.thepinkhacker.apollo.block.entity.ApolloBlockEntityTypes;
import com.thepinkhacker.apollo.fluid.ApolloFluidConstants;
import com.thepinkhacker.apollo.fluid.FluidCarrierStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StorageTankBlockEntity extends InputOutputFluidCarrier<StorageTankBlockEntity> {
    private static final String STORAGE_VARIANT_NBT_TAG = "storage_fluid_variant";
    private static final String STORAGE_AMOUNT_NBT_TAG = "storage_fluid_amount";
    public final FluidCarrierStorage<StorageTankBlockEntity> FLUID_CARRIER_STORAGE_INPUT = new FluidCarrierStorage<>(this);
    public final FluidCarrierStorage<StorageTankBlockEntity> FLUID_CARRIER_STORAGE_OUTPUT = new FluidCarrierStorage<>(this);
    private static final long TANK_CAPACITY = 8 * ApolloFluidConstants.BLOCK_CAPACITY;
    public final SingleVariantStorage<FluidVariant> STORAGE = new SingleVariantStorage<>() {
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
            blockEntity.tickInputOutput();
        }
    }

    @Override
    public FluidCarrierStorage<StorageTankBlockEntity> getInputCarrier() {
        return FLUID_CARRIER_STORAGE_INPUT;
    }

    @Override
    public FluidCarrierStorage<StorageTankBlockEntity> getOutputCarrier() {
        return FLUID_CARRIER_STORAGE_OUTPUT;
    }

    @Override
    public SingleVariantStorage<FluidVariant> getInputStorage() {
        return STORAGE;
    }

    @Override
    public SingleVariantStorage<FluidVariant> getOutputStorage() {
        return STORAGE;
    }

    @Override
    protected void writeStorage(NbtCompound nbt) {
        nbt.put(STORAGE_VARIANT_NBT_TAG, STORAGE.variant.toNbt());
        nbt.putLong(STORAGE_AMOUNT_NBT_TAG, STORAGE.amount);
    }

    @Override
    protected void readStorage(NbtCompound nbt) {
        STORAGE.variant = FluidVariant.fromNbt(nbt.getCompound(STORAGE_VARIANT_NBT_TAG));
        STORAGE.amount = nbt.getLong(STORAGE_AMOUNT_NBT_TAG);
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
    public boolean checkFluidCarrierDirection(Direction direction) {
        return switch (direction) {
            case UP, DOWN -> true;
            default -> false;
        };
    }
}
