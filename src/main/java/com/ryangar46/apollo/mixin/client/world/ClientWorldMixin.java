package com.ryangar46.apollo.mixin.client.world;

import com.ryangar46.apollo.tag.TagManager;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    private static final float NIGHT_ANGLE = 0.5f;

    @ModifyVariable(
            method = "method_23787(F)F",
            at = @At("STORE"),
            ordinal = 1
    )
    private float starBrightness(float f) {
        return ((ClientWorld)(Object)this).getDimensionEntry().isIn(TagManager.ATMOSPHERE_NOT_VISIBLE_WORLDS) ? NIGHT_ANGLE : f;
    }

    @ModifyVariable(
            method = "getSkyColor(Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;",
            at = @At("STORE"),
            ordinal = 1
    )
    private float darkSky(float f) {
        return ((ClientWorld)(Object)this).getDimensionEntry().isIn(TagManager.ATMOSPHERE_NOT_VISIBLE_WORLDS) ? NIGHT_ANGLE : f;
    }
}
