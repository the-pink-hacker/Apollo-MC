package com.thepinkhacker.apollo.client.render.model;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.entity.projectile.MeteoriteEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MeteoriteEntityModel extends GeoModel<MeteoriteEntity> {
    @Override
    public Identifier getModelResource(MeteoriteEntity object) {
        return Apollo.getIdentifier("geo/entity/meteorite.geo.json");
    }

    @Override
    public Identifier getTextureResource(MeteoriteEntity object) {
        return Apollo.getIdentifier("textures/entity/meteorite.png");
    }

    @Override
    public Identifier getAnimationResource(MeteoriteEntity animatable) {
        return Apollo.getIdentifier("animations/entity/meteorite.animation.json");
    }
}
