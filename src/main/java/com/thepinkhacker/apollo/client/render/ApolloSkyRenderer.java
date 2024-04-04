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

    public static void render(
            WorldRenderer renderer,
            MatrixStack matrices,
            Matrix4f projectionMatrix,
            float tickDelta,
            Camera camera
    ) {
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
        for (SpaceBody.Satellite satellite : spaceBody.getSatellites()) {
            renderSatellite(satellite, matrices);
        }

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        setShaderColorWhite();
        matrices.pop();
    }

    private static void renderSatellite(
            SpaceBody.Satellite satellite,
            MatrixStack matrices
    ) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.setShaderTexture(0, satellite.texture());
        // TODO: Add custom sky locations
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0f));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0f));
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        float scale = satellite.scale();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrix, -scale, SATELLITE_Y_OFFSET, -scale).texture(0.0f, 0.0f).next();
        bufferBuilder.vertex(matrix, scale, SATELLITE_Y_OFFSET, -scale).texture(1.0f, 0.0f).next();
        bufferBuilder.vertex(matrix, scale, SATELLITE_Y_OFFSET, scale).texture(1.0f, 1.0f).next();
        bufferBuilder.vertex(matrix, -scale, SATELLITE_Y_OFFSET, scale).texture(0.0f, 1.0f).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
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
