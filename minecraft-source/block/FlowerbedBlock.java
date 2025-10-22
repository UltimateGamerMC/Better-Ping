/*
 * External method calls:
 *   Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
 *   Lnet/minecraft/util/BlockRotation;rotate(Lnet/minecraft/util/math/Direction;)Lnet/minecraft/util/math/Direction;
 *   Lnet/minecraft/block/BlockState;rotate(Lnet/minecraft/util/BlockRotation;)Lnet/minecraft/block/BlockState;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/block/FlowerbedBlock;createShapeFunction()Ljava/util/function/Function;
 *   Lnet/minecraft/block/FlowerbedBlock;createShapeFunction(Lnet/minecraft/state/property/EnumProperty;Lnet/minecraft/state/property/IntProperty;)Ljava/util/function/Function;
 *   Lnet/minecraft/block/FlowerbedBlock;createShapeFunction(Ljava/util/function/Function;)Ljava/util/function/Function;
 *   Lnet/minecraft/block/FlowerbedBlock;dropStack(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)V
 *   Lnet/minecraft/block/FlowerbedBlock;createCodec(Ljava/util/function/Function;)Lcom/mojang/serialization/MapCodec;
 */
package net.minecraft.block;

import com.mojang.serialization.MapCodec;
import java.util.function.Function;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.Segmented;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class FlowerbedBlock
extends PlantBlock
implements Fertilizable,
Segmented {
    public static final MapCodec<FlowerbedBlock> CODEC = FlowerbedBlock.createCodec(FlowerbedBlock::new);
    public static final EnumProperty<Direction> HORIZONTAL_FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty FLOWER_AMOUNT = Properties.FLOWER_AMOUNT;
    private final Function<BlockState, VoxelShape> shapeFunction;

    public MapCodec<FlowerbedBlock> getCodec() {
        return CODEC;
    }

    protected FlowerbedBlock(AbstractBlock.Settings arg) {
        super(arg);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(HORIZONTAL_FACING, Direction.NORTH)).with(FLOWER_AMOUNT, 1));
        this.shapeFunction = this.createShapeFunction();
    }

    private Function<BlockState, VoxelShape> createShapeFunction() {
        return this.createShapeFunction(this.createShapeFunction(HORIZONTAL_FACING, FLOWER_AMOUNT));
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(HORIZONTAL_FACING, rotation.rotate(state.get(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(HORIZONTAL_FACING)));
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (this.shouldAddSegment(state, context, FLOWER_AMOUNT)) {
            return true;
        }
        return super.canReplace(state, context);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.shapeFunction.apply(state);
    }

    @Override
    public double getHeight() {
        return 3.0;
    }

    @Override
    public IntProperty getAmountProperty() {
        return FLOWER_AMOUNT;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getPlacementState(ctx, this, FLOWER_AMOUNT, HORIZONTAL_FACING);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, FLOWER_AMOUNT);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = state.get(FLOWER_AMOUNT);
        if (i < 4) {
            world.setBlockState(pos, (BlockState)state.with(FLOWER_AMOUNT, i + 1), Block.NOTIFY_LISTENERS);
        } else {
            FlowerbedBlock.dropStack((World)world, pos, new ItemStack(this));
        }
    }
}

