package com.ryangar46.apollo.client.gui.screen.ingame;

import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.text.Text;

public class ShuttleWorkbenchScreen extends CraftingScreen {
    public ShuttleWorkbenchScreen(CraftingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
}
