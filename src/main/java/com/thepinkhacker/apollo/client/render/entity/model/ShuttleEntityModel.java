package com.thepinkhacker.apollo.client.render.entity.model;

import com.thepinkhacker.apollo.entity.vehicle.ShuttleEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class ShuttleEntityModel extends ApolloEntityModel<ShuttleEntity> {
    private final ModelPart thruster_center;
    private final ModelPart thruster_north;
    private final ModelPart thruster_east;
    private final ModelPart thruster_south;
    private final ModelPart thruster_west;
    private final ModelPart body;

    public ShuttleEntityModel(ModelPart root) {
        this.thruster_center = root.getChild("thruster_center");
        this.thruster_north = root.getChild("thruster_north");
        this.thruster_east = root.getChild("thruster_east");
        this.thruster_south = root.getChild("thruster_south");
        this.thruster_west = root.getChild("thruster_west");
        this.body = root.getChild("body");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData thruster_center = modelPartData.addChild("thruster_center", ModelPartBuilder.create().uv(10, 6).cuboid(-2.0F, -4.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(58, 0).cuboid(-1.0F, -5.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(90, 0).cuboid(-2.0F, -8.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData thruster_north = modelPartData.addChild("thruster_north", ModelPartBuilder.create().uv(2, 14).cuboid(-2.0F, -4.0F, -7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(50, 0).cuboid(-1.0F, -5.0F, -6.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(82, 0).cuboid(-2.0F, -8.0F, -7.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData thruster_east = modelPartData.addChild("thruster_east", ModelPartBuilder.create().uv(14, 22).mirrored().cuboid(-7.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
                .uv(42, 0).cuboid(-7.0F, -8.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(82, 0).cuboid(-6.0F, -5.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData thruster_south = modelPartData.addChild("thruster_south", ModelPartBuilder.create().uv(6, 30).mirrored().cuboid(-2.0F, -4.0F, 3.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
                .uv(34, 0).cuboid(-1.0F, -5.0F, 4.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(66, 0).cuboid(-2.0F, -8.0F, 3.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData thruster_west = modelPartData.addChild("thruster_west", ModelPartBuilder.create().uv(106, 38).cuboid(3.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(26, 0).cuboid(4.0F, -5.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(58, 0).cuboid(3.0F, -8.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(98, 0).cuboid(-7.0F, -9.0F, -7.0F, 14.0F, 1.0F, 14.0F, new Dilation(0.0F))
                .uv(10, 58).cuboid(-8.0F, -41.0F, -8.0F, 16.0F, 32.0F, 16.0F, new Dilation(0.0F))
                .uv(58, 56).cuboid(-7.0F, -43.0F, -7.0F, 14.0F, 2.0F, 14.0F, new Dilation(0.0F))
                .uv(106, 62).cuboid(-6.0F, -45.0F, -6.0F, 12.0F, 2.0F, 12.0F, new Dilation(0.0F))
                .uv(26, 60).cuboid(-5.0F, -46.0F, -5.0F, 10.0F, 1.0F, 10.0F, new Dilation(0.0F))
                .uv(42, 66).cuboid(-4.0F, -47.0F, -4.0F, 8.0F, 1.0F, 8.0F, new Dilation(0.0F))
                .uv(74, 56).cuboid(-3.0F, -48.0F, -3.0F, 6.0F, 1.0F, 6.0F, new Dilation(0.0F))
                .uv(12, 60).mirrored().cuboid(-1.0F, -54.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        thruster_center.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        thruster_north.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        thruster_east.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        thruster_south.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        thruster_west.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
