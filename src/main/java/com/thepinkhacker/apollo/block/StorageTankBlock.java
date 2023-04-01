package com.thepinkhacker.apollo.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public class StorageTankBlock extends Block implements PipeConnectable {
    public StorageTankBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPipeConnect(Direction direction, BlockState state) {
        return direction.getAxis().isVertical();
    }
}
