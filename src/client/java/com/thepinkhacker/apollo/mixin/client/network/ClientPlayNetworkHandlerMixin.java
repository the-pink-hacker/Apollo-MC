package com.thepinkhacker.apollo.mixin.client.network;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.client.gui.screen.ingame.ShuttleScreen;
import com.thepinkhacker.apollo.entity.vehicle.ShuttleEntity;
import com.thepinkhacker.apollo.network.ApolloNetworkThreadUtils;
import com.thepinkhacker.apollo.network.listener.ApolloClientPlayPacketListener;
import com.thepinkhacker.apollo.network.packet.s2c.play.OpenShuttleScreenS2CPacket;
import com.thepinkhacker.apollo.screen.ShuttleScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.SimpleInventory;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Debug(export = true)
@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin implements ApolloClientPlayPacketListener {
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private ClientWorld world;

    @Override
    public void apollo$onOpenShuttleScreen(OpenShuttleScreenS2CPacket packet) {
        ApolloNetworkThreadUtils.forceMainThread(packet, (ClientPlayNetworkHandler)(Object)this, this.client, listener -> {
            ((ApolloClientPlayPacketListener)listener).apollo$onOpenShuttleScreen(packet);
        });

        Apollo.LOGGER.info("Client End");
        Entity entity = this.world.getEntityById(packet.getShuttleId());
        if (entity instanceof ShuttleEntity shuttleEntity) {
            ClientPlayerEntity player = this.client.player;

            if (player == null) return;

            ShuttleScreenHandler screenHandler = new ShuttleScreenHandler(
                    packet.getSyncId(),
                    player.getInventory(),
                    new SimpleInventory(packet.getSlotCount()),
                    shuttleEntity
            );
            player.currentScreenHandler = screenHandler;
            this.client.setScreen(new ShuttleScreen(
                    screenHandler,
                    player.getInventory(),
                    shuttleEntity
            ));
        }
    }
}
