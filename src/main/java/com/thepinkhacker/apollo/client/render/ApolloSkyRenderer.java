package com.thepinkhacker.apollo.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.thepinkhacker.apollo.resource.SpaceBodyManager;
import com.thepinkhacker.apollo.world.dimension.SpaceBody;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;

public class ApolloSkyRenderer {
    private static final float SATELLITE_Y_OFFSET = 100.0f;
    private static final float DEFAULT_SATELLITE_ROTATION_DEGREES = -90.0f;

    public static void render(
            WorldRenderer renderer,
            MatrixStack matrices,
            Matrix4f projectionMatrix,
            float tickDelta,
            Camera camera
    ) {
        // Setup
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);

        resetSky(
                renderer,
                matrices,
                projectionMatrix,
                camera
        );

        // Render satellites
        RenderSystem.blendFuncSeparate(
                GlStateManager.SrcFactor.SRC_ALPHA,
                GlStateManager.DstFactor.ONE,
                GlStateManager.SrcFactor.ONE,
                GlStateManager.DstFactor.ZERO
        );
        matrices.push();
        setShaderColorWhite();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);

        SpaceBody spaceBody = SpaceBodyManager.getInstance().getSpaceBodyOrDefault(renderer.world);
        float skyAngleDegrees = renderer.world.getSkyAngle(tickDelta) * 360.0f;

        for (SpaceBody.Satellite satellite : spaceBody.getAllSatellites()) {
            renderSatellite(satellite, matrices, skyAngleDegrees);
        }

        // Reset
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        setShaderColorWhite();
        matrices.pop();
    }

    // TODO: Have the order of the satellites affect which one is in front of which
    private static void renderSatellite(
            SpaceBody.Satellite satellite,
            MatrixStack matrices,
            float skyAngleDegrees
    ) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.setShaderTexture(0, satellite.texture());
        Matrix4f matrix = matrices.peek().getPositionMatrix();

        boolean fixedOrbit = satellite.orbit().fixed();
        float speed = satellite.orbit().speed();
        // Offset is negative to make it more intuitive
        // Positive x offset goes in the positive x direction in the world
        float offsetX = (fixedOrbit ? 0.0f : skyAngleDegrees * speed) - satellite.orbit().offset().x;
        float offsetY = -satellite.orbit().offset().y + DEFAULT_SATELLITE_ROTATION_DEGREES;
        float offsetZ = -satellite.orbit().offset().z;

        // X: The direction the sun and more normally rotate in
        matrices.multiply(
                RotationAxis.POSITIVE_Y.rotationDegrees(offsetY)
                        .mul(RotationAxis.POSITIVE_Z.rotationDegrees(offsetZ))
                        .mul(RotationAxis.POSITIVE_X.rotationDegrees(offsetX))
        );

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);

        float scale = satellite.scale();
        int rows = satellite.phases().y;
        int columns = satellite.phases().x;

        bufferBuilder.vertex(matrix, -scale, SATELLITE_Y_OFFSET, -scale).texture(0.0f, 0.0f).next();
        bufferBuilder.vertex(matrix, scale, SATELLITE_Y_OFFSET, -scale).texture(1.0f / columns, 0.0f).next();
        bufferBuilder.vertex(matrix, scale, SATELLITE_Y_OFFSET, scale).texture(1.0f / columns, 1.0f / rows).next();
        bufferBuilder.vertex(matrix, -scale, SATELLITE_Y_OFFSET, scale).texture(0.0f, 1.0f / rows).next();

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());

        // Undo rotation
        matrices.multiply(
                RotationAxis.NEGATIVE_X.rotationDegrees(offsetX)
                        .mul(RotationAxis.NEGATIVE_Z.rotationDegrees(offsetZ))
                        .mul(RotationAxis.NEGATIVE_Y.rotationDegrees(offsetY))
        );
    }

    private static void setShaderColorWhite() {
        RenderSystem.setShaderColor(
                1.0f,
                1.0f,
                1.0f,
                1.0f
        );
    }

    /**
     * Clears all terrain fog such as the orange effect on the horizon during a sunset
     */
    private static void resetFog(Camera camera) {
        // TODO: Thin strip on horizon still has orange
        BackgroundRenderer.applyFog(
                camera,
                BackgroundRenderer.FogType.FOG_TERRAIN,
                0.0f,
                false,
                0
        );
        RenderSystem.setShaderFogColor(0.0f, 0.0f, 0.0f);
    }

    private static void resetSky(
            WorldRenderer renderer,
            MatrixStack matrices,
            Matrix4f projectionMatrix,
            Camera camera
    ) {
        resetFog(camera);
        setSkyColor(
                renderer.darkSkyBuffer,
                matrices,
                projectionMatrix,
                1.0f,
                1.0f,
                1.0f
        );
        setSkyColor(
                renderer.lightSkyBuffer,
                matrices,
                projectionMatrix,
                1.0f,
                1.0f,
                1.0f
        );
    }

    private static void setSkyColor(
            VertexBuffer skyBuffer,
            MatrixStack matrices,
            Matrix4f projectionMatrix,
            float red,
            float green,
            float blue
    ) {
        RenderSystem.setShaderColor(red, green, blue, 1.0f);
        skyBuffer.bind();
        skyBuffer.draw(
                matrices.peek().getPositionMatrix(),
                projectionMatrix,
                RenderSystem.getShader()
        );
        VertexBuffer.unbind();
    }
}
