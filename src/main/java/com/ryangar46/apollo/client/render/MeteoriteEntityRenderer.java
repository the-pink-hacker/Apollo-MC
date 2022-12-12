package com.ryangar46.apollo.client.render;

import com.ryangar46.apollo.client.render.model.MeteoriteEntityModel;
import com.ryangar46.apollo.entity.projectile.MeteoriteEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MeteoriteEntityRenderer extends GeoEntityRenderer<MeteoriteEntity> {
    public MeteoriteEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MeteoriteEntityModel());
    }
}
