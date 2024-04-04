package com.thepinkhacker.apollo.block.entity;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.block.ApolloBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ApolloBlockEntityTypes {
    public static final BlockEntityType<FluidPipeBlockEntity> FLUID_PIPE_BLOCK_ENTITY = of(
            "fluid_pipe",
            FabricBlockEntityTypeBuilder.create(FluidPipeBlockEntity::new, ApolloBlocks.FLUID_PIPE)
    );

    private static <T extends BlockEntity> BlockEntityType<T> of(String id, FabricBlockEntityTypeBuilder<T> builder) {
        return Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(Apollo.MOD_ID, id),
                builder.build()
        );
    }
}
