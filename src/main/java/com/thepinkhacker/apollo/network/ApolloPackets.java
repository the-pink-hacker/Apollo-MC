package com.thepinkhacker.apollo.network;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.network.packet.SyncSpaceBodiesPacket;
import com.thepinkhacker.apollo.network.packet.s2c.play.OpenShuttleScreenS2CPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;

public abstract class ApolloPackets {
    public static final PacketType<SyncSpaceBodiesPacket> SYNC_SPACE_BODIES = PacketType.create(
            Apollo.getIdentifier("sync_space_bodies"),
            SyncSpaceBodiesPacket::read
    );
    public static final PacketType<OpenShuttleScreenS2CPacket> OPEN_SHUTTLE_SCREEN = PacketType.create(
            Apollo.getIdentifier("open_shuttle_screen"),
            OpenShuttleScreenS2CPacket::new
    );
}
