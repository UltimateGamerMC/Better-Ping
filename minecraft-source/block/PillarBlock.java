/*
 * External method calls:
 *   Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/block/PillarBlock;changeRotation(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/BlockRotation;)Lnet/minecraft/block/BlockState;
 *   Lnet/minecraft/block/PillarBlock;createCodec(Ljava/util/function/Function;)Lcom/mojang/serialization/MapCodec;
 */
package net.minecraft.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;

public class PillarBlock
extends Block {
    public static final MapCodec<PillarBlock> CODEC = PillarBlock.createCodec(PillarBlock::new);
    public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;

    public MapCodec<? extends PillarBlock> getCodec() {
        return CODEC;
    }

    public PillarBlock(AbstractBlock.Settings arg) {
        super(arg);
        this.setDefaultState((BlockState)this.getDefaultState().with(AXIS, Direction.Axis.Y));
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return PillarBlock.changeRotation(state, rotation);
    }

    public static BlockState changeRotation(BlockState state, BlockRotation rotation) {
        switch (rotation) {
            case COUNTERCLOCKWISE_90: 
            case CLOCKWISE_90: {
                switch (state.get(AXIS)) {
                    case X: {
                        return (BlockState)state.with(AXIS, Direction.Axis.Z);
                    }
                    case Z: {
                        return (BlockState)state.with(AXIS, Direction.Axis.X);
                    }
                }
                return state;
            }
        }
        return state;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(AXIS, ctx.getSide().getAxis());
    }
}

