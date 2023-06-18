package com.thepinkhacker.apollo.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.thepinkhacker.apollo.resource.SpaceBodyManager;
import com.thepinkhacker.apollo.world.dimension.DayCycleManager;
import com.thepinkhacker.apollo.world.dimension.SpaceBody;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LunarWorldView;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class ApolloSkyRenderer {
    private static final float SATELLITE_Y_OFFSET = 100.0f;
    private static final float DEFAULT_SATELLITE_ROTATION_DEGREES = -90.0f;
    private static final int TICKS_PER_DAY = 20 * 60 * 20;
    private VertexBuffer starsBuffer = null;
    private final WorldRenderer renderer;
    private ClientWorld world;
    public static final List<ApolloSkyRenderer> INSTANCES = new ArrayList<>();

    public ApolloSkyRenderer(WorldRenderer renderer) {
        this.renderer = renderer;
        this.world = renderer.world;
        this.setupStars();
        INSTANCES.add(this);
    }

    /**
     * Is called when ever the current world changes or datapacks are reloaded
     */
    public void updateSky() {
        this.world = renderer.world;
        this.setupStars();
    }

    private void setupStars() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        if (this.starsBuffer != null) {
            this.starsBuffer.close();
        }

        this.starsBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder.BuiltBuffer builtBuffer = SpaceBodyManager
                .getInstance()
                .getSpaceBody(this.world)
                .map(spaceBody -> setupStars(
                        bufferBuilder,
                        spaceBody.getStarSettings()
                ))
                .orElse(renderer.renderStars(bufferBuilder));
        this.starsBuffer.bind();
        this.starsBuffer.upload(builtBuffer);
        VertexBuffer.unbind();
    }

    private BufferBuilder.BuiltBuffer setupStars(BufferBuilder buffer, SpaceBody.StarSettings settings) {
        Random random = Random.create(settings.seed());
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);

        for(int i = 0; i < settings.amount(); ++i) {
            double d = random.nextFloat() * 2.0f - 1.0f;
            double e = random.nextFloat() * 2.0f - 1.0f;
            double f = random.nextFloat() * 2.0f - 1.0f;
            double g = 0.15f + random.nextFloat() * 0.1f;
            double h = d * d + e * e + f * f;
            if (h < 1.0d && h > 0.01d) {
                h = 1.0d / Math.sqrt(h);
                d *= h;
                e *= h;
                f *= h;
                double j = d * 100.0;
                double k = e * 100.0;
                double l = f * 100.0;
                double m = Math.atan2(d, f);
                double n = Math.sin(m);
                double o = Math.cos(m);
                double p = Math.atan2(Math.sqrt(d * d + f * f), e);
                double q = Math.sin(p);
                double r = Math.cos(p);
                double s = random.nextDouble() * Math.PI * 2.0;
                double t = Math.sin(s);
                double u = Math.cos(s);

                for(int v = 0; v < 4; ++v) {
                    double w = 0.0;
                    double x = (double)((v & 2) - 1) * g;
                    double y = (double)((v + 1 & 2) - 1) * g;
                    double aa = x * u - y * t;
                    double ab = y * u + x * t;
                    double ad = aa * q + w * r;
                    double ae = w * q - aa * r;
                    double af = ae * n - ab * o;
                    double ah = ab * n + ae * o;
                    buffer.vertex(j + af, k + ad, l + ah).next();
                }
            }
        }

        return buffer.end();
    }

    public void render(
            MatrixStack matrices,
            Matrix4f projectionMatrix,
            float tickDelta,
            Camera camera
    ) {
        if (renderer.world != world) updateSky();
        if (world == null) return;

        // Setup
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);

        SpaceBody spaceBody = SpaceBodyManager.getInstance().getSpaceBodyOrDefault(this.world);

        resetSky(
                matrices,
                projectionMatrix,
                camera,
                this.world.getSkyColor(camera.getPos(), tickDelta)
        );

        RenderSystem.blendFuncSeparate(
                GlStateManager.SrcFactor.SRC_ALPHA,
                GlStateManager.DstFactor.ONE,
                GlStateManager.SrcFactor.ONE,
                GlStateManager.DstFactor.ZERO
        );

        // Stars
        // TODO: Implement day cycle for stars
        if (spaceBody.getStarSettings().display()) {
            setShaderColorWhite();
            setSkyColor(starsBuffer, matrices, projectionMatrix);
        }

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);

        // Render satellites
        matrices.push();

        DayCycleManager.WorldTime worldTime = DayCycleManager.getLightProviderTime(world.getLunarTime(), this.world);

        for (SpaceBody.Satellite satellite : spaceBody.getAllSatellites()) {
            renderSatellite(satellite, matrices, worldTime);
        }

        // Reset
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        setShaderColorWhite();
        matrices.pop();
    }

    // TODO: Have the order of the satellites affect which one is in front of which
    private void renderSatellite(
            SpaceBody.Satellite satellite,
            MatrixStack matrices,
            DayCycleManager.WorldTime worldTime
    ) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.setShaderTexture(0, satellite.getPrefixedTexture());
        Matrix4f matrix = matrices.peek().getPositionMatrix();

        SpaceBody.Orbit orbit = satellite.getOrbit();
        DayCycleManager.WorldTime satelliteTime = DayCycleManager.WorldTime.ofOrbitTicks(
                worldTime.asTicks(),
                orbit
        );

        // Offset is negative to make it more intuitive
        // Positive x offset goes in the positive x direction in the world
        // 0, 0, 0 is like the overworld's sun's noon position
        float offsetX = (orbit.fixed() ? 0.0f : (float)DayCycleManager.getSkyAngleDegrees(satelliteTime)) - orbit.offset().x;
        float offsetY = -orbit.offset().y + DEFAULT_SATELLITE_ROTATION_DEGREES;
        float offsetZ = -orbit.offset().z;

        // X: The direction the sun and more normally rotate in
        matrices.multiply(
                RotationAxis.POSITIVE_Y.rotationDegrees(offsetY)
                        .mul(RotationAxis.POSITIVE_Z.rotationDegrees(offsetZ))
                        .mul(RotationAxis.POSITIVE_X.rotationDegrees(offsetX))
        );

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);

        float scale = satellite.getScale();
        int phaseRows = satellite.getPhases().y;
        int phaseColumns = satellite.getPhases().x;
        int phaseIndex = getPhase(this.world, phaseRows * phaseColumns);
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
                Float.POSITIVE_INFINITY,
                false,
                0
        );
        RenderSystem.setShaderFogColor(0.0f, 0.0f, 0.0f);
    }

    private void resetSky(
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
        setSkyColor(skyBuffer, matrices, projectionMatrix);
    }

    private static void setSkyColor(
            VertexBuffer skyBuffer,
            MatrixStack matrices,
            Matrix4f projectionMatrix
    ) {
        skyBuffer.bind();
        skyBuffer.draw(
                matrices.peek().getPositionMatrix(),
                projectionMatrix,
                RenderSystem.getShader()
        );
        VertexBuffer.unbind();
    }
}
