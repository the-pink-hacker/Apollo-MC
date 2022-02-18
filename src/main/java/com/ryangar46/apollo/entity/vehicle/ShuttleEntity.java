package com.ryangar46.apollo.entity.vehicle;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.inventory.ImplementedInventory;
import com.ryangar46.apollo.gui.GuiDescription;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShuttleEntity extends MobEntity implements ImplementedInventory, NamedScreenHandlerFactory {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(2, ItemStack.EMPTY);

    public ShuttleEntity(EntityType<? extends ShuttleEntity> type, World world) {
        super(type, world);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, items);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, items);
        return super.writeNbt(nbt);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return ImplementedInventory.super.canPlayerUse(player);
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        BlockPos pos = BlockPos.ORIGIN;
        return new GuiDescription(syncId, inventory, ScreenHandlerContext.create(world, pos));
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        player.openHandledScreen(this);
        return ActionResult.SUCCESS;
    }
}
