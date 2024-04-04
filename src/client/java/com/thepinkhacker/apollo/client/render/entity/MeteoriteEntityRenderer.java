package com.thepinkhacker.apollo.client.render.entity;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.client.render.entity.model.ApolloEntityModelLayers;
import com.thepinkhacker.apollo.client.render.entity.model.MeteoriteEntityModel;
import com.thepinkhacker.apollo.entity.projectile.MeteoriteEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MeteoriteEntityRenderer extends EntityRenderer<MeteoriteEntity> {
    private static final Identifier TEXTURE = Apollo.getIdentifier("textures/entity/shuttle.png");
    private final MeteoriteEntityModel model;

    public MeteoriteEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new MeteoriteEntityModel(ctx.getPart(ApolloEntityModelLayers.METEORITE));
    }

    @Override
    public void render(
            MeteoriteEntity entity,
            float yaw,
            float tickDelta,
            MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider,
            int light
    ) {
        matrixStack.push();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(
                this.model.getLayer(this.getTexture(entity))
        );
        this.model.render(
                matrixStack,
                vertexConsumer,
                light,
                OverlayTexture.DEFAULT_UV,
                1.0f,
                1.0f,
                1.0f,
                1.0f
        );
        matrixStack.pop();
        super.render(entity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
    }

    @Override
    public Identifier getTexture(MeteoriteEntity entity) {
        return TEXTURE;
    }
}
