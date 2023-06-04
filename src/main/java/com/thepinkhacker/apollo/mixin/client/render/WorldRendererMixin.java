package com.thepinkhacker.apollo.mixin.client.render;

import com.thepinkhacker.apollo.client.render.ApolloSkyRenderer;
import com.thepinkhacker.apollo.client.world.DimensionEffectsManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Shadow @Final private MinecraftClient client;
    private final ApolloSkyRenderer apolloSkyRenderer = new ApolloSkyRenderer((WorldRenderer)(Object)this);

    @Inject(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/DimensionEffects;getSkyType()Lnet/minecraft/client/render/DimensionEffects$SkyType;"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void renderApolloSky(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo info) {
        if (this.client.world == null) return;

        if (this.client.world.getDimensionEffects() instanceof DimensionEffectsManager.ApolloDimensionEffects) {
            apolloSkyRenderer.render(matrices, projectionMatrix, tickDelta, camera);
            info.cancel();
        }
    }
}
