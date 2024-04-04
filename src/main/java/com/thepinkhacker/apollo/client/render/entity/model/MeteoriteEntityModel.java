package com.thepinkhacker.apollo.client.render.entity.model;

import com.thepinkhacker.apollo.entity.projectile.MeteoriteEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;

public class MeteoriteEntityModel extends EntityModel<MeteoriteEntity> {
    private final ModelPart root;

    public MeteoriteEntityModel(ModelPart root) {
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(
                EntityModelPartNames.ROOT,
                ModelPartBuilder
                        .create()
                        .uv(0, 0)
                        .cuboid(-4.0f, 1.0f, -5.0f, 8.0f, 8.0f, 1.0f)
                        .cuboid(-5.0f, 1.0f, -4.0f, 1.0f, 8.0f, 8.0f)
                        .cuboid(-4.0f, 1.0f, 4.0f, 8.0f, 8.0f, 1.0f)
                        .cuboid(4.0f, 1.0f, -4.0f, 1.0f, 8.0f, 8.0f)
                        .cuboid(-4.0f, 9.0f, -4.0f, 8.0f, 1.0f, 8.0f)
                        .cuboid(-4.0f, 0.0f, -4.0f, 8.0f, 1.0f, 8.0f),
                ModelTransform.pivot(0F, 0F, 0F)
        );
        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(MeteoriteEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {}
}
