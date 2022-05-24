package com.ryangar46.apollo.mixin.entity;

import com.ryangar46.apollo.tag.TagManager;
import com.ryangar46.apollo.world.GameRuleManager;
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

    @SuppressWarnings("unused")
    @Inject(
            method = "tick()V",
            at = @At("HEAD")
    )
    private void checkPressure(CallbackInfo info) {
        if (!((Entity)(Object)this).world.isClient && ((Entity)(Object)this).world.getRegistryKey() == DimensionManager.MOON) {
            if (((Entity)(Object)this).world.getGameRules().getBoolean(GameRuleManager.SUFFOCATE_IN_VACUUM) && !isVacuumImmune()) {
                if (((Entity)(Object)this) instanceof LivingEntity entity) {
                    Iterable<ItemStack> items = ((Entity)(Object)this).getArmorItems();
                    boolean airtight = true;
                    for (ItemStack item : items) {
                        if (!isAirtightArmor(item)) {
                            airtight = false;
                            break;
                        }
                    }

                    if (!airtight) {
                        if (((Entity)(Object)this) instanceof ServerPlayerEntity player) {
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
        return ((Entity)(Object)this).getType().isIn(TagManager.VACUUM_IMMUNE_CREATURES);
    }

    private boolean isAirtightArmor(ItemStack item) {
        if (item.isIn(TagManager.AIRTIGHT_BOOTS)) {
            return true;
        } else if (item.isIn(TagManager.AIRTIGHT_LEGGINGS)) {
            return true;
        } else if (item.isIn(TagManager.AIRTIGHT_CHESTPLATES)) {
            return true;
        } else return item.isIn(TagManager.AIRTIGHT_HELMETS);
    }

    private void vacuumDamage(LivingEntity entity) {
        if (vacuumTicks % 20 == 0) {
            entity.damage(DamageSource.IN_WALL, 1.0f);
        }
        vacuumTicks++;
    }
}
