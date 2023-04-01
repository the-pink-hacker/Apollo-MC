package com.ryangar46.apollo.mixin.entity;

import com.ryangar46.apollo.registry.tag.ApolloEntityTypeTags;
import com.ryangar46.apollo.registry.tag.ApolloItemTags;
import com.ryangar46.apollo.world.ApolloGameRules;
import com.ryangar46.apollo.world.ApolloWorlds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
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
        if (!((Entity)(Object)this).world.isClient && ((Entity)(Object)this).world.getRegistryKey() == ApolloWorlds.MOON) {
            if (((Entity)(Object)this).world.getGameRules().getBoolean(ApolloGameRules.SUFFOCATE_IN_VACUUM) && !isVacuumImmune()) {
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
                            if (((Entity)(Object)this).world.getRegistryKey() == ApolloWorlds.MOON) {
                                GameMode gameMode = player.interactionManager.getGameMode();
                                if (gameMode == GameMode.SURVIVAL || gameMode == GameMode.ADVENTURE) {
                                    vacuumDamage(((Entity)(Object)this).world, entity);
                                }
                            }
                        } else {
                            vacuumDamage(((Entity)(Object)this).world, entity);
                        }
                    }
                }
            }
        }
    }

    private boolean isVacuumImmune() {
        return ((Entity)(Object)this).getType().isIn(ApolloEntityTypeTags.VACUUM_IMMUNE_CREATURES);
    }

    private boolean isAirtightArmor(ItemStack item) {
        return item.isIn(ApolloItemTags.AIRTIGHT_ARMOR);
    }

    private void vacuumDamage(World world, LivingEntity entity) {
        if (vacuumTicks % 20 == 0) {
            entity.damage(world.getDamageSources().inWall(), 1.0f);
        }
        vacuumTicks++;
    }
}
