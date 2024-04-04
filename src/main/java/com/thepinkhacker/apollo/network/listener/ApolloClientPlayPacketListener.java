package com.thepinkhacker.apollo.network.listener;

import com.thepinkhacker.apollo.network.packet.s2c.play.OpenShuttleScreenS2CPacket;

public interface ApolloClientPlayPacketListener {
    void apollo$onOpenShuttleScreen(OpenShuttleScreenS2CPacket packet);
}
