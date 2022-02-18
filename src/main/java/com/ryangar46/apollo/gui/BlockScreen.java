package com.ryangar46.apollo.gui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class BlockScreen extends CottonInventoryScreen<GuiDescription> {
    public BlockScreen(GuiDescription gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
}
