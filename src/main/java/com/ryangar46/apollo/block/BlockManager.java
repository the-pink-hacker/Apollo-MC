package com.ryangar46.apollo.block;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.fluid.FluidManager;
import com.ryangar46.apollo.item.ItemGroupManager;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockManager {
    public static final Block FLUID_PIPE = new FluidPipeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0f, 6.0f));
    public static final Block FUEL = new FluidBlock(FluidManager.STILL_FUEL, FabricBlockSettings.of(Material.WATER).noCollision().dropsNothing());
    public static final Block LAUNCH_PAD = new LaunchpadBlock(FabricBlockSettings.of(Material.METAL).strength(50.0f, 12000.0f).requiresTool());
    public static final Block LUNAR_DUST = new FallingBlock(FabricBlockSettings.of(Material.SOIL).strength(1.0f));
    public static final Block LUNAR_COBBLESTONE = new Block(FabricBlockSettings.of(Material.STONE).strength(1.75f, 1.5f).requiresTool());
    public static final Block LUNAR_IRON_ORE = new Block(FabricBlockSettings.of(Material.STONE).strength(3.5f, 2.5f).requiresTool());
    public static final Block LUNAR_SOIL = new Block(FabricBlockSettings.of(Material.SOIL).strength(1.0f));
    public static final Block LUNAR_STONE = new Block(FabricBlockSettings.of(Material.STONE).strength(1.5f).requiresTool());
    public static final Block METEORITE = new MeteoriteBlock(FabricBlockSettings.of(Material.STONE).strength(30.0f, 1200.0f).ticksRandomly().requiresTool());
    public static final Block OIL = new FluidBlock(FluidManager.STILL_OIL, FabricBlockSettings.of(Material.LAVA).noCollision().dropsNothing());
    public static final Block OIL_REFINERY = new OilRefineryBlock(FabricBlockSettings.of(Material.METAL).strength(5.0f, 6.0f).requiresTool());
    public static final Block REINFORCED_IRON_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.NETHERITE_BLOCK));
    public static final Block SHUTTLE_WORKBENCH = new ShuttleWorkbenchBlock(FabricBlockSettings.of(Material.METAL).strength(5.0f, 6.0f).requiresTool());
    public static final Block STORAGE_TANK = new StorageTankBlock(FabricBlockSettings.of(Material.METAL).strength(5.0f, 6.0f).requiresTool());

    public static void register() {
        Apollo.LOGGER.info("Registering blocks");
        registerBlock(FLUID_PIPE, "fluid_pipe", ItemGroupManager.APOLLO);
        registerBlock(FUEL, "fuel");
        registerBlock(LAUNCH_PAD, "launchpad", ItemGroupManager.APOLLO);
        registerBlock(LUNAR_DUST, "lunar_dust", ItemGroupManager.MOON);
        registerBlock(LUNAR_COBBLESTONE, "lunar_cobblestone", ItemGroupManager.MOON);
        registerBlock(LUNAR_IRON_ORE, "lunar_iron_ore", ItemGroupManager.MOON);
        registerBlock(LUNAR_SOIL, "lunar_soil", ItemGroupManager.MOON);
        registerBlock(LUNAR_STONE, "lunar_stone", ItemGroupManager.MOON);
        registerBlock(METEORITE, "meteorite", new FabricItemSettings().fireproof().group(ItemGroupManager.APOLLO));
        registerBlock(OIL, "oil");
        registerBlock(OIL_REFINERY, "oil_refinery", ItemGroupManager.APOLLO);
        registerBlock(REINFORCED_IRON_BLOCK, "reinforced_iron_block", new FabricItemSettings().fireproof().group(ItemGroupManager.APOLLO));
        registerBlock(SHUTTLE_WORKBENCH, "shuttle_workbench", ItemGroupManager.APOLLO);
        registerBlock(STORAGE_TANK, "storage_tank", ItemGroupManager.APOLLO);
    }

    private static void registerBlock(Block block, String id, ItemGroup itemGroup) {
        registerBlock(block, id, new FabricItemSettings().group(itemGroup));
    }
    private static void registerBlock(Block block, String id, Item.Settings settings) {
        registerBlock(block, id);
        Registry.register(Registry.ITEM, new Identifier(Apollo.MOD_ID, id), new BlockItem(block, settings));
    }
    private static void registerBlock(Block block, String id) {
        Registry.register(Registry.BLOCK, new Identifier(Apollo.MOD_ID, id), block);
    }
}
