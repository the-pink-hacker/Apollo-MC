package com.ryangar46.apollo.item;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.fluid.FluidManager;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemManager {
    public static final Item FUEL_BUCKET = new BucketItem(FluidManager.STILL_FUEL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ItemGroupManager.APOLLO));
    public static final Item OIL_BUCKET = new BucketItem(FluidManager.STILL_OIL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ItemGroupManager.APOLLO));
    public static final ArmorMaterial SPACE_SUIT_ARMOR_MATERIAL = new SpaceSuitArmor();
    public static final Item SPACE_SUIT_HELMET = new ArmorItem(SPACE_SUIT_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroupManager.APOLLO));
    public static final Item SPACE_SUIT_CHESTPLATE = new ArmorItem(SPACE_SUIT_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroupManager.APOLLO));
    public static final Item SPACE_SUIT_LEGGINGS = new ArmorItem(SPACE_SUIT_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroupManager.APOLLO));
    public static final Item SPACE_SUIT_BOOTS = new ArmorItem(SPACE_SUIT_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroupManager.APOLLO));

    public static void register() {
        Apollo.LOGGER.info("Registering items");
        registerItem("fuel_bucket", FUEL_BUCKET);
        registerItem("oil_bucket", OIL_BUCKET);
        registerItem("space_suit_helmet", SPACE_SUIT_HELMET);
        registerItem("space_suit_chestplate", SPACE_SUIT_CHESTPLATE);
        registerItem("space_suit_leggings", SPACE_SUIT_LEGGINGS);
        registerItem("space_suit_boots", SPACE_SUIT_BOOTS);
    }

    private static void registerItem(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(Apollo.MOD_ID, id), item);
    }
}
