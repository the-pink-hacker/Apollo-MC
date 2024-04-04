package com.thepinkhacker.apollo.network.packet.s2c.play;

import com.thepinkhacker.apollo.network.ApolloPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public class OpenShuttleScreenS2CPacket implements FabricPacket {
    private final int syncId;
    private final int slotCount;
    private final int shuttleId;

    public OpenShuttleScreenS2CPacket(int syncId, int slotCount, int shuttleId) {
        this.syncId = syncId;
        this.slotCount = slotCount;
        this.shuttleId = shuttleId;
    }

    public OpenShuttleScreenS2CPacket(PacketByteBuf buf) {
        this.syncId = buf.readUnsignedByte();
        this.slotCount = buf.readVarInt();
        this.shuttleId = buf.readInt();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeByte(this.syncId);
        buf.writeVarInt(this.slotCount);
        buf.writeInt(this.shuttleId);
    }

    @Override
    public PacketType<OpenShuttleScreenS2CPacket> getType() {
        return ApolloPackets.OPEN_SHUTTLE_SCREEN;
    }

    public int getSyncId() {
        return syncId;
    }

    public int getSlotCount() {
        return slotCount;
    }

    public int getShuttleId() {
        return shuttleId;
    }
}
