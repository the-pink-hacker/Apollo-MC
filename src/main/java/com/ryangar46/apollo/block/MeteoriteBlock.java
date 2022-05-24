package com.ryangar46.apollo.block;

import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class MeteoriteBlock extends FallingBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final IntProperty HEAT = IntProperty.of("heat", 0, 2);
    public static final VoxelShape NORTH_SOUTH_SHAPE = Block.createCuboidShape(4.0f, 1.0f, 3.0f, 12.0f, 9.0f, 13.0f);
    public static final VoxelShape EAST_WEST_SHAPE = Block.createCuboidShape(3.0f, 1.0f, 4.0f, 13.0f, 9.0f, 12.0f);
    public static final VoxelShape UP_DOWN_SHAPE = Block.createCuboidShape(4.0f, 0.0f, 4.0f, 12.0f, 10.0f, 12.0f);
    public static final VoxelShape SHAPE = VoxelShapes.union(NORTH_SOUTH_SHAPE, EAST_WEST_SHAPE, UP_DOWN_SHAPE);

    public MeteoriteBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(WATERLOGGED) && state.get(HEAT) > 0) {
            cool(pos, world);
        }
        super.scheduledTick(state, world, pos, random);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextFloat() < 1.0f / 8.0f) {
            world.setBlockState(pos, state.with(HEAT, Math.max(0, state.get(HEAT) - 1)));
        }
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.damage(DamageSource.HOT_FLOOR, state.get(HEAT));
        }

        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        player.setOnFireFor(state.get(HEAT) * 4);
        super.onBlockBreakStart(state, world, pos, player);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return hasTopRim(world, pos.down());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(WATERLOGGED, HEAT);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        if (state.get(HEAT) > 0 && world.getBlockState(pos.up()).getBlock() == Blocks.WATER) {
            cool(pos, world);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        world.createAndScheduleBlockTick(pos, asBlock(), 1);
        return super.onUse(state, world, pos, player, hand, hit);
    }

    public void cool(BlockPos pos, WorldAccess world) {
        world.setBlockState(pos, getStateManager().getDefaultState(), 0);
        world.syncWorldEvent(2009, pos, 0);
        world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, (1.0F + world.getRandom().nextFloat() * 0.2F) * 0.7F);
    }

    public BlockState getHotState() {
        return this.getDefaultState().with(HEAT, 2);
    }
}
