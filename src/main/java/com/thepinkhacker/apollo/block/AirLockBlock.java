package com.thepinkhacker.apollo.block;

import com.thepinkhacker.apollo.item.ApolloItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class AirLockBlock extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = Properties.HORIZONTAL_AXIS;
    private static final VoxelShape X_SHAPE = Block.createCuboidShape(0.0d, 0.0d, 3.0d, 16.0d, 16.0d, 13.0d);
    private static final VoxelShape Z_SHAPE = Block.createCuboidShape(3.0d, 0.0d, 0.0d, 13.0d, 16.0d, 16.0d);

    public AirLockBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(AXIS, Direction.Axis.X));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(AXIS) == Direction.Axis.Z ? Z_SHAPE : X_SHAPE;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(ApolloItems.AIRLOCK_FRAME);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> {
                return switch (state.get(AXIS)) {
                    case Z -> state.with(AXIS, Direction.Axis.X);
                    case X -> state.with(AXIS, Direction.Axis.Z);
                    default -> state;
                };
            }
            default -> {
                return state;
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }
}
