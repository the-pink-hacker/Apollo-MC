package com.thepinkhacker.apollo.entity.projectile;

import com.thepinkhacker.apollo.block.ApolloBlocks;
import com.thepinkhacker.apollo.block.MeteoriteBlock;
import com.thepinkhacker.apollo.world.ApolloGameRules;
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

public class MeteoriteEntity extends ExplosiveProjectileEntity {
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
        if (!this.getWorld().isClient) {
            World.ExplosionSourceType explosionSourceType = this.getWorld().getGameRules().getBoolean(ApolloGameRules.DO_METEORITE_IMPACTS) ? World.ExplosionSourceType.TNT : World.ExplosionSourceType.NONE;
            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), Math.max(4.0f, (float)Math.random() * 8.0f), true, explosionSourceType);

            BlockPos meteoritePos = BlockPos.ofFloored(hitResult.getPos());
            if (this.getWorld().canSetBlock(meteoritePos)) {
                this.getWorld().setBlockState(meteoritePos, ((MeteoriteBlock) ApolloBlocks.METEORITE).getHotState());
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
}
