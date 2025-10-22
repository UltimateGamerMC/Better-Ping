/*
 * External method calls:
 *   Lnet/minecraft/block/entity/SculkSpreadManager;createWorldGen()Lnet/minecraft/block/entity/SculkSpreadManager;
 *   Lnet/minecraft/block/entity/SculkSpreadManager;spread(Lnet/minecraft/util/math/BlockPos;I)V
 *   Lnet/minecraft/block/entity/SculkSpreadManager;tick(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;Z)V
 *   Lnet/minecraft/world/gen/feature/SculkPatchFeatureConfig;extraRareGrowths()Lnet/minecraft/util/math/intprovider/IntProvider;
 *   Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SculkShriekerBlock;
import net.minecraft.block.SculkSpreadable;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SculkPatchFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class SculkPatchFeature
extends Feature<SculkPatchFeatureConfig> {
    public SculkPatchFeature(Codec<SculkPatchFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<SculkPatchFeatureConfig> context) {
        int l;
        int k;
        BlockPos lv2;
        StructureWorldAccess lv = context.getWorld();
        if (!this.canGenerate(lv, lv2 = context.getOrigin())) {
            return false;
        }
        SculkPatchFeatureConfig lv3 = context.getConfig();
        Random lv4 = context.getRandom();
        SculkSpreadManager lv5 = SculkSpreadManager.createWorldGen();
        int i = lv3.spreadRounds() + lv3.growthRounds();
        for (int j = 0; j < i; ++j) {
            for (k = 0; k < lv3.chargeCount(); ++k) {
                lv5.spread(lv2, lv3.amountPerCharge());
            }
            boolean bl = j < lv3.spreadRounds();
            for (l = 0; l < lv3.spreadAttempts(); ++l) {
                lv5.tick(lv, lv2, lv4, bl);
            }
            lv5.clearCursors();
        }
        BlockPos lv6 = lv2.down();
        if (lv4.nextFloat() <= lv3.catalystChance() && lv.getBlockState(lv6).isFullCube(lv, lv6)) {
            lv.setBlockState(lv2, Blocks.SCULK_CATALYST.getDefaultState(), Block.NOTIFY_ALL);
        }
        k = lv3.extraRareGrowths().get(lv4);
        for (l = 0; l < k; ++l) {
            BlockPos lv7 = lv2.add(lv4.nextInt(5) - 2, 0, lv4.nextInt(5) - 2);
            if (!lv.getBlockState(lv7).isAir() || !lv.getBlockState(lv7.down()).isSideSolidFullSquare(lv, lv7.down(), Direction.UP)) continue;
            lv.setBlockState(lv7, (BlockState)Blocks.SCULK_SHRIEKER.getDefaultState().with(SculkShriekerBlock.CAN_SUMMON, true), Block.NOTIFY_ALL);
        }
        return true;
    }

    private boolean canGenerate(WorldAccess world, BlockPos pos) {
        block5: {
            block4: {
                BlockState lv = world.getBlockState(pos);
                if (lv.getBlock() instanceof SculkSpreadable) {
                    return true;
                }
                if (lv.isAir()) break block4;
                if (!lv.isOf(Blocks.WATER) || !lv.getFluidState().isStill()) break block5;
            }
            return Direction.stream().map(pos::offset).anyMatch(pos2 -> world.getBlockState((BlockPos)pos2).isFullCube(world, (BlockPos)pos2));
        }
        return false;
    }
}

