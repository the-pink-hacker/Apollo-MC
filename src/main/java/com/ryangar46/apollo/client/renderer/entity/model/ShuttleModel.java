package com.ryangar46.apollo.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.ryangar46.apollo.entity.vehicle.ShuttleEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;

public class ShuttleModel extends EntityModel<ShuttleEntity> {
    private final ModelPart base;

    public ShuttleModel(ModelPart modelPart) {
        this.base = modelPart.getChild(EntityModelPartNames.CUBE);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.CUBE, ModelPartBuilder.create()
                .uv(0, 0)
                .cuboid(-6.0f, 12.0f, -6.0f, 12.0f, 12.0f, 12.0f),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(ShuttleEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.base).forEach((modelRenderer) -> {
            modelRenderer.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        });
    }
}
