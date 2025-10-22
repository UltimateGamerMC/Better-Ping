/*
 * External method calls:
 *   Lnet/minecraft/recipe/book/RecipeBookOptions;copyFrom(Lnet/minecraft/recipe/book/RecipeBookOptions;)V
 */
package net.minecraft.recipe.book;

import net.minecraft.recipe.book.RecipeBookOptions;
import net.minecraft.recipe.book.RecipeBookType;

public class RecipeBook {
    protected final RecipeBookOptions options = new RecipeBookOptions();

    public boolean isGuiOpen(RecipeBookType category) {
        return this.options.isGuiOpen(category);
    }

    public void setGuiOpen(RecipeBookType category, boolean open) {
        this.options.setGuiOpen(category, open);
    }

    public boolean isFilteringCraftable(RecipeBookType category) {
        return this.options.isFilteringCraftable(category);
    }

    public void setFilteringCraftable(RecipeBookType category, boolean filteringCraftable) {
        this.options.setFilteringCraftable(category, filteringCraftable);
    }

    public void setOptions(RecipeBookOptions options) {
        this.options.copyFrom(options);
    }

    public RecipeBookOptions getOptions() {
        return this.options;
    }

    public void setCategoryOptions(RecipeBookType category, boolean guiOpen, boolean filteringCraftable) {
        this.options.setGuiOpen(category, guiOpen);
        this.options.setFilteringCraftable(category, filteringCraftable);
    }
}

