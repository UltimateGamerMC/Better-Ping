package net.fabricmc.fabric.mixin.datagen.recipe;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.SmithingTrimRecipeJsonBuilder;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.RegistryKey;

@Mixin(SmithingTrimRecipeJsonBuilder.class)
abstract class SmithingTrimRecipeJsonBuilderMixin {
	@ModifyVariable(method = "offerTo(Lnet/minecraft/data/recipe/RecipeExporter;Lnet/minecraft/registry/RegistryKey;)V", at = @At("HEAD"), argsOnly = true)
	private RegistryKey<Recipe<?>> modifyRecipeKey(RegistryKey<Recipe<?>> recipeKey, RecipeExporter exporter) {
		return RegistryKey.of(recipeKey.getRegistryRef(), exporter.getRecipeIdentifier(recipeKey.getValue()));
	}
}
