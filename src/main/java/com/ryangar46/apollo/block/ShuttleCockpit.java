package com.ryangar46.apollo.block;

import com.ryangar46.apollo.Apollo;
import com.ryangar46.apollo.world.dimension.DimensionManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShuttleCockpit extends Block {
    public ShuttleCockpit(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            if (!player.isSneaking()) {
                MinecraftServer server = world.getServer();
                if (server != null) {
                    if (player instanceof ServerPlayerEntity serverPlayer) {
                        if (world.getRegistryKey() == World.OVERWORLD) {
                            ServerWorld overWorld = server.getWorld(DimensionManager.MOON);
                            if (overWorld != null) {
                                BlockPos playerPos = serverPlayer.getBlockPos();
                                BlockPos destPos = new BlockPos(playerPos.getX(), 128.0f, playerPos.getY());
                                serverPlayer.teleport(overWorld, destPos.getX(), destPos.getY(), destPos.getZ(), serverPlayer.bodyYaw, serverPlayer.prevPitch);
                                return ActionResult.SUCCESS;
                            }
                        } else if (world.getRegistryKey() == DimensionManager.MOON) {
                            ServerWorld moon = server.getWorld(World.OVERWORLD);
                            if (moon != null) {
                                BlockPos playerPos = serverPlayer.getBlockPos();
                                BlockPos destPos = new BlockPos(playerPos.getX(), 128.0f, playerPos.getY());
                                serverPlayer.teleport(moon, destPos.getX(), destPos.getY(), destPos.getZ(), serverPlayer.bodyYaw, serverPlayer.prevPitch);
                                return ActionResult.SUCCESS;
                            }
                        }
                    }
                }
            }
        }
        return ActionResult.FAIL;
    }
}
