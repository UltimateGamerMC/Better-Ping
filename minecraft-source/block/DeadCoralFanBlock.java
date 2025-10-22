/*
 * External method calls:
 *   Lnet/minecraft/block/Block;createColumnShape(DDD)Lnet/minecraft/util/shape/VoxelShape;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/block/DeadCoralFanBlock;createCodec(Ljava/util/function/Function;)Lcom/mojang/serialization/MapCodec;
 */
package net.minecraft.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractCoralBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class DeadCoralFanBlock
extends AbstractCoralBlock {
    public static final MapCodec<DeadCoralFanBlock> CODEC = DeadCoralFanBlock.createCodec(DeadCoralFanBlock::new);
    private static final VoxelShape SHAPE = Block.createColumnShape(12.0, 0.0, 4.0);

    public MapCodec<? extends DeadCoralFanBlock> getCodec() {
        return CODEC;
    }

    protected DeadCoralFanBlock(AbstractBlock.Settings arg) {
        super(arg);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}

