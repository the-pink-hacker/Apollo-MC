package com.thepinkhacker.apollo.block.fluid;

import com.thepinkhacker.apollo.block.BlockWithEntityModeled;
import com.thepinkhacker.apollo.block.entity.ApolloBlockEntityTypes;
import com.thepinkhacker.apollo.block.entity.fluid.FluidValvePipeBlockEntity;
import com.thepinkhacker.apollo.fluid.PipeConnectable;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

// TODO: Fix lighting issue when standing next to pipe
public class FluidValvePipeBlock extends BlockWithEntityModeled implements PipeConnectable, Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty OPEN = Properties.OPEN;
    public static final EnumProperty<Direction.Axis> AXIS_STATE = Properties.AXIS;
    private static final VoxelShape HORIZONTAL_VALVE_SHAPE = VoxelShapes.union(
            createCuboidShape(7.0d, 10.0d, 7.0d, 9.0d, 11.0d, 9.0d),
            createCuboidShape(6.0d, 11.0d, 5.0d, 10.0d, 12.0d, 11.0d),
            createCuboidShape(5.0d, 11.0d, 6.0d, 11.0d, 12.0d, 10.0d)
    );
    private static final VoxelShape X_SHAPE = VoxelShapes.union(
            HORIZONTAL_VALVE_SHAPE,
            createCuboidShape(0.0d, 6.0d, 6.0d, 16.0d, 10.0d, 10.0d)
    );
    private static final VoxelShape Y_SHAPE = VoxelShapes.union(
            createCuboidShape(6.0d, 0.0d, 6.0d, 10.0d, 16.0d, 10.0d),
            // Valve
            createCuboidShape(10.0d, 7.0d, 7.0d, 11.0d, 9.0d, 9.0d),
            createCuboidShape(11.0d, 5.0d, 6.0d, 12.0d, 11.0d, 10.0d),
            createCuboidShape(11.0d, 6.0d, 5.0d, 12.0d, 10.0d, 11.0d)
    );
    private static final VoxelShape Z_SHAPE = VoxelShapes.union(
            HORIZONTAL_VALVE_SHAPE,
            createCuboidShape(6.0d, 6.0d, 0.0d, 10.0d, 10.0d, 16.0d)
    );

    public FluidValvePipeBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState()
                .with(AXIS_STATE, Direction.Axis.X)
                .with(OPEN, true)
                .with(WATERLOGGED, false)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> manager) {
        manager.add(AXIS_STATE, OPEN, WATERLOGGED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState()
                .with(AXIS_STATE, ctx.getPlayerLookDirection().getAxis())
                .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(AXIS_STATE)) {
            case X -> X_SHAPE;
            case Y -> Y_SHAPE;
            case Z -> Z_SHAPE;
        };
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ApolloBlockEntityTypes.FLUID_VALVE_PIPE, FluidValvePipeBlockEntity::tick);
    }

    @Override
    public boolean canPipeConnect(Direction direction, BlockState state) {
        return direction.getAxis() == state.get(AXIS_STATE);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FluidValvePipeBlockEntity(pos, state);
    }

    public boolean isOpen(BlockState state) {
        return state.get(OPEN);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        state = state.cycle(OPEN);
        world.setBlockState(pos, state, Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD);
        world.emitGameEvent(player, this.isOpen(state) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        return ActionResult.success(world.isClient);
    }
}
