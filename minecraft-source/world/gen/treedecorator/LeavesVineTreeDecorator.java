/*
 * External method calls:
 *   Lnet/minecraft/world/gen/treedecorator/TreeDecorator$Generator;replaceWithVine(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/state/property/BooleanProperty;)V
 *
 * Internal private/static methods:
 *   Lnet/minecraft/world/gen/treedecorator/LeavesVineTreeDecorator;placeVines(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/state/property/BooleanProperty;Lnet/minecraft/world/gen/treedecorator/TreeDecorator$Generator;)V
 */
package net.minecraft.world.gen.treedecorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.VineBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class LeavesVineTreeDecorator
extends TreeDecorator {
    public static final MapCodec<LeavesVineTreeDecorator> CODEC = ((MapCodec)Codec.floatRange(0.0f, 1.0f).fieldOf("probability")).xmap(LeavesVineTreeDecorator::new, treeDecorator -> Float.valueOf(treeDecorator.probability));
    private final float probability;

    @Override
    protected TreeDecoratorType<?> getType() {
        return TreeDecoratorType.LEAVE_VINE;
    }

    public LeavesVineTreeDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    public void generate(TreeDecorator.Generator generator) {
        Random lv = generator.getRandom();
        generator.getLeavesPositions().forEach(pos -> {
            BlockPos lv;
            if (lv.nextFloat() < this.probability && generator.isAir(lv = pos.west())) {
                LeavesVineTreeDecorator.placeVines(lv, VineBlock.EAST, generator);
            }
            if (lv.nextFloat() < this.probability && generator.isAir(lv = pos.east())) {
                LeavesVineTreeDecorator.placeVines(lv, VineBlock.WEST, generator);
            }
            if (lv.nextFloat() < this.probability && generator.isAir(lv = pos.north())) {
                LeavesVineTreeDecorator.placeVines(lv, VineBlock.SOUTH, generator);
            }
            if (lv.nextFloat() < this.probability && generator.isAir(lv = pos.south())) {
                LeavesVineTreeDecorator.placeVines(lv, VineBlock.NORTH, generator);
            }
        });
    }

    private static void placeVines(BlockPos pos, BooleanProperty faceProperty, TreeDecorator.Generator generator) {
        generator.replaceWithVine(pos, faceProperty);
        pos = pos.down();
        for (int i = 4; generator.isAir(pos) && i > 0; --i) {
            generator.replaceWithVine(pos, faceProperty);
            pos = pos.down();
        }
    }
}

