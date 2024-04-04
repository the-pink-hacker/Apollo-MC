package com.ryangar46.apollo.mixin;

import com.ryangar46.apollo.entity.GravityManager;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {
    // Changes falling speed
    @Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ExperienceOrbEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V", shift = At.Shift.AFTER, ordinal = 0))
    private void gravityFall(CallbackInfo ci) {
        Vec3d velocity = ((ExperienceOrbEntity)(Object)this).getVelocity();
        ((ExperienceOrbEntity)(Object)this).setVelocity(velocity.add(0.0d, 0.03d - (0.03d * GravityManager.getGravityMultiplier(((ExperienceOrbEntity)(Object)this).world)), 0.0d));
    }
}
