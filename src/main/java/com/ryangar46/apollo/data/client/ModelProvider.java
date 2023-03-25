package com.ryangar46.apollo.data.client;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.item.ApolloItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {}

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        /* === Blocks === */
        registerBasicBlockItem(itemModelGenerator, ApolloItems.AIRLOCK_CONTROLLER);
        registerBasicBlockItem(itemModelGenerator, ApolloItems.AIRLOCK_FRAME);
        registerBasicBlockItem(itemModelGenerator, ApolloItems.FLUID_PIPE, "fluid_pipe_inv");
        registerBasicBlockItem(itemModelGenerator, ApolloItems.LAUNCHPAD, "launchpad_inv");
        registerBasicBlockItem(itemModelGenerator, ApolloItems.LUNAR_COBBLESTONE);
        registerBasicBlockItem(itemModelGenerator, ApolloItems.LUNAR_DUST);
        registerBasicBlockItem(itemModelGenerator, ApolloItems.LUNAR_IRON_ORE);
        registerBasicBlockItem(itemModelGenerator, ApolloItems.LUNAR_SOIL);
        registerBasicBlockItem(itemModelGenerator, ApolloItems.LUNAR_STONE);
        registerBasicBlockItem(itemModelGenerator, ApolloItems.METEORITE, "meteorite_cool");
        registerBasicBlockItem(itemModelGenerator, ApolloItems.OIL_REFINERY);
        registerBasicBlockItem(itemModelGenerator, ApolloItems.REINFORCED_IRON_BLOCK);
        registerBasicBlockItem(itemModelGenerator, ApolloItems.SHUTTLE_WORKBENCH);
        registerBasicBlockItem(itemModelGenerator, ApolloItems.STORAGE_TANK);

        /* === Generated === */
        itemModelGenerator.register(ApolloItems.FUEL_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ApolloItems.METEORITE_SCRAP, Models.GENERATED);
        itemModelGenerator.register(ApolloItems.OIL_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ApolloItems.NEGATIVE_GRAVITY_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ApolloItems.POSITIVE_GRAVITY_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ApolloItems.REINFORCED_IRON_INGOT, Models.GENERATED);
        itemModelGenerator.register(ApolloItems.SPACE_SUIT_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ApolloItems.SPACE_SUIT_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ApolloItems.SPACE_SUIT_HELMET, Models.GENERATED);
        itemModelGenerator.register(ApolloItems.SPACE_SUIT_LEGGINGS, Models.GENERATED);
    }

    private static Model basicBlockItem(Identifier parent) {
        return new Model(Optional.of(parent), Optional.empty());
    }

    private static Model basicBlockItem(String parent) {
        return basicBlockItem(new Identifier(Apollo.MOD_ID, "block/" + parent));
    }

    private static Model basicBlockItem(Item parent) {
        Identifier parentIdentifier = parent.getRegistryEntry().registryKey().getValue();
        return basicBlockItem(new Identifier(parentIdentifier.getNamespace(), "block/" + parentIdentifier.getPath()));
    }

    private static void registerBasicBlockItem(ItemModelGenerator itemModelGenerator, Item item) {
        itemModelGenerator.register(item, basicBlockItem(item));
    }

    private static void registerBasicBlockItem(ItemModelGenerator itemModelGenerator, Item item, String parent) {
        itemModelGenerator.register(item, basicBlockItem(parent));
    }

    private static void registerBasicBlockItem(ItemModelGenerator itemModelGenerator, Item item, Identifier parent) {
        itemModelGenerator.register(item, basicBlockItem(parent));
    }
}
