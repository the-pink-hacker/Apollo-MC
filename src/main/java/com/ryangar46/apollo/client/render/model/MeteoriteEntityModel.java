package com.ryangar46.apollo.client.render.model;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.entity.projectile.MeteoriteEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MeteoriteEntityModel extends AnimatedGeoModel<MeteoriteEntity> {
    @Override
    public Identifier getModelLocation(MeteoriteEntity object) {
        return new Identifier(Apollo.MOD_ID, "geo/entity/meteorite.geo.json");
    }

    @Override
    public Identifier getTextureLocation(MeteoriteEntity object) {
        return new Identifier(Apollo.MOD_ID, "textures/entity/meteorite.png");
    }

    @Override
    public Identifier getAnimationFileLocation(MeteoriteEntity animatable) {
        return new Identifier(Apollo.MOD_ID, "animations/entity/meteorite.animation.json");
    }
}
