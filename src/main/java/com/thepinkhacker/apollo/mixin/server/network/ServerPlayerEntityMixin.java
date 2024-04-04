package com.thepinkhacker.apollo.mixin.server.network;

import com.thepinkhacker.apollo.entity.vehicle.PlayerShuttleScreenOpener;
import com.thepinkhacker.apollo.entity.vehicle.ShuttleEntity;
import com.thepinkhacker.apollo.mixin.entity.player.PlayerEntityMixin;
import com.thepinkhacker.apollo.network.packet.s2c.play.OpenShuttleScreenS2CPacket;
import com.thepinkhacker.apollo.screen.ShuttleScreenHandler;
import com.thepinkhacker.apollo.world.pressure.VacuumChecker;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.packet.s2c.play.OpenHorseScreenS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntityMixin implements PlayerShuttleScreenOpener {
    @Shadow
    private int screenHandlerSyncId;

    @Shadow
    public abstract void incrementScreenHandlerSyncId();

    @Shadow
    public abstract void closeHandledScreen();

    @Shadow
    public abstract void onScreenHandlerOpened(ScreenHandler screenHandler);

    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void vacuumTick(CallbackInfo info) {
        if (!VacuumChecker.checkForVacuum(((ServerPlayerEntity)(Object)this).getWorld(), (ServerPlayerEntity)(Object)this)) return;
        if (VacuumChecker.isArmorCollectionAirtight(((ServerPlayerEntity)(Object)this).getArmorItems())) return;

        apolloVacuumDamage(((ServerPlayerEntity)(Object)this).getWorld());
    }

    @Override
    protected void apolloVacuumDamage(World world) {
        if (((ServerPlayerEntity)(Object)this).interactionManager.getGameMode().isSurvivalLike()) {
            super.apolloVacuumDamage(((ServerPlayerEntity)(Object)this).getWorld());
        }
    }

    @Override
    public void apollo$openShuttleInventory(ShuttleEntity shuttle, Inventory inventory) {
        if (this.currentScreenHandler != this.playerScreenHandler) {
            this.closeHandledScreen();
        }

        this.incrementScreenHandlerSyncId();
        ServerPlayNetworking.send((ServerPlayerEntity)(Object)this, new OpenShuttleScreenS2CPacket(
                this.screenHandlerSyncId,
                inventory.size(),
                shuttle.getId()
        ));
        this.currentScreenHandler = new ShuttleScreenHandler(this.screenHandlerSyncId, this.getInventory(), inventory, shuttle);
        this.onScreenHandlerOpened(this.currentScreenHandler);
    }
}
