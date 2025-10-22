/*
 * External method calls:
 *   Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/block/SnowyBlock;createCodec(Ljava/util/function/Function;)Lcom/mojang/serialization/MapCodec;
 */
package net.minecraft.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class SnowyBlock
extends Block {
    public static final MapCodec<SnowyBlock> CODEC = SnowyBlock.createCodec(SnowyBlock::new);
    public static final BooleanProperty SNOWY = Properties.SNOWY;

    protected MapCodec<? extends SnowyBlock> getCodec() {
        return CODEC;
    }

    protected SnowyBlock(AbstractBlock.Settings arg) {
        super(arg);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(SNOWY, false));
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (direction == Direction.UP) {
            return (BlockState)state.with(SNOWY, SnowyBlock.isSnow(neighborState));
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState lv = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
        return (BlockState)this.getDefaultState().with(SNOWY, SnowyBlock.isSnow(lv));
    }

    protected static boolean isSnow(BlockState state) {
        return state.isIn(BlockTags.SNOW);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SNOWY);
    }
}

