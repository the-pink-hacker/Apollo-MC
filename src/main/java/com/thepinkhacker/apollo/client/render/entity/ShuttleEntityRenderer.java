package com.thepinkhacker.apollo.client.render.entity;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.client.render.entity.model.ApolloEntityModelLayers;
import com.thepinkhacker.apollo.client.render.entity.model.ShuttleEntityModel;
import com.thepinkhacker.apollo.entity.vehicle.ShuttleEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class ShuttleEntityRenderer extends MobEntityRenderer<ShuttleEntity, ShuttleEntityModel> {
    private static final Identifier TEXTURE = Apollo.getIdentifier("textures/entity/shuttle.png");

    public ShuttleEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ShuttleEntityModel(context.getPart(ApolloEntityModelLayers.SHUTTLE)), 0.75f);
    }

    @Override
    public Identifier getTexture(ShuttleEntity entity) {
        return TEXTURE;
    }
}
