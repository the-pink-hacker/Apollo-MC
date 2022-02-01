package com.ryangar46.apollo.client.renderer.entity;

import com.google.common.collect.ImmutableList;
import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.client.renderer.entity.model.ShuttleModel;
import com.ryangar46.apollo.entity.EntityManager;
import com.ryangar46.apollo.entity.ShuttleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ShuttleRenderer extends MobEntityRenderer<ShuttleEntity, ShuttleModel> {

    public ShuttleRenderer(EntityRendererFactory.Context context) {
        super(context, new ShuttleModel(context.getPart(EntityManager.SHUTTLE_LAYER)), 1.0f);
    }

    @Override
    public Identifier getTexture(ShuttleEntity entity) {
        return new Identifier(Apollo.MOD_ID, "textures/entity/shuttle/shuttle.png");
    }
}
