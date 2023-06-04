package com.thepinkhacker.apollo.network.packet;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.thepinkhacker.apollo.network.ApolloPackets;
import com.thepinkhacker.apollo.resource.NbtHelper;
import com.thepinkhacker.apollo.world.dimension.SpaceBody;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class SyncSpaceBodiesPacket implements FabricPacket {
    private final HashMap<Identifier, SpaceBody> spaceBodies;
    private final boolean reload;

    public SyncSpaceBodiesPacket(HashMap<Identifier, SpaceBody> spaceBodies, boolean reload) {
        this.spaceBodies = spaceBodies;
        this.reload = reload;
    }

    @Override
    public void write(PacketByteBuf buffer) {
        NbtCompound nbt = new NbtCompound();

        GsonBuilder gsonBuilder = new GsonBuilder();
        SpaceBody.registerGsonType(gsonBuilder);
        String data = gsonBuilder.create().toJson(spaceBodies);
        nbt.putString("data", data);

        nbt.putBoolean("reload", reload);

        buffer.writeNbt(nbt);
    }

    public static SyncSpaceBodiesPacket read(PacketByteBuf buffer) {
        NbtHelper helper = new NbtHelper(buffer.readNbt());
        HashMap<Identifier, SpaceBody> spaceBodies = helper.getOptionalString("data").map(data -> {
            GsonBuilder gsonBuilder = new GsonBuilder();
            SpaceBody.registerGsonType(gsonBuilder);
            return gsonBuilder.create().fromJson(data, new TypeToken<HashMap<Identifier, SpaceBody>>(){});
        }).orElse(new HashMap<>());

        Boolean reload = helper.getOptionalBoolean("reload").orElse(false);

        return new SyncSpaceBodiesPacket(spaceBodies, reload);
    }

    public HashMap<Identifier, SpaceBody> getSpaceBodies() {
        return spaceBodies;
    }

    public boolean shouldReload() {
        return reload;
    }

    @Override
    public PacketType<SyncSpaceBodiesPacket> getType() {
        return ApolloPackets.SYNC_SPACE_BODIES;
    }
}
