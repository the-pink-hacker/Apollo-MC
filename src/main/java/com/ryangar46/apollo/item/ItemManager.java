package com.ryangar46.apollo.item;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.fluid.FluidManager;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemManager {
    public static final Item FUEL_BUCKET = new BucketItem(FluidManager.STILL_FUEL, new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1).group(ItemGroupManager.APOLLO));
    public static final Item METEORITE_SCRAP = new Item(new FabricItemSettings().fireproof().group(ItemGroupManager.APOLLO));
    public static final Item OIL_BUCKET = new BucketItem(FluidManager.STILL_OIL, new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1).group(ItemGroupManager.APOLLO));
    public static final ArmorMaterial NEGATIVE_GRAVITY_ARMOR_MATERIAL = new GravityArmor(GravityArmor.Type.NEGATIVE);
    public static final Item NEGATIVE_GRAVITY_BOOTS = new ArmorItem(NEGATIVE_GRAVITY_ARMOR_MATERIAL, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroupManager.APOLLO));
    public static final ArmorMaterial POSITIVE_GRAVITY_ARMOR_MATERIAL = new GravityArmor(GravityArmor.Type.POSITIVE);
    public static final Item POSITIVE_GRAVITY_BOOTS = new ArmorItem(POSITIVE_GRAVITY_ARMOR_MATERIAL, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroupManager.APOLLO));
    public static final ArmorMaterial SPACE_SUIT_ARMOR_MATERIAL = new SpaceSuitArmor();
    public static final Item REINFORCED_IRON_INGOT = new Item(new FabricItemSettings().fireproof().group(ItemGroupManager.APOLLO));
    public static final Item SPACE_SUIT_HELMET = new ArmorItem(SPACE_SUIT_ARMOR_MATERIAL, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroupManager.APOLLO));
    public static final Item SPACE_SUIT_CHESTPLATE = new ArmorItem(SPACE_SUIT_ARMOR_MATERIAL, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroupManager.APOLLO));
    public static final Item SPACE_SUIT_LEGGINGS = new ArmorItem(SPACE_SUIT_ARMOR_MATERIAL, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroupManager.APOLLO));
    public static final Item SPACE_SUIT_BOOTS = new ArmorItem(SPACE_SUIT_ARMOR_MATERIAL, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroupManager.APOLLO));

    public static void register() {
        Apollo.LOGGER.info("Registering items");
        registerItem("fuel_bucket", FUEL_BUCKET);
        registerItem("meteorite_scrap", METEORITE_SCRAP);
        registerItem("oil_bucket", OIL_BUCKET);
        registerItem("negative_gravity_boots", NEGATIVE_GRAVITY_BOOTS);
        registerItem("positive_gravity_boots", POSITIVE_GRAVITY_BOOTS);
        registerItem("reinforced_iron_ingot", REINFORCED_IRON_INGOT);
        registerItem("space_suit_helmet", SPACE_SUIT_HELMET);
        registerItem("space_suit_chestplate", SPACE_SUIT_CHESTPLATE);
        registerItem("space_suit_leggings", SPACE_SUIT_LEGGINGS);
        registerItem("space_suit_boots", SPACE_SUIT_BOOTS);
    }

    private static void registerItem(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(Apollo.MOD_ID, id), item);
    }
}
