package com.ryangar46.apollo.mixin;

import com.ryangar46.apollo.tag.TagManager;
import com.ryangar46.apollo.world.dimension.DimensionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    public int vacuumTicks = 0;

    @Inject(
            method = "tick()V",
            at = @At("HEAD")
    )
    private void checkPressure(CallbackInfo info) {
        if (!((Entity)(Object)this).world.isClient && ((Entity)(Object)this).world.getRegistryKey() == DimensionManager.MOON) {
            if (!isVacuumImmune()) {
                if (((Entity)(Object)this) instanceof LivingEntity) {
                    LivingEntity entity = (LivingEntity)(Object)this;
                    Iterable<ItemStack> items = ((Entity)(Object)this).getArmorItems();
                    boolean airtight = true;
                    for (ItemStack item : items) {
                        if (!isAirtightArmor(item)) {
                            airtight = false;
                            break;
                        }
                    }

                    if (!airtight) {
                        if (((Entity)(Object)this) instanceof ServerPlayerEntity) {
                            ServerPlayerEntity player = (ServerPlayerEntity)entity;
                            if (((Entity)(Object)this).world.getRegistryKey() == DimensionManager.MOON) {
                                GameMode gameMode = player.interactionManager.getGameMode();
                                if (gameMode == GameMode.SURVIVAL || gameMode == GameMode.ADVENTURE) {
                                    vacuumDamage(entity);
                                }
                            }
                        } else {
                            vacuumDamage(entity);
                        }
                    }
                }
            }
        }
    }

    private boolean isVacuumImmune() {
        return TagManager.VACUUM_IMMUNE_CREATURES.contains(((Entity)(Object)this).getType());
    }

    private boolean isAirtightArmor(ItemStack item) {
        if (TagManager.AIRTIGHT_BOOTS.contains(item.getItem())) {
            return true;
        } else if (TagManager.AIRTIGHT_LEGGINGS.contains(item.getItem())) {
            return true;
        } else if (TagManager.AIRTIGHT_CHESTPLATES.contains(item.getItem())) {
            return true;
        } else if (TagManager.AIRTIGHT_HELMETS.contains(item.getItem())) {
            return true;
        } else {
            return false;
        }
    }

    private void vacuumDamage(LivingEntity entity) {
        if (vacuumTicks % 20 == 0) {
            entity.damage(DamageSource.IN_WALL, 1.0f);
        }
        vacuumTicks++;
    }
}
