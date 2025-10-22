package net.fabricmc.fabric.api.registry;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.mojang.datafixers.util.Pair;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemUsageContext;

import net.fabricmc.fabric.mixin.content.registry.HoeItemAccessor;

/**
 * A registry for hoe tilling interactions. A vanilla example is turning dirt to dirt paths.
 */
public final class TillableBlockRegistry {
	private TillableBlockRegistry() {
	}

	/**
	 * Registers a tilling interaction.
	 *
	 * <p>Tilling interactions are a two-step process. First, a usage predicate is run that decides whether to till
	 * a block. If the predicate returns {@code true}, an action is executed. Default instances of these can be created
	 * with these {@link HoeItem} methods:
	 * <ul>
	 * <li>usage predicate for farmland-like behavior: {@link HoeItem#canTillFarmland(ItemUsageContext)}</li>
	 * <li>simple action: {@link HoeItem#createTillAction(BlockState)} (BlockState)}</li>
	 * <li>simple action that also drops an item: {@link HoeItem#createTillAndDropAction(BlockState, ItemConvertible)}</li>
	 * </ul>
	 *
	 * @param input          the input block that can be tilled
	 * @param usagePredicate a predicate that filters if the block can be tilled
	 * @param tillingAction  an action that is executed if the predicate returns {@code true}
	 */
	public static void register(Block input, Predicate<ItemUsageContext> usagePredicate, Consumer<ItemUsageContext> tillingAction) {
		Objects.requireNonNull(input, "input block cannot be null");
		HoeItemAccessor.getTillingActions().put(input, Pair.of(usagePredicate, tillingAction));
	}

	/**
	 * Registers a simple tilling interaction.
	 *
	 * @param input          the input block that can be tilled
	 * @param usagePredicate a predicate that filters if the block can be tilled
	 * @param tilled         the tilled result block state
	 */
	public static void register(Block input, Predicate<ItemUsageContext> usagePredicate, BlockState tilled) {
		Objects.requireNonNull(tilled, "tilled block state cannot be null");
		register(input, usagePredicate, HoeItem.createTillAction(tilled));
	}

	/**
	 * Registers a simple tilling interaction that also drops an item.
	 *
	 * @param input          the input block that can be tilled
	 * @param usagePredicate a predicate that filters if the block can be tilled
	 * @param tilled         the tilled result block state
	 * @param droppedItem    an item that is dropped when the input block is tilled
	 */
	public static void register(Block input, Predicate<ItemUsageContext> usagePredicate, BlockState tilled, ItemConvertible droppedItem) {
		Objects.requireNonNull(tilled, "tilled block state cannot be null");
		Objects.requireNonNull(droppedItem, "dropped item cannot be null");
		register(input, usagePredicate, HoeItem.createTillAndDropAction(tilled, droppedItem));
	}
}
