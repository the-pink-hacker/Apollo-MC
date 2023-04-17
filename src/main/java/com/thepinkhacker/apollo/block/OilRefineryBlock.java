package com.thepinkhacker.apollo.block;

import com.thepinkhacker.apollo.block.entity.fluid.OilRefineryBlockEntity;
import com.thepinkhacker.apollo.fluid.PipeConnectable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class OilRefineryBlock extends BlockWithEntityModeled implements PipeConnectable {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty LIT = Properties.LIT;

    public OilRefineryBlock(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(LIT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(FACING, LIT);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public boolean canPipeConnect(Direction direction, BlockState state) {
        Direction side = state.get(FACING).rotateClockwise(Direction.Axis.Y);
        return side == direction || side.getOpposite() == direction || direction == Direction.UP;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new OilRefineryBlockEntity(pos, state);
    }
}
