package com.ryangar46.apollo.mixin;

import com.ryangar46.apollo.tag.TagManager;
import com.ryangar46.apollo.world.dimension.DimensionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public World world;

    @Shadow
    public void kill() {}

    @Shadow
    public boolean isPlayer() {
        return false;
    }

    @Shadow
    public EntityType<?> getType() {
        return null;
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void checkPressure(CallbackInfo info) {
        if (world.getRegistryKey() == DimensionManager.MOON) {
            if (!isVacuumImmune()) {
                if (!isPlayer()) {
                    kill();
                }
            }
        }
    }

    private boolean isVacuumImmune() {
        return TagManager.VACUUM_IMMUNE_CREATURES.contains(this.getType());
    }
}
