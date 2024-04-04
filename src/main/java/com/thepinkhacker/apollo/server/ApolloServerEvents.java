package com.thepinkhacker.apollo.server;

import com.thepinkhacker.apollo.client.render.ApolloSkyRenderer;
import com.thepinkhacker.apollo.network.packet.SyncSpaceBodiesPacket;
import com.thepinkhacker.apollo.resource.SpaceBodyManager;
import com.thepinkhacker.apollo.world.spawner.GenericSpawnerManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public abstract class ApolloServerEvents {
    public static void register() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> GenericSpawnerManager.register());
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> ServerPlayNetworking.send(
                player,
                new SyncSpaceBodiesPacket(
                        SpaceBodyManager.getInstance().getSpaceBodies(),
                        !isHost(player)
                )
        ));
    }

    private static boolean isHost(ServerPlayerEntity player) {
        MinecraftServer server = player.server;
        if (server.isDedicated()) return false;

        return server.isHost(player.getGameProfile());
    }
}
