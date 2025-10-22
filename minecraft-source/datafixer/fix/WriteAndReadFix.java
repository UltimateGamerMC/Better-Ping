/*
 * Internal private/static methods:
 *   Lnet/minecraft/datafixer/fix/WriteAndReadFix;writeAndRead(Ljava/lang/String;Lcom/mojang/datafixers/types/Type;Lcom/mojang/datafixers/types/Type;)Lcom/mojang/datafixers/TypeRewriteRule;
 */
package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;

public class WriteAndReadFix
extends DataFix {
    private final String name;
    private final DSL.TypeReference type;

    public WriteAndReadFix(Schema outputSchema, String name, DSL.TypeReference type) {
        super(outputSchema, true);
        this.name = name;
        this.type = type;
    }

    @Override
    protected TypeRewriteRule makeRule() {
        return this.writeAndRead(this.name, this.getInputSchema().getType(this.type), this.getOutputSchema().getType(this.type));
    }
}

