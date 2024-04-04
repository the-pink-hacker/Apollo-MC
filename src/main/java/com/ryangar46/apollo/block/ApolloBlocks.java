package com.ryangar46.apollo.block;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.fluid.FluidManager;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ApolloBlocks {
    public static final Block FLUID_PIPE = registerBlock("fluid_pipe", new FluidPipeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0f, 6.0f)));
    public static final Block FUEL = registerBlock("fuel", new FluidBlock(FluidManager.STILL_FUEL, FabricBlockSettings.of(Material.WATER).noCollision().dropsNothing()));
    public static final Block LAUNCH_PAD = registerBlock("launch_pad", new LaunchpadBlock(FabricBlockSettings.of(Material.METAL).strength(50.0f, 12000.0f).requiresTool()));
    public static final Block LUNAR_DUST = registerBlock("lunar_dust", new FallingBlock(FabricBlockSettings.of(Material.SOIL).strength(1.0f)));
    public static final Block LUNAR_COBBLESTONE = registerBlock("lunar_cobblestone", new Block(FabricBlockSettings.of(Material.STONE).strength(1.75f, 1.5f).requiresTool()));
    public static final Block LUNAR_IRON_ORE = registerBlock("lunar_iron_ore", new Block(FabricBlockSettings.of(Material.STONE).strength(3.5f, 2.5f).requiresTool()));
    public static final Block LUNAR_SOIL = registerBlock("lunar_soil", new Block(FabricBlockSettings.of(Material.SOIL).strength(1.0f)));
    public static final Block LUNAR_STONE = registerBlock("lunar_stone", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5f).requiresTool()));
    public static final Block METEORITE = registerBlock("meteorite", new MeteoriteBlock(FabricBlockSettings.of(Material.STONE).strength(30.0f, 1200.0f).ticksRandomly().requiresTool()));
    public static final Block OIL = registerBlock("oil", new FluidBlock(FluidManager.STILL_OIL, FabricBlockSettings.of(Material.LAVA).noCollision().dropsNothing()));
    public static final Block OIL_REFINERY = registerBlock("oil_refinery", new OilRefineryBlock(FabricBlockSettings.of(Material.METAL).strength(5.0f, 6.0f).requiresTool()));
    public static final Block REINFORCED_IRON_BLOCK = registerBlock("reinforced_iron_block", new Block(FabricBlockSettings.copyOf(Blocks.NETHERITE_BLOCK)));
    public static final Block SHUTTLE_WORKBENCH = registerBlock("shuttle_workbench", new ShuttleWorkbenchBlock(FabricBlockSettings.of(Material.METAL).strength(5.0f, 6.0f).requiresTool()));
    public static final Block STORAGE_TANK = registerBlock("storage_tank", new StorageTankBlock(FabricBlockSettings.of(Material.METAL).strength(5.0f, 6.0f).requiresTool()));

    private static Block registerBlock(String id, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(Apollo.MOD_ID, id), block);
    }
}
