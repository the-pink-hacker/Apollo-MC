package com.ryangar46.apollo.entity.vehicle;

import com.ryangar46.apollo.inventory.ImplementedInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ShuttleEntity extends MobEntity implements ImplementedInventory, GeoEntity {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(5, ItemStack.EMPTY);
    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);

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
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericIdleController(this));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.CACHE;
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.shouldCancelInteraction()) {
            return ActionResult.PASS;
        }

        if (!world.isClient) {
            return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public double getMountedHeightOffset() {
        return 0.75d;
    }

    @Nullable
    @Override
    public Entity getPrimaryPassenger() {
        return getFirstPassenger();
    }

    private ActionResult teleportPlayer(ServerPlayerEntity player, RegistryKey<World> world, MinecraftServer server) {
        ServerWorld serverWorld = server.getWorld(world);

        if (serverWorld != null) {
            BlockPos playerPos = player.getBlockPos();
            BlockPos destPos = new BlockPos(playerPos.getX(), 128.0f, playerPos.getY());
            player.teleport(serverWorld, destPos.getX(), destPos.getY(), destPos.getZ(), player.bodyYaw, player.prevPitch);
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }
}
