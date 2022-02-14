package com.ryangar46.apollo.mixin;

import com.ryangar46.apollo.entity.GravityManager;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMinecartEntity.class)
public abstract class AbstractMinecartEntityMixin {
    // Changes falling speed
    @Inject(
            method = "tick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/vehicle/AbstractMinecartEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void gravityFall(CallbackInfo ci) {
        double d = ((AbstractMinecartEntity)(Object)this).isTouchingWater() ? 0.005d : 0.04d;
        Vec3d velocity = ((AbstractMinecartEntity)(Object)this).getVelocity();
        ((AbstractMinecartEntity)(Object)this).setVelocity(velocity.add(0.0d, d - (d * GravityManager.getGravityMultiplier(((AbstractMinecartEntity)(Object)this).world)), 0.0d));
    }
}
