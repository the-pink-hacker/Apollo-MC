package com.ryangar46.apollo.mixin.server.network;

import com.ryangar46.apollo.mixin.entity.LivingEntityMixin;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends LivingEntityMixin {
    @Override
    protected void apolloVacuumDamage(World world) {
        if (((ServerPlayerEntity)(Object)this).interactionManager.getGameMode().isSurvivalLike()) {
            super.apolloVacuumDamage(((ServerPlayerEntity)(Object)this).world);
        }
    }
}
