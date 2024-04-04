package com.ryangar46.apollo.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public interface PipeConnectable {
    boolean canPipeConnect(Direction direction, BlockState state);
}
