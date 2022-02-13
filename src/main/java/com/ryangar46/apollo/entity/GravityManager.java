package com.ryangar46.apollo.entity;

import com.ryangar46.apollo.world.dimension.DimensionManager;
import net.minecraft.world.World;

public class GravityManager {
    public static final double DEFAULT_GRAVITY = 0.08d;

    public static double getGravity(World world) {
        if (world.getRegistryKey() == DimensionManager.MOON) {
            return DEFAULT_GRAVITY * 0.165d;
        } else {
            return DEFAULT_GRAVITY;
        }
    }
}
