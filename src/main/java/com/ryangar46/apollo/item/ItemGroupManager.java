package com.ryangar46.apollo.item;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.block.ApolloBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemGroupManager {
    public static final ItemGroup APOLLO = FabricItemGroup.builder(new Identifier(Apollo.MOD_ID, "apollo"))
            .displayName(Text.translatable("itemGroup.apollo.apollo"))
            .icon(() -> new ItemStack(ItemManager.SPACE_SUIT_HELMET))
            .entries(((enabledFeatures, entries, operatorEnabled) -> {
                entries.add(ItemManager.FUEL_BUCKET);
                entries.add(ItemManager.OIL_BUCKET);
                entries.add(ApolloBlocks.FLUID_PIPE);
                entries.add(ApolloBlocks.OIL_REFINERY);
                entries.add(ApolloBlocks.STORAGE_TANK);
                entries.add(ApolloBlocks.METEORITE);
                entries.add(ItemManager.METEORITE_SCRAP);
                entries.add(ApolloBlocks.REINFORCED_IRON_BLOCK);
                entries.add(ItemManager.NEGATIVE_GRAVITY_BOOTS);
                entries.add(ItemManager.POSITIVE_GRAVITY_BOOTS);
                entries.add(ItemManager.REINFORCED_IRON_INGOT);
                entries.add(ItemManager.SPACE_SUIT_HELMET);
                entries.add(ItemManager.SPACE_SUIT_CHESTPLATE);
                entries.add(ItemManager.SPACE_SUIT_LEGGINGS);
                entries.add(ItemManager.SPACE_SUIT_BOOTS);
                entries.add(ApolloBlocks.LAUNCH_PAD);
                entries.add(ApolloBlocks.SHUTTLE_WORKBENCH);
            }))
            .build();

    public static final ItemGroup MOON = FabricItemGroup.builder(new Identifier(Apollo.MOD_ID, "moon"))
            .displayName(Text.translatable("itemGroup.apollo.moon"))
            .icon(() -> new ItemStack(ApolloBlocks.LUNAR_SOIL))
            .entries(((enabledFeatures, entries, operatorEnabled) -> {
                entries.add(ApolloBlocks.LUNAR_DUST);
                entries.add(ApolloBlocks.LUNAR_SOIL);
                entries.add(ApolloBlocks.LUNAR_STONE);
                entries.add(ApolloBlocks.LUNAR_COBBLESTONE);
                entries.add(ApolloBlocks.LUNAR_IRON_ORE);
            }))
            .build();
}
