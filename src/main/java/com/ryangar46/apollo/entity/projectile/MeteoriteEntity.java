package com.ryangar46.apollo.entity.projectile;

import com.ryangar46.apollo.block.BlockManager;
import com.ryangar46.apollo.block.MeteoriteBlock;
import com.ryangar46.apollo.world.GameRuleManager;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class MeteoriteEntity extends ExplosiveProjectileEntity implements IAnimatable {
    private final AnimationFactory FACTORY = new AnimationFactory(this);

    public MeteoriteEntity(EntityType<? extends MeteoriteEntity> entityType, World world) {
        super(entityType, world);
    }

    public boolean isOnFire() {
        return false;
    }

    public float getEffectiveExplosionResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState, float max) {
        return canDestroy(blockState) ? Math.min(0.8f, max) : max;
    }

    public static boolean canDestroy(BlockState block) {
        return !block.isAir() && !block.isIn(BlockTags.WITHER_IMMUNE);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRuleManager.DO_METEORITE_EXPLOSIONS) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
            this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), Math.max(4.0f, (float)Math.random() * 8.0f), false, destructionType);

            BlockPos meteoritePos = new BlockPos(hitResult.getPos());
            if (this.world.canSetBlock(meteoritePos)) {
                this.world.setBlockState(meteoritePos, ((MeteoriteBlock)BlockManager.METEORITE).getHotState());
            }

            this.discard();
        }
    }

    @Override
    public boolean collides() {
        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected boolean isBurning() {
        return false;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return FACTORY;
    }
}
