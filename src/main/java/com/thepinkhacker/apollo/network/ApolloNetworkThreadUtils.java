package com.thepinkhacker.apollo.network;

import com.mojang.logging.LogUtils;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.minecraft.network.OffThreadException;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.thread.ThreadExecutor;
import org.slf4j.Logger;

public class ApolloNetworkThreadUtils {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static <T extends PacketListener> void forceMainThread(FabricPacket packet, T listener, ServerWorld world, FabricPacketApply<T> apply) throws OffThreadException {
        forceMainThread(packet, listener, world.getServer(), apply);
    }

    public static <T extends PacketListener> void forceMainThread(FabricPacket packet, T listener, ThreadExecutor<?> engine, FabricPacketApply<T> apply) throws OffThreadException {
        if (!engine.isOnThread()) {
            engine.executeSync(() -> {
                if (listener.isConnectionOpen()) {
                    try {
                        apply.apply(listener);
                    } catch (Exception var3) {
                        if (listener.shouldCrashOnException()) {
                            throw var3;
                        }

                        LOGGER.error("Failed to handle packet {}, suppressing error", packet, var3);
                    }
                } else {
                    LOGGER.debug("Ignoring packet due to disconnection: {}", packet);
                }
            });
            throw OffThreadException.INSTANCE;
        }
    }

    @FunctionalInterface
    public interface FabricPacketApply <T extends PacketListener> {
        void apply(T listener);
    }
}
