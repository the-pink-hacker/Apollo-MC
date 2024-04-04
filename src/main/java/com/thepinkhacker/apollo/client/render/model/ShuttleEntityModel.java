package com.thepinkhacker.apollo.client.render.model;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.entity.vehicle.ShuttleEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShuttleEntityModel extends GeoModel<ShuttleEntity> {
    @Override
    public Identifier getModelResource(ShuttleEntity object) {
        return Apollo.getIdentifier("geo/entity/shuttle.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShuttleEntity object) {
        return Apollo.getIdentifier("textures/entity/shuttle.png");
    }

    @Override
    public Identifier getAnimationResource(ShuttleEntity animatable) {
        return Apollo.getIdentifier("animations/entity/shuttle.animation.json");
    }
}
