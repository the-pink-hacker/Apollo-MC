package com.thepinkhacker.apollo.screen;

import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.text.Text;

public interface ApolloNamedScreenHandlerFactory extends NamedScreenHandlerFactory {
    @Override
    default Text getDisplayName() {
        return Text.translatable("container.apollo." + getDisplayNameId());
    }

    String getDisplayNameId();
}
