package net.fabricmc.fabric.api.datagen.v1.provider;

import java.util.function.BiConsumer;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.ApiStatus;

import net.minecraft.data.DataProvider;
import net.minecraft.data.loottable.LootTableGenerator;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;

import net.fabricmc.fabric.api.datagen.v1.loot.FabricBlockLootTableGenerator;
import net.fabricmc.fabric.api.datagen.v1.loot.FabricEntityLootTableGenerator;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;

/**
 * A base interface for Loot table providers. You should not implement this class directly.
 *
 * <p>{@link FabricBlockLootTableProvider} provides additional features specific to block drop loot tables.
 *
 * <p>Use {@link SimpleFabricLootTableProvider} for a simple abstract class that you can implement to handle standard loot table functions.
 */
@ApiStatus.NonExtendable
public interface FabricLootTableProvider extends LootTableGenerator, DataProvider {
	/**
	 * Return a new exporter that applies the specified conditions to any loot table it receives.
	 *
	 * <p>For block and entity loot tables, use {@link FabricBlockLootTableGenerator#withConditions} or
	 * {@link FabricEntityLootTableGenerator#withConditions} instead, respectively.
	 */
	default BiConsumer<RegistryKey<LootTable>, LootTable.Builder> withConditions(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> exporter, ResourceCondition... conditions) {
		Preconditions.checkArgument(conditions.length > 0, "Must add at least one condition.");
		return (id, table) -> {
			FabricDataGenHelper.addConditions(table, conditions);
			exporter.accept(id, table);
		};
	}
}
