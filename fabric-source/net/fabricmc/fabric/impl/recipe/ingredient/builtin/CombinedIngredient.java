package net.fabricmc.fabric.impl.recipe.ingredient.builtin;

import java.util.List;
import java.util.function.Function;

import com.mojang.serialization.MapCodec;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.display.SlotDisplay;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;

/**
 * Base class for ALL and ANY ingredients.
 */
abstract class CombinedIngredient implements CustomIngredient {
	protected final List<Ingredient> ingredients;

	protected CombinedIngredient(List<Ingredient> ingredients) {
		if (ingredients.isEmpty()) {
			throw new IllegalArgumentException("ALL or ANY ingredient must have at least one sub-ingredient");
		}

		this.ingredients = ingredients;
	}

	@Override
	public boolean requiresTesting() {
		for (Ingredient ingredient : ingredients) {
			if (ingredient.requiresTesting()) {
				return true;
			}
		}

		return false;
	}

	List<Ingredient> getIngredients() {
		return ingredients;
	}

	@Override
	public SlotDisplay toDisplay() {
		return new SlotDisplay.CompositeSlotDisplay(
				ingredients.stream().map(Ingredient::toDisplay).toList()
		);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CombinedIngredient that)) return false;
		return ingredients.equals(that.ingredients);
	}

	@Override
	public int hashCode() {
		return ingredients.hashCode();
	}

	static class Serializer<I extends CombinedIngredient> implements CustomIngredientSerializer<I> {
		private final Identifier identifier;
		private final MapCodec<I> codec;
		private final PacketCodec<RegistryByteBuf, I> packetCodec;

		Serializer(Identifier identifier, Function<List<Ingredient>, I> factory, MapCodec<I> codec) {
			this.identifier = identifier;
			this.codec = codec;
			this.packetCodec = Ingredient.PACKET_CODEC.collect(PacketCodecs.toList())
					.xmap(factory, I::getIngredients);
		}

		@Override
		public Identifier getIdentifier() {
			return identifier;
		}

		@Override
		public MapCodec<I> getCodec() {
			return codec;
		}

		@Override
		public PacketCodec<RegistryByteBuf, I> getPacketCodec() {
			return this.packetCodec;
		}
	}
}
