package com.ryangar46.apollo.mixin;

import com.ryangar46.apollo.world.dimension.GravityManager;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Particle.class)
public abstract class ParticleMixin {
    @Shadow
    public ClientWorld world;

    @Shadow
    public double velocityY;

    @Shadow
    public float gravityStrength;

    // Changes falling speed
    @Inject(
            method = "tick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/Particle;move(DDD)V"
            )
    )
    private void gravityFall(CallbackInfo ci) {
        this.velocityY += 0.04D * (double)this.gravityStrength;
        this.velocityY -= 0.04D * (double)this.gravityStrength * GravityManager.getGravityMultiplier(world);
    }
}
