package com.ryangar46.apollo.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class ShuttleEntity extends MobEntity {
    public ShuttleEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }
}
