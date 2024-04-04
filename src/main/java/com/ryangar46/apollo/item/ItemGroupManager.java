package com.ryangar46.apollo.item;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.block.BlockManager;
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
                entries.add(BlockManager.FLUID_PIPE);
                entries.add(BlockManager.OIL_REFINERY);
                entries.add(BlockManager.STORAGE_TANK);
                entries.add(BlockManager.METEORITE);
                entries.add(ItemManager.METEORITE_SCRAP);
                entries.add(BlockManager.REINFORCED_IRON_BLOCK);
                entries.add(ItemManager.NEGATIVE_GRAVITY_BOOTS);
                entries.add(ItemManager.POSITIVE_GRAVITY_BOOTS);
                entries.add(ItemManager.REINFORCED_IRON_INGOT);
                entries.add(ItemManager.SPACE_SUIT_HELMET);
                entries.add(ItemManager.SPACE_SUIT_CHESTPLATE);
                entries.add(ItemManager.SPACE_SUIT_LEGGINGS);
                entries.add(ItemManager.SPACE_SUIT_BOOTS);
                entries.add(BlockManager.LAUNCH_PAD);
                entries.add(BlockManager.SHUTTLE_WORKBENCH);
            }))
            .build();

    public static final ItemGroup MOON = FabricItemGroup.builder(new Identifier(Apollo.MOD_ID, "moon"))
            .displayName(Text.translatable("itemGroup.apollo.moon"))
            .icon(() -> new ItemStack(BlockManager.LUNAR_SOIL))
            .entries(((enabledFeatures, entries, operatorEnabled) -> {
                entries.add(BlockManager.LUNAR_DUST);
                entries.add(BlockManager.LUNAR_SOIL);
                entries.add(BlockManager.LUNAR_STONE);
                entries.add(BlockManager.LUNAR_COBBLESTONE);
                entries.add(BlockManager.LUNAR_IRON_ORE);
            }))
            .build();
}
