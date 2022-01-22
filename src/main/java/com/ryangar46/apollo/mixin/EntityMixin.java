package com.ryangar46.apollo.mixin;

import com.google.common.collect.Iterables;
import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.tag.TagManager;
import com.ryangar46.apollo.world.dimension.DimensionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public World world;

    public int VacuumeTicks;

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

    @Shadow
    public Iterable<ItemStack> getArmorItems() {
        return null;
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void checkPressure(CallbackInfo info) {
        if (world.getRegistryKey() == DimensionManager.MOON) {
            if (!isVacuumImmune()) {
                if ((Entity)(Object)this instanceof LivingEntity) {
                    Iterable<ItemStack> items = getArmorItems();
                    boolean airtight = true;
                    for (ItemStack item : items) {
                        if (!isAirtightArmor(item)) {
                            airtight = false;
                            break;
                        }
                    }

                    if (!airtight) {
                        if ((Entity)(Object)this instanceof ServerPlayerEntity) {
                            ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
                            if (this.world.getRegistryKey() == DimensionManager.MOON) {
                                GameMode gameMode = player.interactionManager.getGameMode();
                                if (gameMode == GameMode.SURVIVAL || gameMode == GameMode.ADVENTURE) {
                                    kill();
                                }
                            }
                        } else {
                            kill();
                        }
                    }
                }
            }
        }
    }

    private boolean isVacuumImmune() {
        return TagManager.VACUUM_IMMUNE_CREATURES.contains(this.getType());
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
}
