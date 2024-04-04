package com.thepinkhacker.apollo.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.thepinkhacker.apollo.resource.SpaceBodyManager;
import com.thepinkhacker.apollo.world.dimension.SpaceBody;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LunarWorldView;
import org.joml.Matrix4f;

public class ApolloSkyRenderer {
    private static final float SATELLITE_Y_OFFSET = 100.0f;
    private static final float DEFAULT_SATELLITE_ROTATION_DEGREES = -90.0f;
    private static final int TICKS_PER_DAY = 20 * 60 * 20;

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

        SpaceBody spaceBody = SpaceBodyManager.getInstance().getSpaceBodyOrDefault(renderer.world);

        resetSky(
                renderer,
                matrices,
                projectionMatrix,
                camera,
                renderer.world.getSkyColor(camera.getPos(), tickDelta)
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

        for (SpaceBody.Satellite satellite : spaceBody.getAllSatellites()) {
            renderSatellite(satellite, renderer, matrices);
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
            WorldRenderer renderer,
            MatrixStack matrices
    ) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.setShaderTexture(0, satellite.texture());
        Matrix4f matrix = matrices.peek().getPositionMatrix();

        boolean fixedOrbit = satellite.orbit().fixed();
        float speed = satellite.orbit().speed();
        // Offset is negative to make it more intuitive
        // Positive x offset goes in the positive x direction in the world
        float offsetX = (fixedOrbit ? 0.0f : getSkyAngleDegrees(renderer.world, speed)) - satellite.orbit().offset().x;
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
        int phaseRows = satellite.phases().y;
        int phaseColumns = satellite.phases().x;
        int phaseIndex = getPhase(renderer.world, phaseRows * phaseColumns);
        int row = phaseIndex / phaseColumns;
        int column = phaseIndex % phaseColumns;
        float rowLow = (float)row / phaseRows;
        float rowHigh = (1.0f + row) / phaseRows;
        float columnLow = (float)column / phaseColumns;
        float columnHigh = (1.0f + column) / phaseColumns;

        bufferBuilder.vertex(matrix, -scale, SATELLITE_Y_OFFSET, -scale).texture(columnLow , rowLow).next();
        bufferBuilder.vertex(matrix, scale, SATELLITE_Y_OFFSET, -scale).texture(columnHigh, rowLow).next();
        bufferBuilder.vertex(matrix, scale, SATELLITE_Y_OFFSET, scale).texture(columnHigh, rowHigh).next();
        bufferBuilder.vertex(matrix, -scale, SATELLITE_Y_OFFSET, scale).texture(columnLow, rowHigh).next();

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());

        // Undo rotation
        matrices.multiply(
                RotationAxis.NEGATIVE_X.rotationDegrees(offsetX)
                        .mul(RotationAxis.NEGATIVE_Z.rotationDegrees(offsetZ))
                        .mul(RotationAxis.NEGATIVE_Y.rotationDegrees(offsetY))
        );
    }

    private static int getPhase(LunarWorldView world, int numberOfPhases) {
        return (int)(world.getLunarTime() / TICKS_PER_DAY % numberOfPhases + numberOfPhases) % numberOfPhases;
    }

    public static float getSkyAngleDegrees(LunarWorldView world, float speed) {
        double d = MathHelper.fractionalPart((double)world.getLunarTime() * speed / TICKS_PER_DAY - 0.25d);
        double e = 0.5d - Math.cos(d * Math.PI) / 2.0d;
        return (float)(d * 2.0d + e) / 3.0f * 360.0f;
    }

    private static void setShaderColorOpaque(float red, float green, float blue) {
        RenderSystem.setShaderColor(red, green, blue, 1.0f);
    }

    private static void setShaderColorOpaque(Vec3d color) {
        setShaderColorOpaque((float)color.x, (float)color.y, (float)color.z);
    }

    private static void setShaderColorWhite() {
        setShaderColorOpaque(1.0f, 1.0f, 1.0f);
    }

    private static void setShaderColorBlack() {
        setShaderColorOpaque(0.0f, 0.0f, 0.0f);
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
            Camera camera,
            Vec3d color
    ) {
        resetFog(camera);
        setSkyColor(
                renderer.darkSkyBuffer,
                matrices,
                projectionMatrix,
                color
        );
        setSkyColor(
                renderer.lightSkyBuffer,
                matrices,
                projectionMatrix,
                color
        );
    }

    private static void setSkyColor(
            VertexBuffer skyBuffer,
            MatrixStack matrices,
            Matrix4f projectionMatrix,
            Vec3d color
    ) {
        setShaderColorOpaque(color);
        skyBuffer.bind();
        skyBuffer.draw(
                matrices.peek().getPositionMatrix(),
                projectionMatrix,
                RenderSystem.getShader()
        );
        VertexBuffer.unbind();
    }
}
