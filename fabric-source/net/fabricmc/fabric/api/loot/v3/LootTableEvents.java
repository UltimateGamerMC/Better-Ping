package net.fabricmc.fabric.api.loot.v3;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.ResourceManager;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Events for manipulating loot tables.
 */
public final class LootTableEvents {
	private LootTableEvents() {
	}

	/**
	 * This event can be used to replace loot tables.
	 * If a loot table is replaced, the iteration will stop for that loot table.
	 */
	public static final Event<Replace> REPLACE = EventFactory.createArrayBacked(Replace.class, listeners -> (key, original, source, registries) -> {
		for (Replace listener : listeners) {
			@Nullable LootTable replaced = listener.replaceLootTable(key, original, source, registries);

			if (replaced != null) {
				return replaced;
			}
		}

		return null;
	});

	/**
	 * This event can be used to modify loot tables.
	 * The main use case is to add items to vanilla or mod loot tables (e.g. modded seeds to grass).
	 *
	 * <p>You can also modify loot tables that are created by {@link #REPLACE}.
	 * They have the loot table source {@link LootTableSource#REPLACED}.
	 *
	 * <h2>Example: adding diamonds to the cobblestone loot table</h2>
	 * We'll add a new diamond {@linkplain net.minecraft.loot.LootPool loot pool} to the cobblestone loot table
	 * that will be dropped alongside the original cobblestone loot pool.
	 *
	 * <p>If you want only one of the items to drop, you can use
	 * {@link FabricLootTableBuilder#modifyPools(java.util.function.Consumer)} to add the new item to
	 * the original loot pool instead.
	 * {@snippet :
	 * LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
	 *     // If the loot table is for the cobblestone block and it is not overridden by a user:
	 *     if (Blocks.COBBLESTONE.getLootTableKey() == key && source.isBuiltin()) {
	 *         // Create a new loot pool that will hold the diamonds.
	 *         LootPool.Builder pool = LootPool.builder()
	 *             // Add diamonds...
	 *             .with(ItemEntry.builder(Items.DIAMOND))
	 *             // ...only if the block would survive a potential explosion.
	 *             .conditionally(SurvivesExplosionLootCondition.builder());
	 *
	 *         // Add the loot pool to the loot table
	 *         tableBuilder.pool(pool);
	 *     }
	 * });
	 * }
	 */
	public static final Event<Modify> MODIFY = EventFactory.createArrayBacked(Modify.class, listeners -> (key, tableBuilder, source, registries) -> {
		for (Modify listener : listeners) {
			listener.modifyLootTable(key, tableBuilder, source, registries);
		}
	});

	/**
	 * This event can be used for post-processing after all loot tables have been loaded and modified by Fabric.
	 */
	public static final Event<Loaded> ALL_LOADED = EventFactory.createArrayBacked(Loaded.class, listeners -> (resourceManager, lootManager) -> {
		for (Loaded listener : listeners) {
			listener.onLootTablesLoaded(resourceManager, lootManager);
		}
	});

	/**
	 * This event can be used for cases where the {@link #MODIFY} and {@link #REPLACE} events are inconvenient, such as when you are modifying the result of many loot tables that are unknown,
	 * and don't wish to add a custom loot function to every table.
	 * <br/>Note: if the table was requested to separate drops into stacks of a given size, the resulting drops from this event will be separated.
	 */
	public static final Event<ModifyDrops> MODIFY_DROPS = EventFactory.createArrayBacked(ModifyDrops.class, listeners -> (entry, context, drops) -> {
		for (ModifyDrops listener : listeners) {
			listener.modifyLootTableDrops(entry, context, drops);
		}
	});

	@FunctionalInterface
	public interface Replace {
		/**
		 * Replaces loot tables.
		 *
		 * @param key              the loot table key
		 * @param original        the original loot table
		 * @param source          the source of the original loot table
		 * @param registries      the registry wrapper lookup
		 * @return the new loot table, or null if it wasn't replaced
		 */
		@Nullable
		LootTable replaceLootTable(RegistryKey<LootTable> key, LootTable original, LootTableSource source, RegistryWrapper.WrapperLookup registries);
	}

	@FunctionalInterface
	public interface Modify {
		/**
		 * Called when a loot table is loading to modify loot tables.
		 *
		 * @param key              the loot table key
		 * @param tableBuilder    a builder of the loot table being loaded
		 * @param source          the source of the loot table
		 * @param registries      the registry wrapper lookup
		 */
		void modifyLootTable(RegistryKey<LootTable> key, LootTable.Builder tableBuilder, LootTableSource source, RegistryWrapper.WrapperLookup registries);
	}

	@FunctionalInterface
	public interface Loaded {
		/**
		 * Called when all loot tables have been loaded and {@link LootTableEvents#REPLACE} and {@link LootTableEvents#MODIFY} have been invoked.
		 *
		 * @param resourceManager the server resource manager
		 * @param lootRegistry     the loot registry
		 */
		void onLootTablesLoaded(ResourceManager resourceManager, Registry<LootTable> lootRegistry);
	}

	@FunctionalInterface
	public interface ModifyDrops {
		/**
		 * Called after a loot table is finished generating drops to modify drops.
		 * @param entry the loot table's registry entry. This will be a {@link RegistryEntry.Reference} if the lootTable is registered, or a {@link RegistryEntry.Direct} if the table is inline
		 * @param context the loot context for the current drops
		 * @param drops the list of drops from the loot table to modify
		 */
		void modifyLootTableDrops(RegistryEntry<LootTable> entry, LootContext context, List<ItemStack> drops);
	}
}
