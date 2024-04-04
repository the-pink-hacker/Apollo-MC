package com.thepinkhacker.apollo.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.text.Text;

public interface ApolloExtendedScreenHandlerFactory extends ExtendedScreenHandlerFactory {
    @Override
    default Text getDisplayName() {
        return Text.translatable("container.apollo." + getDisplayNameId());
    }

    String getDisplayNameId();
}
