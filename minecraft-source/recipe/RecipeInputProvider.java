package net.minecraft.recipe;

import net.minecraft.recipe.RecipeFinder;

@FunctionalInterface
public interface RecipeInputProvider {
    public void provideRecipeInputs(RecipeFinder var1);
}

