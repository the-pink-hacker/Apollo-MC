package com.ryangar46.apollo.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class LaunchpadBlock extends Block {
    public static final BooleanProperty NORTH_STATE = BooleanProperty.of("north");
    public static final BooleanProperty EAST_STATE = BooleanProperty.of("east");
    public static final BooleanProperty SOUTH_STATE = BooleanProperty.of("south");
    public static final BooleanProperty WEST_STATE = BooleanProperty.of("west");
    private static final VoxelShape CENTER_SHAPE = Block.createCuboidShape(0.0f, 0.0f, 0.0f, 16.0f, 15.0f, 16.0f);
    private static final VoxelShape NORTH_SIDE_SHAPE = Block.createCuboidShape(0.0f, 15.0f, 0.0f, 16.0f, 16.0f, 1.0f);
    private static final VoxelShape EAST_SIDE_SHAPE = Block.createCuboidShape(15.0f, 15.0f, 0.0f, 16.0f, 16.0f, 16.0f);
    private static final VoxelShape SOUTH_SIDE_SHAPE = Block.createCuboidShape(0.0f, 15.0f, 15.0f, 16.0f, 16.0f, 16.0f);
    private static final VoxelShape WEST_SIDE_SHAPE = Block.createCuboidShape(0.0f, 15.0f, 0.0f, 1.0f, 16.0f, 16.0f);

    public LaunchpadBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        VoxelShape shape = CENTER_SHAPE;
        if (state.get(NORTH_STATE)) shape = VoxelShapes.union(shape, NORTH_SIDE_SHAPE);
        if (state.get(EAST_STATE)) shape = VoxelShapes.union(shape, EAST_SIDE_SHAPE);
        if (state.get(SOUTH_STATE)) shape = VoxelShapes.union(shape, SOUTH_SIDE_SHAPE);
        if (state.get(WEST_STATE)) shape = VoxelShapes.union(shape, WEST_SIDE_SHAPE);
        return shape;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (world.getBlockState(pos.north()).getBlock() == this) {
            state = state.with(NORTH_STATE, false);
        }
        if (world.getBlockState(pos.east()).getBlock() == this) {
            state = state.with(EAST_STATE, false);
        }
        if (world.getBlockState(pos.south()).getBlock() == this) {
            state = state.with(SOUTH_STATE, false);
        }
        if (world.getBlockState(pos.west()).getBlock() == this) {
            state = state.with(WEST_STATE, false);
        }
        world.setBlockState(pos, state, 0);

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (world.getBlockState(pos.north()).getBlock() == this) {
            world.setBlockState(pos.north(), world.getBlockState(pos.north()).with(SOUTH_STATE, true), 0);
        }
        if (world.getBlockState(pos.east()).getBlock() == this) {
            world.setBlockState(pos.east(), world.getBlockState(pos.east()).with(WEST_STATE, true), 0);
        }
        if (world.getBlockState(pos.south()).getBlock() == this) {
            world.setBlockState(pos.south(), world.getBlockState(pos.south()).with(NORTH_STATE, true), 0);
        }
        if (world.getBlockState(pos.west()).getBlock() == this) {
            world.setBlockState(pos.west(), world.getBlockState(pos.west()).with(EAST_STATE, true), 0);
        }

        super.onBroken(world, pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(NORTH_STATE, EAST_STATE, SOUTH_STATE, WEST_STATE);
    }
}
