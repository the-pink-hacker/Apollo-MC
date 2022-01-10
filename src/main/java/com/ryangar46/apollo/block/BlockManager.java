package com.ryangar46.apollo.block;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.fluid.FluidManager;
import com.ryangar46.apollo.item.ItemGroupManager;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockManager {
    public static final Block FUEL = new FluidBlock(FluidManager.STILL_FUEL, FabricBlockSettings.of(Material.WATER).noCollision().dropsNothing()){};
    public static final Block LAUNCH_PAD = new LaunchpadBlock(FabricBlockSettings.of(Material.METAL).strength(50.0f, 12000.0f).requiresTool());
    public static final Block LUNAR_COBBLESTONE = new Block(FabricBlockSettings.of(Material.STONE).strength(1.75f, 1.5f).requiresTool());
    public static final Block LUNAR_IRON_ORE = new Block(FabricBlockSettings.of(Material.STONE).strength(3.5f, 2.5f).requiresTool());
    public static final Block LUNAR_SOIL = new Block(FabricBlockSettings.of(Material.SOIL).strength(1.0f));
    public static final Block LUNAR_STONE = new Block(FabricBlockSettings.of(Material.STONE).strength(1.5f).requiresTool());
    public static final Block OIL = new FluidBlock(FluidManager.STILL_OIL, FabricBlockSettings.of(Material.LAVA).noCollision().dropsNothing()){};
    public static final Block SHUTTLE_COCKPIT = new ShuttleCockpit(FabricBlockSettings.of(Material.METAL).strength(5, 6).requiresTool());
    public static final Block SHUTTLE_ENGINE = new ShuttleEngineBlock(FabricBlockSettings.of(Material.METAL).strength(5, 6).requiresTool());
    public static final Block SHUTTLE_FUEL_STORAGE = new Block(FabricBlockSettings.of(Material.METAL).strength(5, 6).requiresTool());
    public static final Block SHUTTLE_NOSE = new ShuttleNoseBlock(FabricBlockSettings.of(Material.METAL).strength(5, 6).requiresTool());

    public static void register() {
        Apollo.LOGGER.info("Registering blocks");
        registerBlock(LAUNCH_PAD, "launchpad", ItemGroupManager.APOLLO);
        registerBlock(LUNAR_COBBLESTONE, "lunar_cobblestone", ItemGroupManager.MOON);
        registerBlock(LUNAR_IRON_ORE, "lunar_iron_ore", ItemGroupManager.MOON);
        registerBlock(LUNAR_SOIL, "lunar_soil", ItemGroupManager.MOON);
        registerBlock(LUNAR_STONE, "lunar_stone", ItemGroupManager.MOON);
        registerBlock(OIL, "oil");
        registerBlock(SHUTTLE_COCKPIT, "shuttle_cockpit", ItemGroupManager.APOLLO);
        registerBlock(SHUTTLE_ENGINE, "shuttle_engine", ItemGroupManager.APOLLO);
        registerBlock(SHUTTLE_FUEL_STORAGE, "shuttle_fuel_storage", ItemGroupManager.APOLLO);
        registerBlock(SHUTTLE_NOSE, "shuttle_nose", ItemGroupManager.APOLLO);
    }

    private static void registerBlock(Block block, String id, ItemGroup itemGroup) {
        Registry.register(Registry.BLOCK, new Identifier(Apollo.MOD_ID, id), block);
        Registry.register(Registry.ITEM, new Identifier(Apollo.MOD_ID, id), new BlockItem(block, new FabricItemSettings().group(itemGroup)));
    }
    private static void registerBlock(Block block, String id) {
        Registry.register(Registry.BLOCK, new Identifier(Apollo.MOD_ID, id), block);
    }
}
