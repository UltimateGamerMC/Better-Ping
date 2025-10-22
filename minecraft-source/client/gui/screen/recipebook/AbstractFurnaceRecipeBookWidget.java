/*
 * External method calls:
 *   Lnet/minecraft/recipe/display/RecipeDisplay;result()Lnet/minecraft/recipe/display/SlotDisplay;
 *   Lnet/minecraft/client/gui/screen/recipebook/GhostRecipe;addResults(Lnet/minecraft/screen/slot/Slot;Lnet/minecraft/util/context/ContextParameterMap;Lnet/minecraft/recipe/display/SlotDisplay;)V
 *   Lnet/minecraft/recipe/display/FurnaceRecipeDisplay;ingredient()Lnet/minecraft/recipe/display/SlotDisplay;
 *   Lnet/minecraft/client/gui/screen/recipebook/GhostRecipe;addInputs(Lnet/minecraft/screen/slot/Slot;Lnet/minecraft/util/context/ContextParameterMap;Lnet/minecraft/recipe/display/SlotDisplay;)V
 *   Lnet/minecraft/recipe/display/FurnaceRecipeDisplay;fuel()Lnet/minecraft/recipe/display/SlotDisplay;
 *   Lnet/minecraft/client/gui/screen/recipebook/RecipeResultCollection;populateRecipes(Lnet/minecraft/recipe/RecipeFinder;Ljava/util/function/Predicate;)V
 *   Lnet/minecraft/util/Identifier;ofVanilla(Ljava/lang/String;)Lnet/minecraft/util/Identifier;
 */
package net.minecraft.client.gui.screen.recipebook;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.recipebook.GhostRecipe;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.display.FurnaceRecipeDisplay;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.context.ContextParameterMap;

@Environment(value=EnvType.CLIENT)
public class AbstractFurnaceRecipeBookWidget
extends RecipeBookWidget<AbstractFurnaceScreenHandler> {
    private static final ButtonTextures TEXTURES = new ButtonTextures(Identifier.ofVanilla("recipe_book/furnace_filter_enabled"), Identifier.ofVanilla("recipe_book/furnace_filter_disabled"), Identifier.ofVanilla("recipe_book/furnace_filter_enabled_highlighted"), Identifier.ofVanilla("recipe_book/furnace_filter_disabled_highlighted"));
    private final Text toggleCraftableButtonText;

    public AbstractFurnaceRecipeBookWidget(AbstractFurnaceScreenHandler screenHandler, Text toggleCraftableButtonText, List<RecipeBookWidget.Tab> tabs) {
        super(screenHandler, tabs);
        this.toggleCraftableButtonText = toggleCraftableButtonText;
    }

    @Override
    protected void setBookButtonTexture() {
        this.toggleCraftableButton.setTextures(TEXTURES);
    }

    @Override
    protected boolean isValid(Slot slot) {
        return switch (slot.id) {
            case 0, 1, 2 -> true;
            default -> false;
        };
    }

    @Override
    protected void showGhostRecipe(GhostRecipe ghostRecipe, RecipeDisplay display, ContextParameterMap context) {
        ghostRecipe.addResults(((AbstractFurnaceScreenHandler)this.craftingScreenHandler).getOutputSlot(), context, display.result());
        if (display instanceof FurnaceRecipeDisplay) {
            FurnaceRecipeDisplay lv = (FurnaceRecipeDisplay)display;
            ghostRecipe.addInputs((Slot)((AbstractFurnaceScreenHandler)this.craftingScreenHandler).slots.get(0), context, lv.ingredient());
            Slot lv2 = (Slot)((AbstractFurnaceScreenHandler)this.craftingScreenHandler).slots.get(1);
            if (lv2.getStack().isEmpty()) {
                ghostRecipe.addInputs(lv2, context, lv.fuel());
            }
        }
    }

    @Override
    protected Text getToggleCraftableButtonText() {
        return this.toggleCraftableButtonText;
    }

    @Override
    protected void populateRecipes(RecipeResultCollection recipeResultCollection, RecipeFinder recipeFinder) {
        recipeResultCollection.populateRecipes(recipeFinder, (RecipeDisplay display) -> display instanceof FurnaceRecipeDisplay);
    }
}

