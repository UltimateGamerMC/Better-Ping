/*
 * External method calls:
 *   Lnet/minecraft/datafixer/schema/IdentifierNormalizingSchema;normalize(Ljava/lang/String;)Ljava/lang/String;
 *   Lnet/minecraft/datafixer/fix/BlockStateFlattening;lookupBlock(Ljava/lang/String;)Ljava/lang/String;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/datafixer/fix/BlockNameFlatteningFix;fixTypeEverywhere(Ljava/lang/String;Lcom/mojang/datafixers/types/Type;Lcom/mojang/datafixers/types/Type;Ljava/util/function/Function;)Lcom/mojang/datafixers/TypeRewriteRule;
 */
package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.fix.BlockStateFlattening;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class BlockNameFlatteningFix
extends DataFix {
    public BlockNameFlatteningFix(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.BLOCK_NAME);
        Type<?> type2 = this.getOutputSchema().getType(TypeReferences.BLOCK_NAME);
        Type<Pair<String, Either<Integer, String>>> type3 = DSL.named(TypeReferences.BLOCK_NAME.typeName(), DSL.or(DSL.intType(), IdentifierNormalizingSchema.getIdentifierType()));
        Type<Pair<String, String>> type4 = DSL.named(TypeReferences.BLOCK_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType());
        if (!Objects.equals(type, type3) || !Objects.equals(type2, type4)) {
            throw new IllegalStateException("Expected and actual types don't match.");
        }
        return this.fixTypeEverywhere("BlockNameFlatteningFix", type3, type4, dynamicOps -> pair -> pair.mapSecond(either -> either.map(BlockStateFlattening::lookupStateBlock, string -> BlockStateFlattening.lookupBlock(IdentifierNormalizingSchema.normalize(string)))));
    }
}

