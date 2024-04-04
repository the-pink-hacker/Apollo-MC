package com.thepinkhacker.apollo.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public abstract class ApolloScreenHandler extends ScreenHandler {
    private static final int SLOT_ROW_LENGTH = 9;
    private static final int SLOT_OFFSET = 8;
    private static final int SLOT_SIZE = 18;

    protected ApolloScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    public void addPlayerInventorySlots(PlayerInventory inventory) {
        this.addSlotGrid(inventory, 3, SLOT_ROW_LENGTH, 102);

        // Hotbar
        this.addSlotRow(inventory, SLOT_ROW_LENGTH, 142);
    }

    private void addSlotRow(Inventory inventory, int width, int y) {
        for(int column = 0; column < width; ++column) {
            this.addSlot(new Slot(
                    inventory,
                    column,
                    SLOT_OFFSET + column * SLOT_SIZE,
                    y
            ));
        }
    }

    private void addSlotRow(Inventory inventory, int indexOffset, int width, int y) {
        for(int column = 0; column < width; ++column) {
            this.addSlot(new Slot(
                    inventory,
                    column + indexOffset,
                    SLOT_OFFSET + column * SLOT_SIZE,
                    y
            ));
        }
    }

    private void addSlotGrid(Inventory inventory, int height, int width, int y) {
        for(int row = 0; row < height; ++row) {
            this.addSlotRow(
                    inventory,
                    row * SLOT_ROW_LENGTH + SLOT_ROW_LENGTH,
                    width,
                    y + row * SLOT_SIZE + -SLOT_SIZE
            );
        }
    }
}
