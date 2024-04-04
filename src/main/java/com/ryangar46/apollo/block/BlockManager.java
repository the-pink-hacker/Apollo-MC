package com.ryangar46.apollo.block;

import com.ryangar46.apollo.Apollo;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockManager {
    public static final Block LUNAR_SOIL = new Block(FabricBlockSettings.of(Material.SOIL).strength(1.0f));
    public static final Block LUNAR_STONE = new Block(FabricBlockSettings.of(Material.STONE).strength(1.5f, 2.0f));

    public static void register() {
        Apollo.LOGGER.info("Registering blocks");
        registerBlock(LUNAR_SOIL, "lunar_soil", ItemGroup.BUILDING_BLOCKS);
        registerBlock(LUNAR_STONE, "lunar_stone", ItemGroup.BUILDING_BLOCKS);
    }

    private static void registerBlock(Block block, String id, ItemGroup itemGroup) {
        Registry.register(Registry.BLOCK, new Identifier(Apollo.MOD_ID, id), block);
        Registry.register(Registry.ITEM, new Identifier(Apollo.MOD_ID, id), new BlockItem(block, new FabricItemSettings().group(itemGroup)));
    }
}
