/*
 * External method calls:
 *   Lnet/minecraft/block/PlantBlock;onEntityCollision(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/EntityCollisionHandler;)V
 *   Lnet/minecraft/world/World;breakBlock(Lnet/minecraft/util/math/BlockPos;ZLnet/minecraft/entity/Entity;)Z
 *   Lnet/minecraft/block/Block;createColumnShape(DDD)Lnet/minecraft/util/shape/VoxelShape;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/block/LilyPadBlock;createCodec(Ljava/util/function/Function;)Lcom/mojang/serialization/MapCodec;
 */
package net.minecraft.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IceBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class LilyPadBlock
extends PlantBlock {
    public static final MapCodec<LilyPadBlock> CODEC = LilyPadBlock.createCodec(LilyPadBlock::new);
    private static final VoxelShape SHAPE = Block.createColumnShape(14.0, 0.0, 1.5);

    public MapCodec<LilyPadBlock> getCodec() {
        return CODEC;
    }

    protected LilyPadBlock(AbstractBlock.Settings arg) {
        super(arg);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler) {
        super.onEntityCollision(state, world, pos, entity, handler);
        if (world instanceof ServerWorld && entity instanceof AbstractBoatEntity) {
            world.breakBlock(new BlockPos(pos), true, entity);
        }
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        FluidState lv = world.getFluidState(pos);
        FluidState lv2 = world.getFluidState(pos.up());
        return (lv.getFluid() == Fluids.WATER || floor.getBlock() instanceof IceBlock) && lv2.getFluid() == Fluids.EMPTY;
    }
}

