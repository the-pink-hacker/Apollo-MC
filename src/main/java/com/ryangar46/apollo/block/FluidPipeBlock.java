package com.ryangar46.apollo.block;

import com.ryangar46.apollo.block.entity.FluidPipeBlockEntity;
import com.ryangar46.apollo.entity.EntityManager;
import com.ryangar46.apollo.tag.TagManager;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class FluidPipeBlock extends BlockWithEntity implements PipeConnectable, Waterloggable {
    public static final BooleanProperty NORTH_STATE = Properties.NORTH;
    public static final BooleanProperty EAST_STATE = Properties.EAST;
    public static final BooleanProperty SOUTH_STATE = Properties.SOUTH;
    public static final BooleanProperty WEST_STATE = Properties.WEST;
    public static final BooleanProperty UP_STATE = Properties.UP;
    public static final BooleanProperty DOWN_STATE = Properties.DOWN;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final IntProperty LEVEL = Properties.LEVEL_8;
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
                .with(DOWN_STATE, false)
                .with(WATERLOGGED, false)
                .with(LEVEL, 0)
        );
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
        if (state.get(WATERLOGGED)) world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getState(getDefaultState(), ctx.getWorld(), ctx.getBlockPos());
    }

    private static BlockState getState(BlockState state, WorldAccess world, BlockPos pos) {
        return state.with(NORTH_STATE, canConnect(Direction.NORTH, world, pos))
                .with(EAST_STATE, canConnect(Direction.EAST, world, pos))
                .with(SOUTH_STATE, canConnect(Direction.SOUTH, world, pos))
                .with(WEST_STATE, canConnect(Direction.WEST, world, pos))
                .with(UP_STATE, canConnect(Direction.UP, world, pos))
                .with(DOWN_STATE, canConnect(Direction.DOWN, world, pos))
                .with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER);
    }

    private static boolean canConnect(Direction direction, WorldAccess world, BlockPos pos) {
        BlockPos otherPos = pos.offset(direction);
        BlockState otherState = world.getBlockState(otherPos);
        Block otherBlock = otherState.getBlock();

        if (otherBlock instanceof PipeConnectable pipeConnectable) {
            return pipeConnectable.canPipeConnect(direction.getOpposite(), otherState) && otherBlock.getRegistryEntry().isIn(TagManager.FLUID_PIPE_CONNECTABLE_BLOCKS);
        }

        return otherBlock.getRegistryEntry().isIn(TagManager.FLUID_PIPE_CONNECTABLE_BLOCKS);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(NORTH_STATE, EAST_STATE, SOUTH_STATE, WEST_STATE, UP_STATE, DOWN_STATE, WATERLOGGED, LEVEL);
    }

    @Override
    public boolean canPipeConnect(Direction direction, BlockState state) {
        return true;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FluidPipeBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, EntityManager.FLUID_PIPE_BLOCK_ENTITY, FluidPipeBlockEntity::tick);
    }

    public static int getFluidLevel(BlockState state) {
        return state.get(LEVEL);
    }

    public static BlockState setFluidLevel(BlockState state, int level) {
        return state.with(LEVEL, level);
    }

    public static Collection<Direction> getConnections(WorldAccess world, BlockPos pos, BlockState state) {
        Collection<Direction> directions = new ArrayList<>();

        if (state.get(NORTH_STATE)) if (world.getBlockState(pos.north()).getBlock() instanceof FluidPipeBlock) directions.add(Direction.NORTH);
        if (state.get(EAST_STATE)) if (world.getBlockState(pos.east()).getBlock() instanceof FluidPipeBlock) directions.add(Direction.EAST);
        if (state.get(SOUTH_STATE)) if (world.getBlockState(pos.south()).getBlock() instanceof FluidPipeBlock) directions.add(Direction.SOUTH);
        if (state.get(WEST_STATE)) if (world.getBlockState(pos.west()).getBlock() instanceof FluidPipeBlock) directions.add(Direction.WEST);
        if (state.get(UP_STATE)) if (world.getBlockState(pos.up()).getBlock() instanceof FluidPipeBlock) directions.add(Direction.UP);
        if (state.get(DOWN_STATE)) if (world.getBlockState(pos.down()).getBlock() instanceof FluidPipeBlock) directions.add(Direction.DOWN);

        return directions;
    }
}
