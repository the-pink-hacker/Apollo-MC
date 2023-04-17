package com.thepinkhacker.apollo.block.fluid;

import com.thepinkhacker.apollo.block.BlockWithEntityModeled;
import com.thepinkhacker.apollo.block.entity.ApolloBlockEntityTypes;
import com.thepinkhacker.apollo.block.entity.fluid.StorageTankBlockEntity;
import com.thepinkhacker.apollo.fluid.PipeConnectable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StorageTankBlock extends BlockWithEntityModeled implements PipeConnectable {
    public StorageTankBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ApolloBlockEntityTypes.STORAGE_TANK, StorageTankBlockEntity::tick);
    }

    @Override
    public boolean canPipeConnect(Direction direction, BlockState state) {
        return direction.getAxis().isVertical();
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StorageTankBlockEntity(pos, state);
    }
}
