/*
 * External method calls:
 *   Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
 *   Lnet/minecraft/block/Block;createColumnShape(DDD)Lnet/minecraft/util/shape/VoxelShape;
 *   Lnet/minecraft/block/Block;createCuboidZShape(DDDDD)Lnet/minecraft/util/shape/VoxelShape;
 *   Lnet/minecraft/util/shape/VoxelShapes;union(Lnet/minecraft/util/shape/VoxelShape;Lnet/minecraft/util/shape/VoxelShape;)Lnet/minecraft/util/shape/VoxelShape;
 *   Lnet/minecraft/util/shape/VoxelShapes;createHorizontalFacingShapeMap(Lnet/minecraft/util/shape/VoxelShape;)Ljava/util/Map;
 *   Lnet/minecraft/util/shape/VoxelShapes;combineAndSimplify(Lnet/minecraft/util/shape/VoxelShape;Lnet/minecraft/util/shape/VoxelShape;Lnet/minecraft/util/function/BooleanBiFunction;)Lnet/minecraft/util/shape/VoxelShape;
 *   Lnet/minecraft/util/shape/VoxelShapes;createFacingShapeMap(Lnet/minecraft/util/shape/VoxelShape;Lnet/minecraft/util/math/Vec3d;)Ljava/util/Map;
 *   Lnet/minecraft/entity/player/PlayerEntity;openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;
 *   Lnet/minecraft/entity/player/PlayerEntity;incrementStat(Lnet/minecraft/util/Identifier;)V
 *   Lnet/minecraft/util/ItemScatterer;onStateReplaced(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V
 *   Lnet/minecraft/screen/ScreenHandler;calculateComparatorOutput(Lnet/minecraft/block/entity/BlockEntity;)I
 *   Lnet/minecraft/util/BlockRotation;rotate(Lnet/minecraft/util/math/Direction;)Lnet/minecraft/util/math/Direction;
 *   Lnet/minecraft/block/BlockState;rotate(Lnet/minecraft/util/BlockRotation;)Lnet/minecraft/block/BlockState;
 *   Lnet/minecraft/block/entity/HopperBlockEntity;onEntityCollided(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/Entity;Lnet/minecraft/block/entity/HopperBlockEntity;)V
 *   Lnet/minecraft/util/shape/VoxelShapes;fullCube()Lnet/minecraft/util/shape/VoxelShape;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/block/HopperBlock;createShapeFunction(Lnet/minecraft/util/shape/VoxelShape;)Ljava/util/function/Function;
 *   Lnet/minecraft/block/HopperBlock;createShapeFunction(Ljava/util/function/Function;[Lnet/minecraft/state/property/Property;)Ljava/util/function/Function;
 *   Lnet/minecraft/block/HopperBlock;validateTicker(Lnet/minecraft/block/entity/BlockEntityType;Lnet/minecraft/block/entity/BlockEntityType;Lnet/minecraft/block/entity/BlockEntityTicker;)Lnet/minecraft/block/entity/BlockEntityTicker;
 *   Lnet/minecraft/block/HopperBlock;updateEnabled(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V
 *   Lnet/minecraft/block/HopperBlock;createCodec(Ljava/util/function/Function;)Lcom/mojang/serialization/MapCodec;
 */
package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;

public class HopperBlock
extends BlockWithEntity {
    public static final MapCodec<HopperBlock> CODEC = HopperBlock.createCodec(HopperBlock::new);
    public static final EnumProperty<Direction> FACING = Properties.HOPPER_FACING;
    public static final BooleanProperty ENABLED = Properties.ENABLED;
    private final Function<BlockState, VoxelShape> shapeFunction;
    private final Map<Direction, VoxelShape> shapesByDirection;

    public MapCodec<HopperBlock> getCodec() {
        return CODEC;
    }

    public HopperBlock(AbstractBlock.Settings arg) {
        super(arg);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.DOWN)).with(ENABLED, true));
        VoxelShape lv = Block.createColumnShape(12.0, 11.0, 16.0);
        this.shapeFunction = this.createShapeFunction(lv);
        this.shapesByDirection = ImmutableMap.builderWithExpectedSize(5).putAll(VoxelShapes.createHorizontalFacingShapeMap(VoxelShapes.union(lv, Block.createCuboidZShape(4.0, 8.0, 10.0, 0.0, 4.0)))).put(Direction.DOWN, lv).build();
    }

    private Function<BlockState, VoxelShape> createShapeFunction(VoxelShape shape) {
        VoxelShape lv = VoxelShapes.union(Block.createColumnShape(16.0, 10.0, 16.0), Block.createColumnShape(8.0, 4.0, 10.0));
        VoxelShape lv2 = VoxelShapes.combineAndSimplify(lv, shape, BooleanBiFunction.ONLY_FIRST);
        Map<Direction, VoxelShape> map = VoxelShapes.createFacingShapeMap(Block.createCuboidZShape(4.0, 4.0, 8.0, 0.0, 8.0), new Vec3d(8.0, 6.0, 8.0).multiply(0.0625));
        return this.createShapeFunction(state -> VoxelShapes.union(lv2, VoxelShapes.combineAndSimplify((VoxelShape)map.get(state.get(FACING)), VoxelShapes.fullCube(), BooleanBiFunction.AND)), ENABLED);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.shapeFunction.apply(state);
    }

    @Override
    protected VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return this.shapesByDirection.get(state.get(FACING));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction lv = ctx.getSide().getOpposite();
        return (BlockState)((BlockState)this.getDefaultState().with(FACING, lv.getAxis() == Direction.Axis.Y ? Direction.DOWN : lv)).with(ENABLED, true);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HopperBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient() ? null : HopperBlock.validateTicker(type, BlockEntityType.HOPPER, HopperBlockEntity::serverTick);
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.isOf(state.getBlock())) {
            return;
        }
        this.updateEnabled(world, pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        BlockEntity blockEntity;
        if (!world.isClient() && (blockEntity = world.getBlockEntity(pos)) instanceof HopperBlockEntity) {
            HopperBlockEntity lv = (HopperBlockEntity)blockEntity;
            player.openHandledScreen(lv);
            player.incrementStat(Stats.INSPECT_HOPPER);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        this.updateEnabled(world, pos, state);
    }

    private void updateEnabled(World world, BlockPos pos, BlockState state) {
        boolean bl;
        boolean bl2 = bl = !world.isReceivingRedstonePower(pos);
        if (bl != state.get(ENABLED)) {
            world.setBlockState(pos, (BlockState)state.with(ENABLED, bl), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
        ItemScatterer.onStateReplaced(state, world, pos);
    }

    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos, Direction direction) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, ENABLED);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler) {
        BlockEntity lv = world.getBlockEntity(pos);
        if (lv instanceof HopperBlockEntity) {
            HopperBlockEntity.onEntityCollided(world, pos, state, entity, (HopperBlockEntity)lv);
        }
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }
}

