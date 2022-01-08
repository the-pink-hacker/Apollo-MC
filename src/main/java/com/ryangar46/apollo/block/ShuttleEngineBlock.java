package com.ryangar46.apollo.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class ShuttleEngineBlock extends Block {
    public static final VoxelShape BODY_SHAPE = Block.createCuboidShape(0.0f, 8.0f, 0.0f, 16.0f, 16.0f, 16.0f);
    public static final VoxelShape LOWER_BODY_SHAPE = Block.createCuboidShape(1.0f, 7.0f, 1.0f, 15.0f, 8.0f, 15.0f);
    public static final VoxelShape THRUSTER_CENTER_SHAPE = Block.createCuboidShape(6.0f, 0.0f, 6.0f, 10.0f, 7.0f, 10.0f);
    public static final VoxelShape THRUSTER_NORTH_SHAPE = Block.createCuboidShape(6.0f, -1.0f, 1.0f, 10.0f, 7.0f, 5.0f);
    public static final VoxelShape THRUSTER_EAST_SHAPE = Block.createCuboidShape(11.0f, -1.0f, 6.0f, 15.0f, 7.0f, 10.0f);
    public static final VoxelShape THRUSTER_SOUTH_SHAPE = Block.createCuboidShape(6.0f, -1.0f, 11.0f, 10.0f, 7.0f, 15.0f);
    public static final VoxelShape THRUSTER_WEST_SHAPE = Block.createCuboidShape(1.0f, -1.0f, 6.0f, 5.0f, 7.0f, 10.0f);
    public static final VoxelShape SHAPE = VoxelShapes.union(BODY_SHAPE, LOWER_BODY_SHAPE, THRUSTER_CENTER_SHAPE, THRUSTER_NORTH_SHAPE, THRUSTER_EAST_SHAPE, THRUSTER_SOUTH_SHAPE, THRUSTER_WEST_SHAPE);

    public ShuttleEngineBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
