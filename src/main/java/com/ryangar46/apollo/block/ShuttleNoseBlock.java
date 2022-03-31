package com.ryangar46.apollo.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class ShuttleNoseBlock extends Block {
    public static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0f, 0.0f, 0.0f, 16.0f, 2.0f, 16.0f),
            Block.createCuboidShape(1.0f, 2.0f, 1.0f, 15.0f, 4.0f, 15.0f),
            Block.createCuboidShape(2.0f, 4.0f, 2.0f, 14.0f, 6.0f, 14.0f),
            Block.createCuboidShape(3.0f, 6.0f, 3.0f, 13.0f, 7.0f, 13.0f),
            Block.createCuboidShape(5.0f, 7.0f, 5.0f, 11.0f, 8.0f, 11.0f)
    );

    public ShuttleNoseBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
