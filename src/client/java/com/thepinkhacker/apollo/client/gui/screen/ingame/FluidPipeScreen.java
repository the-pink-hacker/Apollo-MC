package com.thepinkhacker.apollo.client.gui.screen.ingame;

import com.thepinkhacker.apollo.block.entity.fluid.FluidPipeBlockEntity;
import com.thepinkhacker.apollo.fluid.FluidCarrierStorage;
import com.thepinkhacker.apollo.screen.FluidPipeScreenHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.ResourceAmount;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;
import java.util.Optional;

public class FluidPipeScreen extends HandledScreen<FluidPipeScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/dispenser.png");

    public FluidPipeScreen(FluidPipeScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        int x = width / 2;
        int y = height / 2;
        Optional<ResourceAmount<FluidVariant>> storage = getStorage(handler);
        context.drawText(textRenderer, storage.map(ResourceAmount::amount).map(Objects::toString).orElse("Nothing"), x, y, 0x000000, false);
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
