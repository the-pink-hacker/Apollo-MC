package com.thepinkhacker.apollo.mixin.server.world;

import com.thepinkhacker.apollo.world.spawner.GenericSpawner;
import com.thepinkhacker.apollo.world.spawner.GenericSpawnerManager;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerChunkManager.class)
public abstract class ServerChunkManagerMixin {
    @Shadow @Final private ServerWorld world;
    @Shadow private boolean spawnMonsters;
    @Shadow private boolean spawnAnimals;

    @Inject(
            method = "tickChunks()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V",
                    shift = At.Shift.AFTER,
                    ordinal = 2
            )
    )
    private void tickGenericSpawners(CallbackInfo callbackInfo) {
        for (GenericSpawner spawner : GenericSpawnerManager.spawners) {
            spawner.spawn(world, spawnMonsters, spawnAnimals);
        }
    }
}
