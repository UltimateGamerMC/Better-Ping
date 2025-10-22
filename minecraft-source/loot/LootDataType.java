/*
 * External method calls:
 *   Lnet/minecraft/loot/LootDataType$Validator;run(Lnet/minecraft/loot/LootTableReporter;Lnet/minecraft/registry/RegistryKey;Ljava/lang/Object;)V
 *   Lnet/minecraft/loot/LootTableReporter;withContextType(Lnet/minecraft/util/context/ContextType;)Lnet/minecraft/loot/LootTableReporter;
 *   Lnet/minecraft/loot/LootTableReporter;makeChild(Lnet/minecraft/util/ErrorReporter$Context;Lnet/minecraft/registry/RegistryKey;)Lnet/minecraft/loot/LootTableReporter;
 *   Lnet/minecraft/loot/LootTable;validate(Lnet/minecraft/loot/LootTableReporter;)V
 *   Lnet/minecraft/loot/context/LootContextAware;validate(Lnet/minecraft/loot/LootTableReporter;)V
 *
 * Internal private/static methods:
 *   Lnet/minecraft/loot/LootDataType;simpleValidator()Lnet/minecraft/loot/LootDataType$Validator;
 *   Lnet/minecraft/loot/LootDataType;tableValidator()Lnet/minecraft/loot/LootDataType$Validator;
 */
package net.minecraft.loot;

import com.mojang.serialization.Codec;
import java.util.stream.Stream;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContextAware;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionTypes;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.ErrorReporter;

public record LootDataType<T>(RegistryKey<Registry<T>> registryKey, Codec<T> codec, Validator<T> validator) {
    public static final LootDataType<LootCondition> PREDICATES = new LootDataType<LootCondition>(RegistryKeys.PREDICATE, LootCondition.CODEC, LootDataType.simpleValidator());
    public static final LootDataType<LootFunction> ITEM_MODIFIERS = new LootDataType<LootFunction>(RegistryKeys.ITEM_MODIFIER, LootFunctionTypes.CODEC, LootDataType.simpleValidator());
    public static final LootDataType<LootTable> LOOT_TABLES = new LootDataType<LootTable>(RegistryKeys.LOOT_TABLE, LootTable.CODEC, LootDataType.tableValidator());

    public void validate(LootTableReporter reporter, RegistryKey<T> key, T value) {
        this.validator.run(reporter, key, value);
    }

    public static Stream<LootDataType<?>> stream() {
        return Stream.of(PREDICATES, ITEM_MODIFIERS, LOOT_TABLES);
    }

    private static <T extends LootContextAware> Validator<T> simpleValidator() {
        return (reporter, key, value) -> value.validate(reporter.makeChild(new ErrorReporter.LootTableContext(key), key));
    }

    private static Validator<LootTable> tableValidator() {
        return (reporter, key, value) -> value.validate(reporter.withContextType(value.getType()).makeChild(new ErrorReporter.LootTableContext(key), key));
    }

    @FunctionalInterface
    public static interface Validator<T> {
        public void run(LootTableReporter var1, RegistryKey<T> var2, T var3);
    }
}

