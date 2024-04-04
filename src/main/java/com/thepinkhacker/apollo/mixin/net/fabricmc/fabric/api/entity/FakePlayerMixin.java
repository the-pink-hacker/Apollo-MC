package com.thepinkhacker.apollo.mixin.net.fabricmc.fabric.api.entity;

import com.thepinkhacker.apollo.entity.vehicle.PlayerShuttleScreenOpener;
import com.thepinkhacker.apollo.entity.vehicle.ShuttleEntity;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FakePlayer.class)
public abstract class FakePlayerMixin implements PlayerShuttleScreenOpener {
    // Shouldn't do anything since this is a fake player
    @Override
    public void apollo$openShuttleInventory(ShuttleEntity shuttle, Inventory inventory) {}
}
