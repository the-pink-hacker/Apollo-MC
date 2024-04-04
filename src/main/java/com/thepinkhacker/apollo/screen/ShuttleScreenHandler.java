package com.thepinkhacker.apollo.screen;

import com.thepinkhacker.apollo.entity.vehicle.ShuttleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class ShuttleScreenHandler extends ApolloScreenHandler {
    private final Inventory inventory;
    private final ShuttleEntity entity;

    public ShuttleScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, ShuttleEntity entity) {
        // I don't know why this is null
        // This is how `HorseScreenHandler` does it though
        super(null, syncId);
        this.inventory = inventory;
        this.entity = entity;

        this.addPlayerInventorySlots(playerInventory);

        inventory.onOpen(playerInventory.player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return !this.entity.areInventoriesDifferent(this.inventory)
                && this.inventory.canPlayerUse(player)
                && this.entity.isAlive()
                && this.entity.distanceTo(player) < 8.0f;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }
}
