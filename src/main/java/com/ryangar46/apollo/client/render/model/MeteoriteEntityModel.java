package com.ryangar46.apollo.client.render.model;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.entity.projectile.MeteoriteEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MeteoriteEntityModel extends GeoModel<MeteoriteEntity> {
    @Override
    public Identifier getModelResource(MeteoriteEntity object) {
        return new Identifier(Apollo.MOD_ID, "geo/entity/meteorite.geo.json");
    }

    @Override
    public Identifier getTextureResource(MeteoriteEntity object) {
        return new Identifier(Apollo.MOD_ID, "textures/entity/meteorite.png");
    }

    @Override
    public Identifier getAnimationResource(MeteoriteEntity animatable) {
        return new Identifier(Apollo.MOD_ID, "animations/entity/meteorite.animation.json");
    }
}
