package com.ryangar46.apollo.block;

import com.ryangar46.apollo.tag.TagManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class FluidPipeBlock extends Block {
    public static final BooleanProperty NORTH_STATE = Properties.NORTH;
    public static final BooleanProperty EAST_STATE = Properties.EAST;
    public static final BooleanProperty SOUTH_STATE = Properties.SOUTH;
    public static final BooleanProperty WEST_STATE = Properties.WEST;
    public static final BooleanProperty UP_STATE = Properties.UP;
    public static final BooleanProperty DOWN_STATE = Properties.DOWN;
    private static final VoxelShape CENTER_SHAPE = Block.createCuboidShape(6.0f, 6.0f, 6.0f, 10.0f, 10.0f, 10.0f);
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(6.0f, 6.0f, 0.0f, 10.0f, 10.0f, 6.0f);
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(10.0f, 6.0f, 6.0f, 16.0f, 10.0f, 10.0f);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(6.0f, 6.0f, 10.0f, 10.0f, 10.0f, 16.0f);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0f, 6.0f, 6.0f, 6.0f, 10.0f, 10.0f);
    private static final VoxelShape UP_SHAPE = Block.createCuboidShape(6.0f, 10.0f, 6.0f, 10.0f, 16.0f, 10.0f);
    private static final VoxelShape DOWN_SHAPE = Block.createCuboidShape(6.0f, 0.0f, 6.0f, 10.0f, 6.0f, 10.0f);

    public FluidPipeBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState()
                .with(NORTH_STATE, false)
                .with(EAST_STATE, false)
                .with(SOUTH_STATE, false)
                .with(WEST_STATE, false)
                .with(UP_STATE, false)
                .with(DOWN_STATE, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        VoxelShape shape = CENTER_SHAPE;
        if (state.get(NORTH_STATE)) shape = VoxelShapes.union(shape, NORTH_SHAPE);
        if (state.get(EAST_STATE)) shape = VoxelShapes.union(shape, EAST_SHAPE);
        if (state.get(SOUTH_STATE)) shape = VoxelShapes.union(shape, SOUTH_SHAPE);
        if (state.get(WEST_STATE)) shape = VoxelShapes.union(shape, WEST_SHAPE);
        if (state.get(UP_STATE)) shape = VoxelShapes.union(shape, UP_SHAPE);
        if (state.get(DOWN_STATE)) shape = VoxelShapes.union(shape, DOWN_SHAPE);
        return shape;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        state = getState(state, world, pos);

        world.setBlockState(pos, state, 0);

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }


    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getState(getDefaultState(), ctx.getWorld(), ctx.getBlockPos());
    }

    private static BlockState getState(BlockState state, WorldAccess world, BlockPos pos) {
        return state.with(NORTH_STATE, canConnect(world.getBlockState(pos.north()).getBlock()))
                .with(EAST_STATE, canConnect(world.getBlockState(pos.east()).getBlock()))
                .with(SOUTH_STATE, canConnect(world.getBlockState(pos.south()).getBlock()))
                .with(WEST_STATE, canConnect(world.getBlockState(pos.west()).getBlock()))
                .with(UP_STATE, canConnect(world.getBlockState(pos.up()).getBlock()))
                .with(DOWN_STATE, canConnect(world.getBlockState(pos.down()).getBlock()));
    }

    private static boolean canConnect(Block block) {
        return block.getRegistryEntry().isIn(TagManager.FLUID_PIPE_CONNECTABLE_BLOCKS);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(NORTH_STATE, EAST_STATE, SOUTH_STATE, WEST_STATE, UP_STATE, DOWN_STATE);
    }
}
