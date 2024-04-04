package com.ryangar46.apollo.block;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.item.ItemGroupManager;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockManager {
    public static final Block LUNAR_COBBLESTONE = new Block(FabricBlockSettings.of(Material.STONE).strength(1.75f, 1.5f).requiresTool());
    public static final Block LUNAR_IRON_ORE = new Block(FabricBlockSettings.of(Material.STONE).strength(3.5f, 2.5f).requiresTool());
    public static final Block LUNAR_SOIL = new Block(FabricBlockSettings.of(Material.SOIL).strength(1.0f));
    public static final Block LUNAR_STONE = new Block(FabricBlockSettings.of(Material.STONE).strength(1.5f).requiresTool());

    public static void register() {
        Apollo.LOGGER.info("Registering blocks");
        registerBlock(LUNAR_COBBLESTONE, "lunar_cobblestone", ItemGroupManager.MOON);
        registerBlock(LUNAR_IRON_ORE, "lunar_iron_ore", ItemGroupManager.MOON);
        registerBlock(LUNAR_SOIL, "lunar_soil", ItemGroupManager.MOON);
        registerBlock(LUNAR_STONE, "lunar_stone", ItemGroupManager.MOON);
    }

    private static void registerBlock(Block block, String id, ItemGroup itemGroup) {
        Registry.register(Registry.BLOCK, new Identifier(Apollo.MOD_ID, id), block);
        Registry.register(Registry.ITEM, new Identifier(Apollo.MOD_ID, id), new BlockItem(block, new FabricItemSettings().group(itemGroup)));
    }
}
