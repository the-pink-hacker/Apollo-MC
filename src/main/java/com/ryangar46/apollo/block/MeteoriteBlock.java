package com.ryangar46.apollo.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class MeteoriteBlock extends Block {
    public static final VoxelShape NORTH_SOUTH_SHAPE = Block.createCuboidShape(4.0f, 1.0f, 3.0f, 12.0f, 9.0f, 13.0f);
    public static final VoxelShape EAST_WEST_SHAPE = Block.createCuboidShape(3.0f, 1.0f, 4.0f, 13.0f, 9.0f, 12.0f);
    public static final VoxelShape UP_DOWN_SHAPE = Block.createCuboidShape(4.0f, 0.0f, 4.0f, 12.0f, 10.0f, 12.0f);
    public static final VoxelShape SHAPE = VoxelShapes.union(NORTH_SOUTH_SHAPE, EAST_WEST_SHAPE, UP_DOWN_SHAPE);

    public MeteoriteBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return hasTopRim(world, pos.down());
    }
}
