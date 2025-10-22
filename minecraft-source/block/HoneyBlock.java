/*
 * External method calls:
 *   Lnet/minecraft/entity/Entity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V
 *   Lnet/minecraft/world/World;sendEntityStatus(Lnet/minecraft/entity/Entity;B)V
 *   Lnet/minecraft/entity/damage/DamageSources;fall()Lnet/minecraft/entity/damage/DamageSource;
 *   Lnet/minecraft/entity/Entity;handleFallDamage(DFLnet/minecraft/entity/damage/DamageSource;)Z
 *   Lnet/minecraft/block/TranslucentBlock;onEntityCollision(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/EntityCollisionHandler;)V
 *   Lnet/minecraft/advancement/criterion/SlideDownBlockCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/block/BlockState;)V
 *   Lnet/minecraft/world/World;addParticleClient(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V
 *   Lnet/minecraft/block/Block;createColumnShape(DDD)Lnet/minecraft/util/shape/VoxelShape;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/block/HoneyBlock;triggerAdvancement(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/BlockPos;)V
 *   Lnet/minecraft/block/HoneyBlock;updateSlidingVelocity(Lnet/minecraft/entity/Entity;)V
 *   Lnet/minecraft/block/HoneyBlock;addCollisionEffects(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;)V
 *   Lnet/minecraft/block/HoneyBlock;addParticles(Lnet/minecraft/entity/Entity;I)V
 *   Lnet/minecraft/block/HoneyBlock;createCodec(Ljava/util/function/Function;)Lcom/mojang/serialization/MapCodec;
 */
package net.minecraft.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TranslucentBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class HoneyBlock
extends TranslucentBlock {
    public static final MapCodec<HoneyBlock> CODEC = HoneyBlock.createCodec(HoneyBlock::new);
    private static final double field_31101 = 0.13;
    private static final double field_31102 = 0.08;
    private static final double field_31103 = 0.05;
    private static final int TICKS_PER_SECOND = 20;
    private static final VoxelShape SHAPE = Block.createColumnShape(14.0, 0.0, 15.0);

    public MapCodec<HoneyBlock> getCodec() {
        return CODEC;
    }

    public HoneyBlock(AbstractBlock.Settings arg) {
        super(arg);
    }

    private static boolean hasHoneyBlockEffects(Entity entity) {
        return entity instanceof LivingEntity || entity instanceof AbstractMinecartEntity || entity instanceof TntEntity || entity instanceof AbstractBoatEntity;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        entity.playSound(SoundEvents.BLOCK_HONEY_BLOCK_SLIDE, 1.0f, 1.0f);
        if (!world.isClient()) {
            world.sendEntityStatus(entity, EntityStatuses.DRIP_RICH_HONEY);
        }
        if (entity.handleFallDamage(fallDistance, 0.2f, world.getDamageSources().fall())) {
            entity.playSound(this.soundGroup.getFallSound(), this.soundGroup.getVolume() * 0.5f, this.soundGroup.getPitch() * 0.75f);
        }
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler) {
        if (this.isSliding(pos, entity)) {
            this.triggerAdvancement(entity, pos);
            this.updateSlidingVelocity(entity);
            this.addCollisionEffects(world, entity);
        }
        super.onEntityCollision(state, world, pos, entity, handler);
    }

    private static double getOldVelocityY(double d) {
        return d / (double)0.98f + 0.08;
    }

    private static double getNewVelocityY(double d) {
        return (d - 0.08) * (double)0.98f;
    }

    private boolean isSliding(BlockPos pos, Entity entity) {
        if (entity.isOnGround()) {
            return false;
        }
        if (entity.getY() > (double)pos.getY() + 0.9375 - 1.0E-7) {
            return false;
        }
        if (HoneyBlock.getOldVelocityY(entity.getVelocity().y) >= -0.08) {
            return false;
        }
        double d = Math.abs((double)pos.getX() + 0.5 - entity.getX());
        double e = Math.abs((double)pos.getZ() + 0.5 - entity.getZ());
        double f = 0.4375 + (double)(entity.getWidth() / 2.0f);
        return d + 1.0E-7 > f || e + 1.0E-7 > f;
    }

    private void triggerAdvancement(Entity entity, BlockPos pos) {
        if (entity instanceof ServerPlayerEntity && entity.getEntityWorld().getTime() % 20L == 0L) {
            Criteria.SLIDE_DOWN_BLOCK.trigger((ServerPlayerEntity)entity, entity.getEntityWorld().getBlockState(pos));
        }
    }

    private void updateSlidingVelocity(Entity entity) {
        Vec3d lv = entity.getVelocity();
        if (HoneyBlock.getOldVelocityY(entity.getVelocity().y) < -0.13) {
            double d = -0.05 / HoneyBlock.getOldVelocityY(entity.getVelocity().y);
            entity.setVelocity(new Vec3d(lv.x * d, HoneyBlock.getNewVelocityY(-0.05), lv.z * d));
        } else {
            entity.setVelocity(new Vec3d(lv.x, HoneyBlock.getNewVelocityY(-0.05), lv.z));
        }
        entity.onLanding();
    }

    private void addCollisionEffects(World world, Entity entity) {
        if (HoneyBlock.hasHoneyBlockEffects(entity)) {
            if (world.random.nextInt(5) == 0) {
                entity.playSound(SoundEvents.BLOCK_HONEY_BLOCK_SLIDE, 1.0f, 1.0f);
            }
            if (!world.isClient() && world.random.nextInt(5) == 0) {
                world.sendEntityStatus(entity, EntityStatuses.DRIP_HONEY);
            }
        }
    }

    public static void addRegularParticles(Entity entity) {
        HoneyBlock.addParticles(entity, 5);
    }

    public static void addRichParticles(Entity entity) {
        HoneyBlock.addParticles(entity, 10);
    }

    private static void addParticles(Entity entity, int count) {
        if (!entity.getEntityWorld().isClient()) {
            return;
        }
        BlockState lv = Blocks.HONEY_BLOCK.getDefaultState();
        for (int j = 0; j < count; ++j) {
            entity.getEntityWorld().addParticleClient(new BlockStateParticleEffect(ParticleTypes.BLOCK, lv), entity.getX(), entity.getY(), entity.getZ(), 0.0, 0.0, 0.0);
        }
    }
}

