package com.ryangar46.apollo.client.render;

import com.ryangar46.apollo.client.render.model.MeteoriteEntityModel;
import com.ryangar46.apollo.entity.projectile.MeteoriteEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class MeteoriteEntityRenderer extends GeoProjectilesRenderer<MeteoriteEntity> {
    public MeteoriteEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MeteoriteEntityModel());
    }
}
