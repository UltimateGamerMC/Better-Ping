package net.fabricmc.fabric.mixin.loot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.function.LootFunction;

import net.fabricmc.fabric.api.loot.v3.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v3.FabricLootTableBuilder;

/**
 * The implementation of the injected interface {@link FabricLootTableBuilder}.
 * Simply implements the new methods by adding the relevant objects inside the lists.
 */
@Mixin(LootTable.Builder.class)
abstract class LootTableBuilderMixin implements FabricLootTableBuilder {
	@Shadow
	@Final
	@Mutable
	private ImmutableList.Builder<LootPool> pools;

	@Shadow
	@Final
	private ImmutableList.Builder<LootFunction> functions;

	@Unique
	private LootTable.Builder self() {
		// noinspection ConstantConditions
		return (LootTable.Builder) (Object) this;
	}

	@Override
	public LootTable.Builder pool(LootPool pool) {
		this.pools.add(pool);
		return self();
	}

	@Override
	public LootTable.Builder apply(LootFunction function) {
		this.functions.add(function);
		return self();
	}

	@Override
	public LootTable.Builder pools(Collection<? extends LootPool> pools) {
		this.pools.addAll(pools);
		return self();
	}

	@Override
	public LootTable.Builder apply(Collection<? extends LootFunction> functions) {
		this.functions.addAll(functions);
		return self();
	}

	@Override
	public LootTable.Builder modifyPools(Consumer<? super LootPool.Builder> modifier) {
		var list = new ArrayList<>(pools.build());
		ListIterator<LootPool> iterator = list.listIterator();

		while (iterator.hasNext()) {
			LootPool.Builder poolBuilder = FabricLootPoolBuilder.copyOf(iterator.next());
			modifier.accept(poolBuilder);
			iterator.set(poolBuilder.build());
		}

		this.pools = ImmutableList.builder();
		this.pools.addAll(list);

		return self();
	}
}
