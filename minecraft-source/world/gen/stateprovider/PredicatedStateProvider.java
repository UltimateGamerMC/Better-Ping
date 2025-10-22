/*
 * External method calls:
 *   Lnet/minecraft/world/gen/stateprovider/BlockStateProvider;of(Lnet/minecraft/block/Block;)Lnet/minecraft/world/gen/stateprovider/SimpleBlockStateProvider;
 *   Lnet/minecraft/world/gen/stateprovider/PredicatedStateProvider$Rule;ifTrue()Lnet/minecraft/world/gen/blockpredicate/BlockPredicate;
 *   Lnet/minecraft/world/gen/blockpredicate/BlockPredicate;test(Ljava/lang/Object;Ljava/lang/Object;)Z
 *   Lnet/minecraft/world/gen/stateprovider/PredicatedStateProvider$Rule;then()Lnet/minecraft/world/gen/stateprovider/BlockStateProvider;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/world/gen/stateprovider/PredicatedStateProvider;of(Lnet/minecraft/world/gen/stateprovider/BlockStateProvider;)Lnet/minecraft/world/gen/stateprovider/PredicatedStateProvider;
 */
package net.minecraft.world.gen.stateprovider;

import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record PredicatedStateProvider(BlockStateProvider fallback, List<Rule> rules) {
    public static final Codec<PredicatedStateProvider> CODEC = RecordCodecBuilder.create(instance -> instance.group(((MapCodec)BlockStateProvider.TYPE_CODEC.fieldOf("fallback")).forGetter(PredicatedStateProvider::fallback), ((MapCodec)Rule.CODEC.listOf().fieldOf("rules")).forGetter(PredicatedStateProvider::rules)).apply((Applicative<PredicatedStateProvider, ?>)instance, PredicatedStateProvider::new));

    public static PredicatedStateProvider of(BlockStateProvider stateProvider) {
        return new PredicatedStateProvider(stateProvider, List.of());
    }

    public static PredicatedStateProvider of(Block block) {
        return PredicatedStateProvider.of(BlockStateProvider.of(block));
    }

    public BlockState getBlockState(StructureWorldAccess world, Random random, BlockPos pos) {
        for (Rule lv : this.rules) {
            if (!lv.ifTrue().test(world, pos)) continue;
            return lv.then().get(random, pos);
        }
        return this.fallback.get(random, pos);
    }

    public record Rule(BlockPredicate ifTrue, BlockStateProvider then) {
        public static final Codec<Rule> CODEC = RecordCodecBuilder.create(instance -> instance.group(((MapCodec)BlockPredicate.BASE_CODEC.fieldOf("if_true")).forGetter(Rule::ifTrue), ((MapCodec)BlockStateProvider.TYPE_CODEC.fieldOf("then")).forGetter(Rule::then)).apply((Applicative<Rule, ?>)instance, Rule::new));
    }
}

