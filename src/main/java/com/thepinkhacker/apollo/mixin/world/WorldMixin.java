package com.thepinkhacker.apollo.mixin.world;

import com.thepinkhacker.apollo.world.dimension.DayCycleManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Debug(export = true)
@Mixin(World.class)
public abstract class WorldMixin {
    @Redirect(
            method = "calculateAmbientDarkness",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getSkyAngle(F)F"
            )
    )
    private float calculateAmbientDarknessSkyAngle(World world, float tickDelta) {
        return (float)DayCycleManager.getSkyAngleDegreesLightProvider(world);
    }
}
