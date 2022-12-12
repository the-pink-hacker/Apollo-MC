package com.ryangar46.apollo.client.render;

import com.ryangar46.apollo.client.render.model.ShuttleEntityModel;
import com.ryangar46.apollo.entity.vehicle.ShuttleEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShuttleEntityRenderer extends GeoEntityRenderer<ShuttleEntity> {
    public ShuttleEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ShuttleEntityModel());
    }
}
