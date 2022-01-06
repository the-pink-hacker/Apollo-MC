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

    public static void register() {
        Apollo.LOGGER.info("Registering items");
        registerBlock(LUNAR_SOIL, Apollo.MOD_ID, "lunar_soil", ItemGroup.BUILDING_BLOCKS);
    }

    public static void registerBlock(Block block, String namespace, String id, ItemGroup itemGroup) {
        Registry.register(Registry.BLOCK, new Identifier(namespace, id), block);
        Registry.register(Registry.ITEM, new Identifier(namespace, id), new BlockItem(block, new FabricItemSettings().group(itemGroup)));
    }
}
