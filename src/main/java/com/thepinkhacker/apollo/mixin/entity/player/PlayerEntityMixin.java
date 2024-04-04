package com.thepinkhacker.apollo.mixin.entity.player;

import com.thepinkhacker.apollo.entity.vehicle.PlayerShuttleScreenOpener;
import com.thepinkhacker.apollo.entity.vehicle.ShuttleEntity;
import com.thepinkhacker.apollo.mixin.entity.LivingEntityMixin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntityMixin implements PlayerShuttleScreenOpener {
    @Shadow
    public ScreenHandler currentScreenHandler;
    @Shadow
    @Final
    public PlayerScreenHandler playerScreenHandler;

    @Shadow
    public abstract PlayerInventory getInventory();

    // Similar to `PlayerEntity.openHorseInventory`
    // FakePlayer class inherits the empty method while ServerPlayerEntity has the real code
    @Override
    public void apollo$openShuttleInventory(ShuttleEntity shuttle, Inventory inventory) {}
}
