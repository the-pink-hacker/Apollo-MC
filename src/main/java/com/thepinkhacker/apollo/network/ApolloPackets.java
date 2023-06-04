package com.thepinkhacker.apollo.network;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.client.render.ApolloSkyRenderer;
import com.thepinkhacker.apollo.network.packet.SyncSpaceBodiesPacket;
import com.thepinkhacker.apollo.resource.SpaceBodyManager;
import com.thepinkhacker.apollo.world.dimension.SpaceBody;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.util.Identifier;

import java.util.Map;

public abstract class ApolloPackets {
    public static final PacketType<SyncSpaceBodiesPacket> SYNC_SPACE_BODIES = PacketType.create(
            Apollo.getIdentifier("sync_space_bodies"),
            SyncSpaceBodiesPacket::read
    );

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(ApolloPackets.SYNC_SPACE_BODIES, (packet, player, sender) -> {
            if (packet.shouldReload()) {
                SpaceBodyManager spaceBodyManager = SpaceBodyManager.getInstance();
                spaceBodyManager.clearSpaceBodies();

                for (Map.Entry<Identifier, SpaceBody> entry : packet.getSpaceBodies().entrySet()) {
                    spaceBodyManager.addSpaceBody(entry.getKey(), entry.getValue());
                }
            }

            for (ApolloSkyRenderer renderer : ApolloSkyRenderer.INSTANCES) {
                renderer.updateSky();
            }
        });
    }
}
