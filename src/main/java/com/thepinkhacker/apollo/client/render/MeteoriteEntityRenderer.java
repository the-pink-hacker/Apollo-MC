package com.thepinkhacker.apollo.client.render;

import com.thepinkhacker.apollo.client.render.model.MeteoriteEntityModel;
import com.thepinkhacker.apollo.entity.projectile.MeteoriteEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MeteoriteEntityRenderer extends GeoEntityRenderer<MeteoriteEntity> {
    public MeteoriteEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MeteoriteEntityModel());
    }
}
