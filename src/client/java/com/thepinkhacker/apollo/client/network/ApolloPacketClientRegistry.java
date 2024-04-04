package com.thepinkhacker.apollo.client.network;

import com.thepinkhacker.apollo.client.render.ApolloSkyRenderer;
import com.thepinkhacker.apollo.network.ApolloPackets;
import com.thepinkhacker.apollo.network.listener.ApolloClientPlayPacketListener;
import com.thepinkhacker.apollo.resource.SpaceBodyManager;
import com.thepinkhacker.apollo.world.dimension.SpaceBody;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Debug;

import java.util.Map;

@Debug(export = true)
public abstract class ApolloPacketClientRegistry {
    public static void register() {
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

        ClientPlayNetworking.registerGlobalReceiver(
                ApolloPackets.OPEN_SHUTTLE_SCREEN,
                (packet, player, sender) -> ((ApolloClientPlayPacketListener)player.networkHandler).apollo$onOpenShuttleScreen(packet)
        );
    }
}
