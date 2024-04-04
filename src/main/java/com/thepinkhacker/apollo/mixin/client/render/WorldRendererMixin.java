package com.thepinkhacker.apollo.mixin.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.thepinkhacker.apollo.resource.SpaceBodyManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Debug(export = true)
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
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
    private void setMoon(int i, Identifier texture) {
        RenderSystem.setShaderTexture(
                i,
                SpaceBodyManager
                        .getInstance()
                        .getSpaceBodyOrDefault(world)
                        .getSecondaryOrbitingBody(texture)
        );
    }

    @ModifyArg(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/DimensionEffects;getFogColorOverride(FF)[F"
            ),
            index = 0
    )
    private float getFogColorSkyAngle(float skyAngle) {
        return SpaceBodyManager.getInstance().getSpaceBodyOrDefault(world).skyAngle(skyAngle);
    }
}
