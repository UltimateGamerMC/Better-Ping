/*
 * Internal private/static methods:
 *   Lnet/minecraft/block/RedstoneBlock;createCodec(Ljava/util/function/Function;)Lcom/mojang/serialization/MapCodec;
 */
package net.minecraft.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class RedstoneBlock
extends Block {
    public static final MapCodec<RedstoneBlock> CODEC = RedstoneBlock.createCodec(RedstoneBlock::new);

    public MapCodec<RedstoneBlock> getCodec() {
        return CODEC;
    }

    public RedstoneBlock(AbstractBlock.Settings arg) {
        super(arg);
    }

    @Override
    protected boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return 15;
    }
}

