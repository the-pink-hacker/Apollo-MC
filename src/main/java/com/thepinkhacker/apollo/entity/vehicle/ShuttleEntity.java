package com.thepinkhacker.apollo.entity.vehicle;

import com.thepinkhacker.apollo.inventory.ImplementedInventory;
import com.thepinkhacker.apollo.world.ApolloGameRules;
import com.thepinkhacker.apollo.world.ApolloWorlds;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class ShuttleEntity extends MobEntity implements ImplementedInventory, GeoEntity {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(5, ItemStack.EMPTY);
    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);
    private static final double THRUST = 0.1d;
    private static final double MAX_SPEED = 10.0d;

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

        if (!getWorld().isClient) {
            return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public double getMountedHeightOffset() {
        return 0.75d;
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.getControllingPassenger() instanceof PlayerEntity player) {
            // Player is holding down W
            if (player.forwardSpeed > 0.0f) {
                // Add thrust
                Vec3d currentVelocity = this.getVelocity();
                this.setVelocity(currentVelocity.x, Math.min(currentVelocity.y + THRUST, MAX_SPEED), currentVelocity.z);
                this.velocityDirty = true;

                // TODO: Could get teleported above the escape height
                int escapeHeight = this.getWorld().getGameRules().getInt(ApolloGameRules.SHUTTLE_ESCAPE_HEIGHT);

                if (this.getBlockY() > escapeHeight) {
                    if (this.getWorld() instanceof ServerWorld serverWorld) {
                        // If at the moon, go to the overworld
                        // If no at the moon, go to the moon
                        RegistryKey<World> destination = this.getWorld().getRegistryKey() == ApolloWorlds.MOON ? World.OVERWORLD : ApolloWorlds.MOON;
                        this.travelToPlanet(serverWorld.getServer().getWorld(destination), escapeHeight);
                    }
                }
            }
        }
        super.travel(movementInput);
    }

    public boolean travelToPlanet(ServerWorld destination, double height) {
        if (destination == this.getWorld()) return false;

        Vec3d destinationPosition = this.getPos();
        destinationPosition = new Vec3d(destinationPosition.x, height, destinationPosition.z);
        TeleportTarget target = new TeleportTarget(destinationPosition, Vec3d.ZERO, this.getYaw(), this.getPitch());

        // Save passenger list before they're dismounted from the teleport
        List<Entity> passengers = this.getPassengerList();

         ShuttleEntity teleportedShuttle = FabricDimensions.teleport(this, destination, target);

         if (teleportedShuttle == null) return false;

        // Teleport passengers
        for (Entity passenger : passengers) {
            Entity teleportedPassenger = FabricDimensions.teleport(passenger, destination, target);

            if (teleportedPassenger == null) continue;

            // Remount passenger
            teleportedPassenger.startRiding(teleportedShuttle, true);
        }

        return true;
    }
}
