package com.thepinkhacker.apollo.client.gui.screen.ingame;

import com.thepinkhacker.apollo.screen.FluidPipeScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FluidPipeScreen extends HandledScreen<FluidPipeScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/dispenser.png");

    public FluidPipeScreen(FluidPipeScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    protected void init() {
        super.init();

        // Center title to match blocks such as the dispenser
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}
