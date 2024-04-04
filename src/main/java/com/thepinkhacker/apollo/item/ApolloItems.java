package com.thepinkhacker.apollo.item;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.block.ApolloBlocks;
import com.thepinkhacker.apollo.fluid.ApolloFluids;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ApolloItems {
    /* === Items === */
    public static final Item FUEL_BUCKET = registerItem("fuel_bucket", new BucketItem(ApolloFluids.STILL_FUEL, new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)));
    public static final Item METEORITE_SCRAP = registerItem("meteorite_scrap", new Item(new FabricItemSettings().fireproof()));
    public static final Item OIL_BOTTLE = registerItem("oil_bottle", new OilBottleItem(new FabricItemSettings().recipeRemainder(Items.GLASS_BOTTLE).maxCount(1)));
    public static final Item OIL_BUCKET = registerItem("oil_bucket", new BucketItem(ApolloFluids.STILL_OIL, new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)));
    public static final Item OXYGEN_PLANT_SEEDS = registerItem("oxygen_plant_seeds", new AliasedBlockItem(ApolloBlocks.OXYGEN_PLANT, new FabricItemSettings()));
    public static final Item NEGATIVE_GRAVITY_BOOTS = registerItem("negative_gravity_boots", new ArmorItem(ApolloArmorMaterials.GRAVITY_NEGATIVE, ArmorItem.Type.BOOTS, new FabricItemSettings()));
    public static final Item POSITIVE_GRAVITY_BOOTS = registerItem("positive_gravity_boots", new ArmorItem(ApolloArmorMaterials.GRAVITY_POSITIVE, ArmorItem.Type.BOOTS, new FabricItemSettings()));
    public static final Item REINFORCED_IRON_INGOT = registerItem("reinforced_iron_ingot", new Item(new FabricItemSettings().fireproof()));
    public static final Item SPACE_SUIT_HELMET = registerItem("space_suit_helmet", new ArmorItem(ApolloArmorMaterials.SPACE_SUIT, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item SPACE_SUIT_CHESTPLATE = registerItem("space_suit_chestplate", new ArmorItem(ApolloArmorMaterials.SPACE_SUIT, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item SPACE_SUIT_LEGGINGS = registerItem("space_suit_leggings", new ArmorItem(ApolloArmorMaterials.SPACE_SUIT, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item SPACE_SUIT_BOOTS = registerItem("space_suit_boots", new ArmorItem(ApolloArmorMaterials.SPACE_SUIT, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    /* === Blocks === */
    public static final Item AIRLOCK_CONTROLLER = registerItem(ApolloBlocks.AIRLOCK_CONTROLLER);
    public static final Item AIRLOCK_FRAME = registerItem(ApolloBlocks.AIRLOCK_FRAME);
    public static final Item FLUID_PIPE = registerItem(ApolloBlocks.FLUID_PIPE);
    public static final Item FLUID_VALVE_PIPE = registerItem(ApolloBlocks.FLUID_VALVE_PIPE);
    public static final Item LAUNCHPAD = registerItem(ApolloBlocks.LAUNCHPAD);
    public static final Item LUNAR_DUST = registerItem(ApolloBlocks.LUNAR_DUST);
    public static final Item LUNAR_COBBLESTONE = registerItem(ApolloBlocks.LUNAR_COBBLESTONE);
    public static final Item LUNAR_IRON_ORE = registerItem(ApolloBlocks.LUNAR_IRON_ORE);
    public static final Item LUNAR_SOIL = registerItem(ApolloBlocks.LUNAR_SOIL);
    public static final Item LUNAR_STONE = registerItem(ApolloBlocks.LUNAR_STONE);
    public static final Item METEORITE = registerItem(new BlockItem(ApolloBlocks.METEORITE, new FabricItemSettings().fireproof()));
    public static final Item OIL_REFINERY = registerItem(ApolloBlocks.OIL_REFINERY);
    public static final Item OILED_SAND = registerItem(ApolloBlocks.OILED_SAND);
    public static final Item OXYGEN_PLANT = registerItem(ApolloBlocks.OXYGEN_PLANT);
    public static final Item REINFORCED_IRON_BLOCK = registerItem(new BlockItem(ApolloBlocks.REINFORCED_IRON_BLOCK, new FabricItemSettings().fireproof()));
    public static final Item SHUTTLE_WORKBENCH = registerItem(ApolloBlocks.SHUTTLE_WORKBENCH);
    public static final Item STORAGE_TANK = registerItem(ApolloBlocks.STORAGE_TANK);

    public static void register() {
        Apollo.LOGGER.info("Registering items");

        // Add fuels
        FuelRegistry.INSTANCE.add(FUEL_BUCKET, 20_000);
    }

    private static Item registerItem(Block block) {
        return registerItem(new BlockItem(block, new FabricItemSettings()));
    }

    private static Item registerItem(BlockItem item) {
        return registerItem(item.getBlock(), item);
    }

    protected static Item registerItem(Block block, Item item) {
        return registerItem(Registries.BLOCK.getId(block), item);
    }

    private static Item registerItem(String id, Item item) {
        return registerItem(Apollo.getIdentifier(id), item);
    }

    private static Item registerItem(Identifier id, Item item) {
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, id, item);
    }
}
