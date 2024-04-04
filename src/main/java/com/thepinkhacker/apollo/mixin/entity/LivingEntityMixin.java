package com.thepinkhacker.apollo.mixin.entity;

import com.thepinkhacker.apollo.world.dimension.GravityManager;
import com.thepinkhacker.apollo.world.pressure.VacuumChecker;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    public int apolloVacuumTicks = 0;

    // Inject into server only tick
    @Inject(
            method = "tick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;getStuckArrowCount()I"
            )
    )
    private void vacuumTick(CallbackInfo info) {
        if (!VacuumChecker.checkForVacuum(((LivingEntity)(Object)this).world, (LivingEntity)(Object)this)) return;
        if (VacuumChecker.isArmorCollectionAirtight(((LivingEntity)(Object)this).getArmorItems())) return;

        apolloVacuumDamage(((LivingEntity)(Object)this).world);
    }

    protected void apolloVacuumDamage(World world) {
        if (apolloVacuumTicks % 20 == 0) {
            ((LivingEntity)(Object)this).damage(world.getDamageSources().inWall(), 1.0f);
        }
        apolloVacuumTicks++;
    }

    @ModifyVariable(
            method = "travel(Lnet/minecraft/util/math/Vec3d;)V",
            at = @At("STORE"),
            ordinal = 0
    )
    private double gravityFall(double g) {
        return g * GravityManager.getGravityMultiplier(((LivingEntity)(Object)this).world, ((LivingEntity)(Object)this).getArmorItems());
    }

    @ModifyVariable(
            method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private float gravityDamage(float fallDistance) {
        return fallDistance * (float)GravityManager.getGravityMultiplier(((LivingEntity)(Object)this).world, ((LivingEntity)(Object)this).getArmorItems());
    }
}
