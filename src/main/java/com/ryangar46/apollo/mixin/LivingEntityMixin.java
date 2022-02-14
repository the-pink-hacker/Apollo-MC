package com.ryangar46.apollo.mixin;

import com.ryangar46.apollo.entity.GravityManager;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    // Changes falling speed
    @ModifyVariable(
            method = "travel(Lnet/minecraft/util/math/Vec3d;)V",
            at = @At("STORE"),
            ordinal = 0
    )
    private double gravityFall(double g) {
        return g * GravityManager.getGravityMultiplier(((LivingEntity)(Object)this).world);
    }

    // Changes damage calculation
    @ModifyVariable(
            method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z",
            at = @At("HEAD"),
            ordinal = 0
    )
    private float gravityDamage(float fallDistance) {
        return fallDistance * (float)GravityManager.getGravityMultiplier(((LivingEntity)(Object)this).world);
    }
}
