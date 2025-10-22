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

public class Schema4420
extends IdentifierNormalizingSchema {
    public Schema4420(int i, Schema schema) {
        super(i, schema);
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);
        schema.register(map, "minecraft:area_effect_cloud", (String string) -> DSL.optionalFields("custom_particle", TypeReferences.PARTICLE.in(schema)));
        return map;
    }
}

