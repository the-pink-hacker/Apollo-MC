package com.ryangar46.apollo.entity;

import com.ryangar46.apollo.world.dimension.DimensionManager;
import net.minecraft.world.World;

public class GravityManager {
    public static double getGravityMultiplier(World world) {
        if (world.getRegistryKey() == DimensionManager.MOON) {
            return 0.165d;
        } else {
            return 1.0d;
        }
    }
}
