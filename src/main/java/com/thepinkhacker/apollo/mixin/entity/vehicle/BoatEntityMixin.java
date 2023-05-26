package com.thepinkhacker.apollo.mixin.entity.vehicle;

import com.thepinkhacker.apollo.world.dimension.GravityManager;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {
    // Changes falling speed
    @SuppressWarnings("unused")
    @ModifyVariable(
            method = "updateVelocity()V",
            at = @At("STORE"),
            ordinal = 0
    )
    private double gravityFall(double y) {
        return y * GravityManager.getGravityMultiplier(((BoatEntity)(Object)this).getWorld());
    }
}
