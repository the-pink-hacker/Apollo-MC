package com.thepinkhacker.apollo.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AirlockControllerBlock extends Block {
    private static final BooleanProperty POWERED = Properties.POWERED;
    private static final BooleanProperty OPEN = Properties.OPEN;

    public AirlockControllerBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState()
                .with(POWERED, false)
                .with(OPEN, false)
        );
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        boolean isReceivingPower = world.isReceivingRedstonePower(pos);

        // Update powered state
        if (state.get(POWERED) != isReceivingPower) {
            state = state.with(POWERED, isReceivingPower);
            world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
        }

        // Only continue if it's still powered
        if (isReceivingPower) return;

        // TODO: Open/close airlock
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED).add(OPEN);
    }
}
