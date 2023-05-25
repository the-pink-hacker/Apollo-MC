package com.thepinkhacker.apollo.mixin.client.world;

import com.thepinkhacker.apollo.resource.SpaceBodyManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// TODO: Separate Apollo skyboxes from overworld
@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    @ModifyVariable(
            method = "method_23787(F)F",
            at = @At("STORE"),
            ordinal = 1
    )
    private float starBrightnessSkyAngle(float skyAngle) {
        return SpaceBodyManager
                .getInstance()
                .getSpaceBodyOrDefault((World)(Object)this)
                .skyAngle(skyAngle);
    }

    @ModifyVariable(
            method = "getSkyColor(Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;",
            at = @At("STORE"),
            ordinal = 1
    )
    private float darkSkyAngle(float skyAngle) {
        return SpaceBodyManager
                .getInstance()
                .getSpaceBodyOrDefault((World)(Object)this)
                .skyAngle(skyAngle);
    }
}
