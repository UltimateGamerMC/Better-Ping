package net.fabricmc.fabric.mixin.recipe.ingredient;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.network.ClientConnection;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.impl.recipe.ingredient.SupportedIngredientsClientConnection;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin implements SupportedIngredientsClientConnection {
	@Unique
	private Set<Identifier> fabric_supportedCustomIngredients = Set.of();

	@Override
	public void fabric_setSupportedCustomIngredients(Set<Identifier> supportedCustomIngredients) {
		fabric_supportedCustomIngredients = supportedCustomIngredients;
	}

	@Override
	public Set<Identifier> fabric_getSupportedCustomIngredients() {
		return fabric_supportedCustomIngredients;
	}
}
