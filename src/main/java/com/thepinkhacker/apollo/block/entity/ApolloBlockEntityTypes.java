package com.thepinkhacker.apollo.block.entity;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.block.ApolloBlocks;
import com.thepinkhacker.apollo.block.entity.fluid.FluidPipeBlockEntity;
import com.thepinkhacker.apollo.block.entity.fluid.FluidValvePipeBlockEntity;
import com.thepinkhacker.apollo.block.entity.fluid.StorageTankBlockEntity;
import com.thepinkhacker.apollo.fluid.FluidCarrier;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ApolloBlockEntityTypes {
    public static final BlockEntityType<FluidPipeBlockEntity> FLUID_PIPE = of(
            "fluid_pipe",
            FabricBlockEntityTypeBuilder.create(FluidPipeBlockEntity::new, ApolloBlocks.FLUID_PIPE)
    );
    public static final BlockEntityType<FluidValvePipeBlockEntity> FLUID_VALVE_PIPE = of(
            "fluid_valve_pipe",
            FabricBlockEntityTypeBuilder.create(FluidValvePipeBlockEntity::new, ApolloBlocks.FLUID_VALVE_PIPE)
    );
    public static final BlockEntityType<StorageTankBlockEntity> STORAGE_TANK = of(
            "storage_tank",
            FabricBlockEntityTypeBuilder.create(StorageTankBlockEntity::new, ApolloBlocks.STORAGE_TANK)
    );

    public static void register() {
        registerFluidCarrier(FLUID_PIPE);
        registerFluidCarrier(FLUID_VALVE_PIPE);
        registerFluidCarrier(STORAGE_TANK);
    }

    private static <T extends BlockEntity> BlockEntityType<T> of(String id, FabricBlockEntityTypeBuilder<T> builder) {
        return Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(Apollo.MOD_ID, id),
                builder.build()
        );
    }

    private static <T extends BlockEntity & FluidCarrier<?>> void registerFluidCarrier(BlockEntityType<T> blockEntityType) {
        FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> {
            return blockEntity.checkFluidCarrierDirection(direction) ? blockEntity.getFluidCarrierStorage(direction) : null;
        }, blockEntityType);
    }
}
