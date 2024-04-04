package com.ryangar46.apollo.mixin;

import com.ryangar46.apollo.entity.GravityManager;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BoatEntity.class)
public class BoatEntityMixin {
    // Changes falling speed
    @ModifyVariable(
            method = "updateVelocity()V",
            at = @At("STORE"),
            ordinal = 0
    )
    private double gravityFall(double d) {
        return d * GravityManager.getGravityMultiplier(((BoatEntity)(Object)this).world);
    }
}
