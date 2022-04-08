package com.ryangar46.apollo.mixin.entity.projectile;

import com.ryangar46.apollo.world.dimension.GravityManager;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin {
    // Changes falling speed
    @SuppressWarnings("unused")
    @ModifyArg(
            method = "tick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;setVelocity(DDD)V"
            ),
            index = 1
    )
    private double gravityFall(double y) {
        return y + 0.05d - (0.05d * GravityManager.getGravityMultiplier(((PersistentProjectileEntity)(Object)this).world));
    }
}
