package com.ryangar46.apollo.world.dimension;

import com.ryangar46.apollo.item.GravityArmor;
import com.ryangar46.apollo.tag.TagManager;
import com.ryangar46.apollo.world.ApolloWorlds;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GravityManager {
    public static double getGravityMultiplier(World world) {
        if (world.getRegistryKey() == ApolloWorlds.MOON) {
            return 0.165d;
        }
        return 1.0d;
    }
    public static double getGravityMultiplier(World world, Iterable<ItemStack> armor) {
        GravityArmor.Type type = GravityArmor.Type.NORMAL;
        for (ItemStack item : armor) {
            GravityArmor.Type t = getArmorGravityMultiplier(item);
            if (t != null) {
                type = t;
            }
        }

        if (type == GravityArmor.Type.NEGATIVE) return 0.165d;
        else if (type == GravityArmor.Type.POSITIVE) return 1.0d;
        return getGravityMultiplier(world);
    }

    public static GravityArmor.Type getArmorGravityMultiplier(ItemStack item) {
        if (!item.isEmpty()) {
            if (item.isIn(TagManager.NEGATIVE_GRAVITY_EQUIPABLES)) {
                return GravityArmor.Type.NEGATIVE;
            } else if (item.isIn(TagManager.POSITIVE_GRAVITY_EQUIPABLES)) {
                return GravityArmor.Type.POSITIVE;
            }
            return GravityArmor.Type.NORMAL;
        }
        return null;
    }
}
