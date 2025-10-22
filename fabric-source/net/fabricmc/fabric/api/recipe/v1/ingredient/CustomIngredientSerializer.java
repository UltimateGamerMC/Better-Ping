package net.fabricmc.fabric.api.recipe.v1.ingredient;

import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.Nullable;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.impl.recipe.ingredient.CustomIngredientImpl;

/**
 * Serializer for a {@link CustomIngredient}.
 *
 * <p>All instances must be registered using {@link #register} for deserialization to work.
 *
 * @param <T> the type of the custom ingredient
 */
public interface CustomIngredientSerializer<T extends CustomIngredient> {
	/**
	 * Registers a custom ingredient serializer, using the {@linkplain CustomIngredientSerializer#getIdentifier() serializer's identifier}.
	 *
	 * @throws IllegalArgumentException if the serializer is already registered
	 */
	static void register(CustomIngredientSerializer<?> serializer) {
		CustomIngredientImpl.registerSerializer(serializer);
	}

	/**
	 * {@return the custom ingredient serializer registered with the given identifier, or {@code null} if there is no such serializer}.
	 */
	@Nullable
	static CustomIngredientSerializer<?> get(Identifier identifier) {
		return CustomIngredientImpl.getSerializer(identifier);
	}

	/**
	 * {@return the identifier of this serializer}.
	 */
	Identifier getIdentifier();

	/**
	 * {@return the codec}.
	 *
	 * <p>Codecs are used to read the ingredient from the recipe JSON files.
	 *
	 * @see Ingredient#CODEC
	 */
	MapCodec<T> getCodec();

	/**
	 * {@return the packet codec for serializing this ingredient}.
	 *
	 * @see Ingredient#PACKET_CODEC
	 */
	PacketCodec<RegistryByteBuf, T> getPacketCodec();
}
