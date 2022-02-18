package com.ryangar46.apollo.gui;

import com.ryangar46.apollo.Apollo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ScreenManager {
    public static final ScreenHandlerType<GuiDescription> SHUTTLE_SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(new Identifier(Apollo.MOD_ID, "shuttle"), (syncId, inventory) -> new GuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY));

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        ScreenRegistry.<GuiDescription, BlockScreen>register(ScreenManager.SHUTTLE_SCREEN_HANDLER_TYPE, (gui, inventory, title) -> new BlockScreen(gui, inventory.player, title));
    }
}
