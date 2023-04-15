package com.thepinkhacker.apollo.block.entity;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.block.ApolloBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ApolloBlockEntityTypes {
    public static final BlockEntityType<FluidPipeBlockEntity> FLUID_PIPE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(Apollo.MOD_ID, "fluid_pipe"),
            FabricBlockEntityTypeBuilder.create(FluidPipeBlockEntity::new, ApolloBlocks.FLUID_PIPE)
                    .build()
    );
}
