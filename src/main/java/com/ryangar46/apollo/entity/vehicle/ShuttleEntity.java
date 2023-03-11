package com.ryangar46.apollo.entity.vehicle;

import com.ryangar46.apollo.inventory.ImplementedInventory;
import com.ryangar46.apollo.world.ApolloWorlds;
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
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class ShuttleEntity extends MobEntity implements ImplementedInventory, GeoEntity {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(5, ItemStack.EMPTY);
    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);
    private static final double FLY_SPEED = 0.2d;
    private static final int EXIT_PLANET_Y = 128;

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

    @Override
    public void travel(Vec3d movementInput) {
        if (this.getPrimaryPassenger() instanceof PlayerEntity player) {
            player.sendMessage(Text.literal(this.getBlockY() + ""));
            // Player is holding down W
            if (player.forwardSpeed > 0.0f) {
                // TODO: Add acceleration to shuttle
                this.setVelocity(0.0d, FLY_SPEED, 0.0d);

                if (this.getBlockY() > EXIT_PLANET_Y) {
                    MinecraftServer server = this.getServer();
                    if (server != null) {
                        // If at the moon, go to the overworld
                        // If no at the moon, go to the moon
                        RegistryKey<World> destination = this.world.getRegistryKey() == ApolloWorlds.MOON ? World.OVERWORLD : ApolloWorlds.MOON;
                        this.travelToPlanet(this.getServer().getWorld(destination));
                    }
                }
            }
        }
        super.travel(movementInput);
    }

    public boolean travelToPlanet(ServerWorld destination) {
        if (destination == this.getWorld()) return false;

        // Teleport passengers
        List<Entity> passengers = this.getPassengerList();
        for (Entity passenger : passengers) {
            if (passenger instanceof ServerPlayerEntity player) {
                player.teleport(destination, player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch());
                continue;
            }
            // TODO: Teleport non-player passengers
        }

        // TODO: Teleport shuttle

        return true;
    }
}
