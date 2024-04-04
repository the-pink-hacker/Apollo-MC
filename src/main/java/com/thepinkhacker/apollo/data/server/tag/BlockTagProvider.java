package com.thepinkhacker.apollo.data.server.tag;

import com.thepinkhacker.apollo.block.ApolloBlocks;
import com.thepinkhacker.apollo.registry.tag.ApolloBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class BlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public BlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        configureApollo(arg);
        configureMinecraft(arg);
    }

    private void configureApollo(RegistryWrapper.WrapperLookup lookup) {
        this.getOrCreateTagBuilder(ApolloBlockTags.FLUID_PIPE_CONNECTABLE_BLOCKS)
                .add(ApolloBlocks.FLUID_HOPPER)
                .add(ApolloBlocks.FLUID_PIPE)
                .add(ApolloBlocks.FLUID_VALVE_PIPE)
                .add(ApolloBlocks.OIL_REFINERY)
                .add(ApolloBlocks.STORAGE_TANK);

        this.getOrCreateTagBuilder(ApolloBlockTags.LAUNCHPADS)
                .add(ApolloBlocks.LAUNCHPAD);

        this.getOrCreateTagBuilder(ApolloBlockTags.LUNAR_ORE_REPLACEABLES)
                .add(ApolloBlocks.LUNAR_STONE);

        this.getOrCreateTagBuilder(ApolloBlockTags.INFINIBURN_MOON)
                .forceAddTag(BlockTags.INFINIBURN_OVERWORLD);

        this.getOrCreateTagBuilder(ApolloBlockTags.CONVERTABLE_TO_OILED_SAND)
                .add(Blocks.SAND);

        this.getOrCreateTagBuilder(ApolloBlockTags.OIL_BOTTLEABLE)
                .add(ApolloBlocks.OILED_SAND);
    }

    private void configureMinecraft(RegistryWrapper.WrapperLookup lookup) {
        this.getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ApolloBlocks.FLUID_PIPE)
                .add(ApolloBlocks.FLUID_VALVE_PIPE)
                .add(ApolloBlocks.AIRLOCK_CONTROLLER)
                .add(ApolloBlocks.AIRLOCK_FRAME)
                .add(ApolloBlocks.LAUNCHPAD)
                .add(ApolloBlocks.LUNAR_COBBLESTONE)
                .add(ApolloBlocks.LUNAR_IRON_ORE)
                .add(ApolloBlocks.LUNAR_STONE)
                .add(ApolloBlocks.METEORITE)
                .add(ApolloBlocks.OIL_REFINERY)
                .add(ApolloBlocks.REINFORCED_IRON_BLOCK)
                .add(ApolloBlocks.SHUTTLE_WORKBENCH);

        this.getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(ApolloBlocks.LUNAR_DUST)
                .add(ApolloBlocks.LUNAR_SOIL)
                .add(ApolloBlocks.OILED_SAND);

        this.getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ApolloBlocks.METEORITE)
                .add(ApolloBlocks.REINFORCED_IRON_BLOCK);

        this.getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ApolloBlocks.FLUID_PIPE)
                .add(ApolloBlocks.FLUID_VALVE_PIPE)
                .add(ApolloBlocks.AIRLOCK_CONTROLLER)
                .add(ApolloBlocks.AIRLOCK_FRAME)
                .add(ApolloBlocks.LAUNCHPAD)
                .add(ApolloBlocks.LUNAR_COBBLESTONE)
                .add(ApolloBlocks.LUNAR_IRON_ORE)
                .add(ApolloBlocks.LUNAR_STONE)
                .add(ApolloBlocks.OIL_REFINERY)
                .add(ApolloBlocks.SHUTTLE_WORKBENCH);

        this.getOrCreateTagBuilder(BlockTags.SAND)
                .add(ApolloBlocks.OILED_SAND);
    }
}
