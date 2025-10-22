/*
 * External method calls:
 *   Lnet/minecraft/datafixer/schema/IdentifierNormalizingSchema;registerEntities(Lcom/mojang/datafixers/schemas/Schema;)Ljava/util/Map;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/datafixer/schema/Schema3685;registerFields(Lcom/mojang/datafixers/schemas/Schema;)Lcom/mojang/datafixers/types/templates/TypeTemplate;
 */
package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class Schema3685
extends IdentifierNormalizingSchema {
    public Schema3685(int i, Schema schema) {
        super(i, schema);
    }

    protected static TypeTemplate registerFields(Schema schema) {
        return DSL.optionalFields("inBlockState", TypeReferences.BLOCK_STATE.in(schema), "item", TypeReferences.ITEM_STACK.in(schema));
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);
        schema.register(map, "minecraft:trident", () -> Schema3685.registerFields(schema));
        schema.register(map, "minecraft:spectral_arrow", () -> Schema3685.registerFields(schema));
        schema.register(map, "minecraft:arrow", () -> Schema3685.registerFields(schema));
        return map;
    }
}

