package com.thepinkhacker.apollo.screen;

import com.thepinkhacker.apollo.Apollo;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class ApolloScreenHandlers {
    public static final ScreenHandlerType<FluidPipeScreenHandler> FLUID_PIPE = of("fluid_pipe", FluidPipeScreenHandler::new);

    private static <T extends ScreenHandler> ScreenHandlerType<T> of(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(
                Registries.SCREEN_HANDLER,
                Apollo.getIdentifier(id),
                new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES)
        );
    }
}
