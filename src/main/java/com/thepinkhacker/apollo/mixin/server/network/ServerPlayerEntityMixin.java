package com.thepinkhacker.apollo.mixin.server.network;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.mixin.entity.LivingEntityMixin;
import com.thepinkhacker.apollo.world.pressure.VacuumChecker;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends LivingEntityMixin {
    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void vacuumTick(CallbackInfo info) {
        if (!VacuumChecker.checkForVacuum(((ServerPlayerEntity)(Object)this).world, (ServerPlayerEntity)(Object)this)) return;
        if (VacuumChecker.isArmorCollectionAirtight(((ServerPlayerEntity)(Object)this).getArmorItems())) return;

        apolloVacuumDamage(((ServerPlayerEntity)(Object)this).world);
    }

    @Override
    protected void apolloVacuumDamage(World world) {
        Apollo.LOGGER.info("Hi");
        if (((ServerPlayerEntity)(Object)this).interactionManager.getGameMode().isSurvivalLike()) {
            super.apolloVacuumDamage(((ServerPlayerEntity)(Object)this).world);
        }
    }
}
