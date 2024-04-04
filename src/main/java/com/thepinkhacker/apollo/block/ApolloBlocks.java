package com.thepinkhacker.apollo.block;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.block.crop.OxygenPlantBlock;
import com.thepinkhacker.apollo.block.fluid.FluidPipeBlock;
import com.thepinkhacker.apollo.block.fluid.FluidValvePipeBlock;
import com.thepinkhacker.apollo.block.fluid.StorageTankBlock;
import com.thepinkhacker.apollo.fluid.ApolloFluids;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ApolloBlocks {
    public static final Block AIRLOCK_CONTROLLER = registerBlock("airlock_controller", new AirlockControllerBlock(FabricBlockSettings.of(Material.METAL).strength(50.0f, 12_000.0f).requiresTool()));
    public static final Block AIRLOCK_FRAME = registerBlock("airlock_frame", new Block(FabricBlockSettings.of(Material.METAL).strength(50.0f, 12_000f).requiresTool()));
    public static final Block AIRLOCK = registerBlock("airlock", new AirLockBlock(FabricBlockSettings.of(Material.METAL).strength(-1.0f)));
    public static final Block FLUID_PIPE = registerBlock("fluid_pipe", new FluidPipeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0f, 6.0f)));
    public static final Block FLUID_VALVE_PIPE = registerBlock("fluid_valve_pipe", new FluidValvePipeBlock(FabricBlockSettings.copyOf(FLUID_PIPE)));
    public static final Block FUEL = registerBlock("fuel", new FluidBlock(ApolloFluids.STILL_FUEL, FabricBlockSettings.of(Material.WATER).noCollision().dropsNothing()));
    public static final Block LAUNCHPAD = registerBlock("launchpad", new LaunchpadBlock(FabricBlockSettings.of(Material.METAL).strength(50.0f, 12_000.0f).requiresTool()));
    public static final Block LUNAR_DUST = registerBlock("lunar_dust", new FallingBlock(FabricBlockSettings.of(Material.SOIL).strength(1.0f)));
    public static final Block LUNAR_COBBLESTONE = registerBlock("lunar_cobblestone", new Block(FabricBlockSettings.of(Material.STONE).strength(1.75f, 1.5f).requiresTool()));
    public static final Block LUNAR_IRON_ORE = registerBlock("lunar_iron_ore", new Block(FabricBlockSettings.of(Material.STONE).strength(3.5f, 2.5f).requiresTool()));
    public static final Block LUNAR_SOIL = registerBlock("lunar_soil", new Block(FabricBlockSettings.of(Material.SOIL).strength(1.0f)));
    public static final Block LUNAR_STONE = registerBlock("lunar_stone", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5f).requiresTool()));
    public static final Block METEORITE = registerBlock("meteorite", new MeteoriteBlock(FabricBlockSettings.of(Material.STONE).strength(30.0f, 1_200.0f).ticksRandomly().requiresTool()));
    public static final Block OIL = registerBlock("oil", new FluidBlock(ApolloFluids.STILL_OIL, FabricBlockSettings.of(Material.LAVA).noCollision().dropsNothing()));
    public static final Block OIL_REFINERY = registerBlock("oil_refinery", new OilRefineryBlock(FabricBlockSettings.of(Material.METAL).strength(5.0f, 6.0f).requiresTool()));
    public static final Block OXYGEN_PLANT = registerBlock("oxygen_plant", new OxygenPlantBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP)));
    public static final Block REINFORCED_IRON_BLOCK = registerBlock("reinforced_iron_block", new Block(FabricBlockSettings.copyOf(Blocks.NETHERITE_BLOCK)));
    public static final Block SHUTTLE_WORKBENCH = registerBlock("shuttle_workbench", new ShuttleWorkbenchBlock(FabricBlockSettings.of(Material.METAL).strength(5.0f, 6.0f).requiresTool()));
    public static final Block STORAGE_TANK = registerBlock("storage_tank", new StorageTankBlock(FabricBlockSettings.of(Material.METAL).strength(5.0f, 6.0f).requiresTool()));

    private static Block registerBlock(String id, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(Apollo.MOD_ID, id), block);
    }
}
