package com.thepinkhacker.apollo.client.gui.screen;

import com.thepinkhacker.apollo.client.gui.screen.ingame.FluidPipeScreen;
import com.thepinkhacker.apollo.screen.ApolloScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ApolloClientScreenRegistry {
    public static void register() {
        HandledScreens.register(ApolloScreenHandlers.FLUID_PIPE, FluidPipeScreen::new);
    }
}
