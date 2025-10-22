/*
 * External method calls:
 *   Lnet/minecraft/datafixer/schema/IdentifierNormalizingSchema;registerEntities(Lcom/mojang/datafixers/schemas/Schema;)Ljava/util/Map;
 */
package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class Schema4306
extends IdentifierNormalizingSchema {
    public Schema4306(int i, Schema schema) {
        super(i, schema);
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);
        map.remove("minecraft:potion");
        schema.register(map, "minecraft:splash_potion", () -> DSL.optionalFields("Item", TypeReferences.ITEM_STACK.in(schema)));
        schema.register(map, "minecraft:lingering_potion", () -> DSL.optionalFields("Item", TypeReferences.ITEM_STACK.in(schema)));
        return map;
    }
}

