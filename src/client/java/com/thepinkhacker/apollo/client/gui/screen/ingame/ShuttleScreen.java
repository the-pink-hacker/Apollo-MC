package com.thepinkhacker.apollo.client.gui.screen.ingame;

import com.thepinkhacker.apollo.entity.vehicle.ShuttleEntity;
import com.thepinkhacker.apollo.screen.ShuttleScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Identifier;

public class ShuttleScreen extends HandledScreen<ShuttleScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/horse.png");
    private final ShuttleEntity entity;
    private int mouseX;
    private int mouseY;

    public ShuttleScreen(ShuttleScreenHandler handler, PlayerInventory inventory, ShuttleEntity entity) {
        super(handler, inventory, entity.getDisplayName());
        this.entity = entity;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        InventoryScreen.drawEntity(
                context,
                x + 51,
                y + 60,
                17,
                x + 51 - this.mouseX,
                y + 75 - 50 - this.mouseY,
                this.entity
        );
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
