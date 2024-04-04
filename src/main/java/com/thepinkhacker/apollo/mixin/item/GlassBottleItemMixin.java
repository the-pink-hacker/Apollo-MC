package com.thepinkhacker.apollo.mixin.item;

import com.thepinkhacker.apollo.item.ApolloItems;
import com.thepinkhacker.apollo.registry.tag.ApolloBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GlassBottleItem.class)
public abstract class GlassBottleItemMixin extends Item {
    private GlassBottleItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        BlockState state = world.getBlockState(pos);
        ItemStack itemStack = context.getStack();


        if (context.getSide() == Direction.DOWN || !state.isIn(ApolloBlockTags.OIL_BOTTLEABLE)) {
            return ActionResult.PASS;
        }

        player.setStackInHand(
                context.getHand(),
                ItemUsage.exchangeStack(
                        itemStack,
                        player,
                        new ItemStack(ApolloItems.OIL_BOTTLE)
                )
        );
        player.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));

        world.playSound(
                null,
                pos,
                SoundEvents.ITEM_BOTTLE_FILL,
                SoundCategory.BLOCKS,
                1.0f,
                1.0f
        );
        world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
        world.setBlockState(pos, Blocks.SAND.getDefaultState());

        return ActionResult.success(world.isClient);
    }
}
