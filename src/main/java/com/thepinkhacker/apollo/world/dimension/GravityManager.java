package com.thepinkhacker.apollo.world.dimension;

import com.thepinkhacker.apollo.item.ApolloArmorMaterials;
import com.thepinkhacker.apollo.resource.SpaceBodyManager;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GravityManager {
    public static final double DEFAULT = 1.0d;
    public static final double LOW = 0.165d;

    public static double getGravityMultiplier(World world) {
        return SpaceBodyManager
                .getInstance()
                .getSpaceBodyOrDefault(world)
                .getGravity();
    }

    public static double getGravityMultiplier(World world, Iterable<ItemStack> armor) {
        for (ItemStack item : armor) {
            if (item.getItem() instanceof ArmorItem armorItem) {
                ArmorMaterial material = armorItem.getMaterial();
                if (material == ApolloArmorMaterials.GRAVITY_NEGATIVE) return LOW;
                if (material == ApolloArmorMaterials.GRAVITY_POSITIVE) return DEFAULT;
            }
        }

        return getGravityMultiplier(world);
    }
}
