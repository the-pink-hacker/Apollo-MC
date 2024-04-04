package com.thepinkhacker.apollo.item;

import com.thepinkhacker.apollo.block.ApolloBlocks;
import com.thepinkhacker.apollo.registry.tag.ApolloBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class OilBottleItem extends Item {
    public OilBottleItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        BlockState state = world.getBlockState(pos);
        ItemStack itemStack = context.getStack();

        if (context.getSide() == Direction.DOWN || !state.isIn(ApolloBlockTags.CONVERTABLE_TO_OILED_SAND)) return ActionResult.PASS;

        world.playSound(
                null,
                pos,
                SoundEvents.ENTITY_GENERIC_SPLASH,
                SoundCategory.BLOCKS,
                1.0f,
                1.0f
        );
        player.setStackInHand(
                context.getHand(),
                ItemUsage.exchangeStack(
                        itemStack,
                        player,
                        new ItemStack(Items.GLASS_BOTTLE)
                )
        );
        player.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));

        // Create particle effects on server
        if (world instanceof ServerWorld serverWorld) {
            for (int i = 0; i < 5; i++) {
                serverWorld.spawnParticles(
                        ParticleTypes.SPLASH,
                        pos.getX() + world.random.nextDouble(),
                        pos.getY() + 1,
                        pos.getZ() + world.random.nextDouble(),
                        1,
                        0.0d,
                        0.0d,
                        0.0d,
                        1.0d
                );
            }
        }

        world.playSound(
                null,
                pos,
                SoundEvents.ITEM_BOTTLE_EMPTY,
                SoundCategory.BLOCKS,
                1.0f,
                1.0f
        );
        world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
        world.setBlockState(pos, ApolloBlocks.OILED_SAND.getDefaultState());

        return ActionResult.success(world.isClient);
    }
}
