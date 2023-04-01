package com.thepinkhacker.apollo.mixin.entity;

import com.thepinkhacker.apollo.world.dimension.GravityManager;
import net.minecraft.entity.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin {
    // Changes falling speed
    @SuppressWarnings("unused")
    @ModifyArg(
            method = "tick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/Vec3d;add(DDD)Lnet/minecraft/util/math/Vec3d;"
            ),
            index = 1
    )
    private double gravityFall(double y) {
        return y * GravityManager.getGravityMultiplier(((FallingBlockEntity)(Object)this).world);
    }

    // Changes damage calculation
    @ModifyVariable(
            method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z",
            at = @At("HEAD"),
            ordinal = 0
    )
    private float gravityDamage(float fallDistance) {
        return fallDistance * (float)GravityManager.getGravityMultiplier(((FallingBlockEntity)(Object)this).world);
    }
}
