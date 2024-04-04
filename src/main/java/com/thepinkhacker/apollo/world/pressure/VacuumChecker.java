package com.thepinkhacker.apollo.world.pressure;

import com.thepinkhacker.apollo.registry.tag.ApolloEntityTypeTags;
import com.thepinkhacker.apollo.registry.tag.ApolloItemTags;
import com.thepinkhacker.apollo.world.ApolloGameRules;
import com.thepinkhacker.apollo.world.ApolloWorlds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class VacuumChecker {
    public static boolean checkForVacuum(World world, LivingEntity entity) {
        boolean onMoon = world.getRegistryKey() == ApolloWorlds.MOON;
        boolean suffocateInVacuum = world.getGameRules().getBoolean(ApolloGameRules.SUFFOCATE_IN_VACUUM);
        boolean vacuumImmune = VacuumChecker.isVacuumImmune(entity);
        return onMoon && suffocateInVacuum && !vacuumImmune;
    }

    public static boolean isVacuumImmune(LivingEntity entity) {
        return entity.getType().isIn(ApolloEntityTypeTags.VACUUM_IMMUNE_CREATURES);
    }

    public static boolean isArmorAirtight(ItemStack item) {
        return item.isIn(ApolloItemTags.AIRTIGHT_ARMOR);
    }

    public static boolean isArmorCollectionAirtight(Iterable<ItemStack> items) {
        boolean airtight = true;

        for (ItemStack item : items) {
            if (isArmorAirtight(item)) {
                airtight = false;
                break;
            }
        }

        return airtight;
    }
}
