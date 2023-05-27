package com.thepinkhacker.apollo.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public class ApolloSkyRenderer {
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
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
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
