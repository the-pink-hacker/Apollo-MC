package com.ryangar46.apollo.mixin;

import com.ryangar46.apollo.world.dimension.GravityManager;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    // Changes falling speed
    @ModifyArg(
            method = "tick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/Vec3d;add(DDD)Lnet/minecraft/util/math/Vec3d;"
            ),
            index = 1
    )
    private double gravityFall(double y) {
        return y * GravityManager.getGravityMultiplier(((ItemEntity)(Object)this).world);
    }
}
