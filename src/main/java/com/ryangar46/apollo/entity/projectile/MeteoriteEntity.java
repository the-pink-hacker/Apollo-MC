package com.ryangar46.apollo.entity.projectile;

import com.ryangar46.apollo.block.BlockManager;
import com.ryangar46.apollo.block.MeteoriteBlock;
import com.ryangar46.apollo.world.GameRuleManager;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MeteoriteEntity extends ExplosiveProjectileEntity implements GeoEntity {
    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);

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
            // TODO: Fix game rule
            //Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRuleManager.DO_METEORITE_IMPACTS) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.KEEP;
            this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), Math.max(4.0f, (float)Math.random() * 8.0f), true, World.ExplosionSourceType.TNT);

            BlockPos meteoritePos = new BlockPos(hitResult.getPos());
            if (this.world.canSetBlock(meteoritePos)) {
                this.world.setBlockState(meteoritePos, ((MeteoriteBlock)BlockManager.METEORITE).getHotState());
            }



            this.discard();
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected boolean isBurning() {
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericIdleController(this));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.CACHE;
    }
}
