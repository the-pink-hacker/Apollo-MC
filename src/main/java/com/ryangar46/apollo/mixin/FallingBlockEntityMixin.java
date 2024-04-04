package com.ryangar46.apollo.mixin;

import com.ryangar46.apollo.entity.GravityManager;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin {
    // Changes falling speed
    @Inject(
            method = "tick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/FallingBlockEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V",
                    shift = At.Shift.AFTER,
                    ordinal = 0
            )
    )
    private void gravityFall(CallbackInfo ci) {
        Vec3d velocity = ((FallingBlockEntity)(Object)this).getVelocity();
        ((FallingBlockEntity)(Object)this).setVelocity(velocity.add(0.0d, 0.04d - (0.04d * GravityManager.getGravityMultiplier(((FallingBlockEntity)(Object)this).world)), 0.0d));
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
