package com.thepinkhacker.apollo.client.gui.screen.ingame;

import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.screen.FluidPipeScreenHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.ResourceAmount;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class FluidPipeScreen extends HandledScreen<FluidPipeScreenHandler> {
    private static final Identifier BACKGROUND = Apollo.getIdentifier("textures/gui/container/fluid_pipe.png");

    public FluidPipeScreen(FluidPipeScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private static Optional<ResourceAmount<FluidVariant>> getStorage(ScreenHandler handler) {
        if (handler instanceof FluidPipeScreenHandler fluidPipeHandler) {
            return Optional.ofNullable(fluidPipeHandler.getFluidStorage());
        } else {
            return Optional.empty();
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(BACKGROUND, this.x, this.y, 0, 0, backgroundWidth, backgroundHeight);

        // Fluid
        Optional<ResourceAmount<FluidVariant>> storage = getStorage(handler);
        long fluidAmount = storage.map(ResourceAmount::amount).orElse(0L);
        Fluid fluid = storage.map(ResourceAmount::resource).orElse(FluidVariant.blank()).getFluid();

        int maxProgress = 53;
        int progress = (int) ((52 * (double) fluidAmount / FluidConstants.BUCKET)) + 1;

        context.drawTexture(BACKGROUND, this.x + 79, this.y + maxProgress - progress + 16, 176, maxProgress - progress, 17, progress);
    }

    @Override
    protected void init() {
        super.init();

        // Center title to match blocks such as the dispenser
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}
