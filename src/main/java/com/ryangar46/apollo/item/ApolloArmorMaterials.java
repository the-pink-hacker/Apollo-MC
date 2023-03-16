package com.ryangar46.apollo.item;

import net.minecraft.item.ArmorMaterial;

public class ApolloArmorMaterials {
    public static final ArmorMaterial NEGATIVE_GRAVITY_ARMOR_MATERIAL = new GravityArmor(GravityArmor.Type.NEGATIVE);
    public static final ArmorMaterial POSITIVE_GRAVITY_ARMOR_MATERIAL = new GravityArmor(GravityArmor.Type.POSITIVE);
    public static final ArmorMaterial SPACE_SUIT_ARMOR_MATERIAL = new SpaceSuitArmor();
}
