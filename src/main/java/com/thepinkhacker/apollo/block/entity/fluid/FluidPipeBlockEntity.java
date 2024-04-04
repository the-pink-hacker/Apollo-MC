package com.thepinkhacker.apollo.block.entity.fluid;

import com.thepinkhacker.apollo.block.entity.ApolloBlockEntityTypes;
import com.thepinkhacker.apollo.block.fluid.FluidPipeBlock;
import com.thepinkhacker.apollo.fluid.FluidCarrier;
import com.thepinkhacker.apollo.fluid.FluidCarrierStorage;
import com.thepinkhacker.apollo.screen.ApolloExtendedScreenHandlerFactory;
import com.thepinkhacker.apollo.screen.FluidPipeScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FluidPipeBlockEntity extends BlockEntity implements FluidCarrier<FluidPipeBlockEntity>, ApolloExtendedScreenHandlerFactory {
    public final FluidCarrierStorage<FluidPipeBlockEntity> FLUID_CARRIER_STORAGE = new FluidCarrierStorage<>(this);

    public FluidPipeBlockEntity(BlockPos pos, BlockState state) {
        super(ApolloBlockEntityTypes.FLUID_PIPE, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, FluidPipeBlockEntity blockEntity) {
        if (world instanceof ServerWorld serverWorld) blockEntity.tickCarrier(serverWorld, pos);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        writeFluidCarrier(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        readFluidCarrier(nbt);
    }

    @Override
    public FluidCarrierStorage<FluidPipeBlockEntity> getMainCarrierStorage() {
        return FLUID_CARRIER_STORAGE;
    }

    @Override
    public boolean checkFluidCarrierDirection(Direction direction) {
        if (world == null) return false;
        return FluidPipeBlock.getConnections(this.world, this.pos, this.world.getBlockState(this.pos)).contains(direction);
    }

    @Override
    public String getDisplayNameId() {
        return "fluid_pipe";
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FluidPipeScreenHandler(syncId, playerInventory, FLUID_CARRIER_STORAGE.createSnapshot());
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        NbtCompound nbt = new NbtCompound();
        FLUID_CARRIER_STORAGE.writeNbt(nbt);
        buf.writeNbt(nbt);
    }
}
