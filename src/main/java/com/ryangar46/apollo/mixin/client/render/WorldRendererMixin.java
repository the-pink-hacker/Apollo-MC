package com.ryangar46.apollo.mixin.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ryangar46.apollo.client.render.SkyManager;
import com.ryangar46.apollo.tag.TagManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    private static final float NIGHT_ANGLE = 0.5f;

    @Shadow
    private ClientWorld world;

    @Redirect(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V",
                    ordinal = 1
            )
    )
    private void setMoon(int i, Identifier identifier) {
        if (world.getDimensionEntry().isIn(TagManager.EARTH_VISIBLE_WORLDS)) {
            RenderSystem.setShaderTexture(i, SkyManager.EARTH);
        } else {
            RenderSystem.setShaderTexture(i, identifier);
        }
    }

    @ModifyArg(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/DimensionEffects;getFogColorOverride(FF)[F"
            ),
            index = 0
    )
    private float getFogColor(float skyAngle) {
        return world.getDimensionEntry().isIn(TagManager.ATMOSPHERE_NOT_VISIBLE_WORLDS) ? NIGHT_ANGLE : skyAngle;
    }
}
