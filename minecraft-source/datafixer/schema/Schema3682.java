/*
 * External method calls:
 *   Lnet/minecraft/datafixer/schema/IdentifierNormalizingSchema;registerBlockEntities(Lcom/mojang/datafixers/schemas/Schema;)Ljava/util/Map;
 *   Lnet/minecraft/datafixer/schema/Schema1458;itemsAndCustomName(Lcom/mojang/datafixers/schemas/Schema;)Lcom/mojang/datafixers/types/templates/TypeTemplate;
 */
package net.minecraft.datafixer.schema;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;
import net.minecraft.datafixer.schema.Schema1458;

public class Schema3682
extends IdentifierNormalizingSchema {
    public Schema3682(int i, Schema schema) {
        super(i, schema);
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerBlockEntities(schema);
        schema.register(map, "minecraft:crafter", () -> Schema1458.itemsAndCustomName(schema));
        return map;
    }
}

