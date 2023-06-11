package com.thepinkhacker.apollo.client.render.entity.model;

import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;

public abstract class ApolloEntityModel<T extends Entity> extends EntityModel<T> {
    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {}
}
