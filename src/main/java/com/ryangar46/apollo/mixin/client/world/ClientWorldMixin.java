package com.ryangar46.apollo.mixin.client.world;

import com.ryangar46.apollo.tag.TagManager;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    @ModifyVariable(
            method = "Lnet/minecraft/client/world/ClientWorld;method_23787(F)F",
            at = @At("STORE"),
            ordinal = 1
    )
    private float starBrightness(float f) {
        return ((ClientWorld)(Object)this).method_40134().isIn(TagManager.ATMOSPHERE_NOT_VISIBLE_WORLDS) ? 0.5f : f;
    }
}
