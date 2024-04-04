package com.thepinkhacker.apollo.entity.vehicle;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.RideableInventory;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShuttleEntity extends MobEntity implements RideableInventory {
    private final SimpleInventory items = new SimpleInventory(0);
    private static final double THRUST = 0.1d;
    private static final double MAX_SPEED = 10.0d;

    public ShuttleEntity(EntityType<? extends ShuttleEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        //Inventories.readNbt(nbt, items);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        //Inventories.writeNbt(nbt, items);
        return super.writeNbt(nbt);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.shouldCancelInteraction()) {
            this.openInventory(player);
            return ActionResult.success(this.getWorld().isClient);
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

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        if (this.getFirstPassenger() instanceof LivingEntity livingEntity) {
            return livingEntity;
        }
        return null;
    }

    @Override
    public void travel(Vec3d movementInput) {
//        if (this.getControllingPassenger() instanceof PlayerEntity player) {
//            // Player is holding down W
//            if (player.forwardSpeed > 0.0f) {
//                // Add thrust
//                Vec3d currentVelocity = this.getVelocity();
//                this.setVelocity(currentVelocity.x, Math.min(currentVelocity.y + THRUST, MAX_SPEED), currentVelocity.z);
//                this.velocityDirty = true;
//
//                // TODO: Could get teleported above the escape height
//                int escapeHeight = this.getWorld().getGameRules().getInt(ApolloGameRules.SHUTTLE_ESCAPE_HEIGHT);
//
//                if (this.getBlockY() > escapeHeight) {
//                    if (this.getWorld() instanceof ServerWorld serverWorld) {
//                        // If at the moon, go to the overworld
//                        // If no at the moon, go to the moon
//                        RegistryKey<World> destination = this.getWorld().getRegistryKey() == ApolloWorlds.MOON ? World.OVERWORLD : ApolloWorlds.MOON;
//                        this.travelToPlanet(serverWorld.getServer().getWorld(destination), escapeHeight);
//                    }
//                }
//            }
//        }
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

    @Override
    public void openInventory(PlayerEntity player) {
        if (!this.getWorld().isClient && (!this.hasPassengers() || this.hasPassenger(player))) {
            if (player instanceof PlayerShuttleScreenOpener playerInterface) {
                playerInterface.apollo$openShuttleInventory(this, items);
            }
        }
    }

    public boolean areInventoriesDifferent(Inventory inventory) {
        return this.items != inventory;
    }

    protected void dropInventory() {
        super.dropInventory();
        for (int i = 0; i < this.items.size(); ++i) {
            ItemStack itemStack = this.items.getStack(i);
            if (!itemStack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemStack)) {
                this.dropStack(itemStack);
            }
        }
    }
}
