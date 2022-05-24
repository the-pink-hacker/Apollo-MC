package com.ryangar46.apollo.client.render.model;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.entity.vehicle.ShuttleEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShuttleEntityModel extends AnimatedGeoModel<ShuttleEntity> {
    @Override
    public Identifier getModelResource(ShuttleEntity object) {
        return new Identifier(Apollo.MOD_ID, "geo/entity/shuttle.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShuttleEntity object) {
        return new Identifier(Apollo.MOD_ID, "textures/entity/shuttle.png");
    }

    @Override
    public Identifier getAnimationResource(ShuttleEntity animatable) {
        return new Identifier(Apollo.MOD_ID, "animations/entity/shuttle.animation.json");
    }
}
