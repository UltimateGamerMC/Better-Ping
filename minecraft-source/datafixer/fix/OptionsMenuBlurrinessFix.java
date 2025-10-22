/*
 * Internal private/static methods:
 *   Lnet/minecraft/datafixer/fix/OptionsMenuBlurrinessFix;fixTypeEverywhereTyped(Ljava/lang/String;Lcom/mojang/datafixers/types/Type;Ljava/util/function/Function;)Lcom/mojang/datafixers/TypeRewriteRule;
 *   Lnet/minecraft/datafixer/fix/OptionsMenuBlurrinessFix;update(Ljava/lang/String;)I
 */
package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.datafixer.TypeReferences;

public class OptionsMenuBlurrinessFix
extends DataFix {
    public OptionsMenuBlurrinessFix(Schema outputSchema) {
        super(outputSchema, false);
    }

    @Override
    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("OptionsMenuBlurrinessFix", this.getInputSchema().getType(TypeReferences.OPTIONS), optionsTyped -> optionsTyped.update(DSL.remainderFinder(), optionsDynamic -> optionsDynamic.update("menuBackgroundBlurriness", dynamic -> {
            int i = this.update(dynamic.asString("0.5"));
            return dynamic.createString(String.valueOf(i));
        })));
    }

    private int update(String value) {
        try {
            return Math.round(Float.parseFloat(value) * 10.0f);
        } catch (NumberFormatException numberFormatException) {
            return 5;
        }
    }
}

