package com.ryangar46.apollo.mixin;

import com.mojang.authlib.GameProfile;
import com.ryangar46.apollo.tag.TagManager;
import com.ryangar46.apollo.world.dimension.DimensionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    @Shadow
    public ServerPlayerInteractionManager interactionManager;

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void checkPressure(CallbackInfo info) {
        if (this.world.getRegistryKey() == DimensionManager.MOON) {
            GameMode gameMode = interactionManager.getGameMode();
            if (gameMode == GameMode.SURVIVAL || gameMode == GameMode.ADVENTURE) {
                if (!isWearingAirtightArmor()) {
                    kill();
                }
            }
        }
    }

    private boolean isWearingAirtightArmor() {
        PlayerInventory inventory = getInventory();

        // Boots
        if (TagManager.AIRTIGHT_BOOTS.contains(inventory.armor.get(0).getItem())) {
            // Leggings
            if (TagManager.AIRTIGHT_LEGGINGS.contains(inventory.armor.get(1).getItem())) {
                // Chestplates
                if (TagManager.AIRTIGHT_CHESTPLATES.contains(inventory.armor.get(2).getItem())) {
                    // Helmets
                    if (TagManager.AIRTIGHT_HELMETS.contains(inventory.armor.get(3).getItem())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
