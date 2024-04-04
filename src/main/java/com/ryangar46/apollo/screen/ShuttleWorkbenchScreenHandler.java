package com.ryangar46.apollo.screen;

import com.ryangar46.apollo.block.BlockManager;
import com.ryangar46.apollo.recipe.RecipeManager;
import com.ryangar46.apollo.recipe.ShuttleWorkbenchRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.Optional;

public class ShuttleWorkbenchScreenHandler extends CraftingScreenHandler {
    public ShuttleWorkbenchScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(syncId, playerInventory);
    }

    public ShuttleWorkbenchScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(syncId, playerInventory, context);
    }

    protected static void updateResult(ScreenHandler handler, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory) {
        if (!world.isClient) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
            ItemStack itemStack = ItemStack.EMPTY;
            Optional<ShuttleWorkbenchRecipe> optional = world.getServer().getRecipeManager().getFirstMatch(RecipeManager.SHUTTLE_WORKBENCH_RECIPE_TYPE, craftingInventory, world);
            if (optional.isPresent()) {
                ShuttleWorkbenchRecipe craftingRecipe = optional.get();
                if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, craftingRecipe)) {
                    itemStack = craftingRecipe.craft(craftingInventory);
                }
            }

            resultInventory.setStack(0, itemStack);
            handler.setPreviousTrackedSlot(0, itemStack);
            serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
        }
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        this.context.run((world, pos) -> ShuttleWorkbenchScreenHandler.updateResult(this, world, this.player, this.input, this.result));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, BlockManager.SHUTTLE_WORKBENCH);
    }

}
