package com.thepinkhacker.apollo.server.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public abstract class ApolloCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            SpaceTimeCommand.register(dispatcher);
        });
    }
}
