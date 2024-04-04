package com.ryangar46.apollo.item;

import com.ryangar46.apollo.Apollo;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemManager {
    public static final ArmorMaterial SPACE_SUIT_ARMOR_MATERIAL = new SpaceSuitArmor();
    public static final Item SPACE_SUIT_HELMET = new ArmorItem(SPACE_SUIT_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item SPACE_SUIT_CHESTPLATE = new ArmorItem(SPACE_SUIT_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item SPACE_SUIT_LEGGINGS = new ArmorItem(SPACE_SUIT_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item SPACE_SUIT_BOOTS = new ArmorItem(SPACE_SUIT_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    public static void register() {
        Apollo.LOGGER.info("Registering items");
        registerItem("space_suit_helmet", SPACE_SUIT_HELMET);
        registerItem("space_suit_chestplate", SPACE_SUIT_CHESTPLATE);
        registerItem("space_suit_leggings", SPACE_SUIT_LEGGINGS);
        registerItem("space_suit_boots", SPACE_SUIT_BOOTS);
    }

    private static void registerItem(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(Apollo.MOD_ID, id), item);
    }
}
