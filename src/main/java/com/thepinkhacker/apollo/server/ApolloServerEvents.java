package com.thepinkhacker.apollo.server;

import com.thepinkhacker.apollo.network.packet.SyncSpaceBodiesPacket;
import com.thepinkhacker.apollo.resource.SpaceBodyManager;
import com.thepinkhacker.apollo.world.dimension.SpaceBody;
import com.thepinkhacker.apollo.world.spawner.GenericSpawnerManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ApolloServerEvents {
    public static void register() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> GenericSpawnerManager.register());
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
            if (!success) return;

            HashMap<Identifier, SpaceBody> spaceBodies = SpaceBodyManager.getInstance().getSpaceBodies();

            for (ServerPlayerEntity player : getNonHostPlayers(server)) {
                ServerPlayNetworking.send(player, new SyncSpaceBodiesPacket(spaceBodies));
            }
        });
        ServerPlayConnectionEvents.JOIN.register((networkHandler, packetSender, server) -> {
            packetSender.sendPacket(new SyncSpaceBodiesPacket(SpaceBodyManager.getInstance().getSpaceBodies()));
        });
    }

    private static List<ServerPlayerEntity> getNonHostPlayers(MinecraftServer server) {
        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();

        // If dedicated, all players are not the host
        if (server.isDedicated()) return players;

        ArrayList<ServerPlayerEntity> nonHosts = new ArrayList<>(players.size() - 1);

        for (ServerPlayerEntity player : players) {
            if (server.isHost(player.getGameProfile())) continue;
            nonHosts.add(player);
        }

        return nonHosts;
    }
}
