package com.thepinkhacker.apollo.data.client;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.block.ApolloBlocks;
import com.thepinkhacker.apollo.item.ApolloItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        /* === Cube All === */
        generator.registerSimpleCubeAll(ApolloBlocks.AIRLOCK_CONTROLLER);
        generator.registerSimpleCubeAll(ApolloBlocks.AIRLOCK_FRAME);
        generator.registerSimpleCubeAll(ApolloBlocks.FLUID_HOPPER);
        generator.registerRotatable(ApolloBlocks.LUNAR_COBBLESTONE);
        generator.registerRotatable(ApolloBlocks.LUNAR_DUST);
        generator.registerSimpleCubeAll(ApolloBlocks.LUNAR_IRON_ORE);
        generator.registerRotatable(ApolloBlocks.LUNAR_SOIL);
        generator.registerRotatable(ApolloBlocks.LUNAR_STONE);
        generator.registerSimpleCubeAll(ApolloBlocks.REINFORCED_IRON_BLOCK);
        generator.registerRotatable(ApolloBlocks.OILED_SAND);

        /* === Items === */
        registerSimpleBlockItem(generator, ApolloBlocks.AIRLOCK_CONTROLLER);
        registerSimpleBlockItem(generator, ApolloBlocks.AIRLOCK_FRAME);
        registerSimpleBlockItem(generator, ApolloBlocks.FLUID_HOPPER);
        registerSimpleBlockItem(generator, ApolloBlocks.FLUID_PIPE, "fluid_pipe_inv");
        registerSimpleBlockItem(generator, ApolloBlocks.FLUID_VALVE_PIPE, "fluid_valve_pipe");
        registerSimpleBlockItem(generator, ApolloBlocks.LAUNCHPAD, "launchpad_inv");
        registerSimpleBlockItem(generator, ApolloBlocks.LUNAR_COBBLESTONE);
        registerSimpleBlockItem(generator, ApolloBlocks.LUNAR_DUST);
        registerSimpleBlockItem(generator, ApolloBlocks.LUNAR_IRON_ORE);
        registerSimpleBlockItem(generator, ApolloBlocks.LUNAR_SOIL);
        registerSimpleBlockItem(generator, ApolloBlocks.LUNAR_STONE);
        registerSimpleBlockItem(generator, ApolloBlocks.METEORITE, "meteorite_cool");
        registerSimpleBlockItem(generator, ApolloBlocks.OIL_REFINERY);
        registerSimpleBlockItem(generator, ApolloBlocks.OILED_SAND);
        registerSimpleBlockItem(generator, ApolloBlocks.OXYGEN_PLANT, "oxygen_plant_stage3");
        registerSimpleBlockItem(generator, ApolloBlocks.REINFORCED_IRON_BLOCK);
        registerSimpleBlockItem(generator, ApolloBlocks.SHUTTLE_WORKBENCH);
        registerSimpleBlockItem(generator, ApolloBlocks.STORAGE_TANK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        registerGeneratedItem(generator, ApolloItems.FUEL_BOTTLE);
        registerGeneratedItem(generator, ApolloItems.FUEL_BUCKET);
        registerGeneratedItem(generator, ApolloItems.METEORITE_SCRAP);
        registerGeneratedItem(generator, ApolloItems.OIL_BOTTLE);
        registerGeneratedItem(generator, ApolloItems.OIL_BUCKET);
        registerGeneratedItem(generator, ApolloItems.OXYGEN_PLANT_SEEDS);
        registerGeneratedItem(generator, ApolloItems.NEGATIVE_GRAVITY_BOOTS);
        registerGeneratedItem(generator, ApolloItems.POSITIVE_GRAVITY_BOOTS);
        registerGeneratedItem(generator, ApolloItems.REINFORCED_IRON_INGOT);
        registerGeneratedItem(generator, ApolloItems.SPACE_SUIT_BOOTS);
        registerGeneratedItem(generator, ApolloItems.SPACE_SUIT_CHESTPLATE);
        registerGeneratedItem(generator, ApolloItems.SPACE_SUIT_HELMET);
        registerGeneratedItem(generator, ApolloItems.SPACE_SUIT_LEGGINGS);
    }

    private static void registerSimpleBlockItem(BlockStateModelGenerator generator, Block block) {
        Identifier parentIdentifier = block.getRegistryEntry().registryKey().getValue();
        generator.registerParentedItemModel(block, new Identifier(parentIdentifier.getNamespace(), "block/" + parentIdentifier.getPath()));
    }

    private static void registerSimpleBlockItem(BlockStateModelGenerator generator, Block block, String blockModel) {
        generator.registerParentedItemModel(block, Apollo.getIdentifier("block/" + blockModel));
    }

    private static void registerGeneratedItem(ItemModelGenerator generator, Item item) {
        generator.register(item, Models.GENERATED);
    }
}
