/*
 * External method calls:
 *   Lnet/minecraft/client/gui/DrawContext;drawTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIFFIIII)V
 *   Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIIIIIII)V
 */
package net.minecraft.client.gui.screen.ingame;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenPos;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;
import net.minecraft.client.gui.screen.recipebook.AbstractFurnaceRecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(value=EnvType.CLIENT)
public abstract class AbstractFurnaceScreen<T extends AbstractFurnaceScreenHandler>
extends RecipeBookScreen<T> {
    private final Identifier background;
    private final Identifier litProgressTexture;
    private final Identifier burnProgressTexture;

    public AbstractFurnaceScreen(T handler, PlayerInventory playerInventory, Text title, Text toggleCraftableButtonText, Identifier background, Identifier litProgressTexture, Identifier burnProgressTexture, List<RecipeBookWidget.Tab> recipeBookTabs) {
        super(handler, new AbstractFurnaceRecipeBookWidget((AbstractFurnaceScreenHandler)handler, toggleCraftableButtonText, recipeBookTabs), playerInventory, title);
        this.background = background;
        this.litProgressTexture = litProgressTexture;
        this.burnProgressTexture = burnProgressTexture;
    }

    @Override
    public void init() {
        super.init();
        this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
    }

    @Override
    protected ScreenPos getRecipeBookButtonPos() {
        return new ScreenPos(this.x + 20, this.height / 2 - 49);
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        int n;
        int m;
        int k = this.x;
        int l = this.y;
        context.drawTexture(RenderPipelines.GUI_TEXTURED, this.background, k, l, 0.0f, 0.0f, this.backgroundWidth, this.backgroundHeight, 256, 256);
        if (((AbstractFurnaceScreenHandler)this.handler).isBurning()) {
            m = 14;
            n = MathHelper.ceil(((AbstractFurnaceScreenHandler)this.handler).getFuelProgress() * 13.0f) + 1;
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, this.litProgressTexture, 14, 14, 0, 14 - n, k + 56, l + 36 + 14 - n, 14, n);
        }
        m = 24;
        n = MathHelper.ceil(((AbstractFurnaceScreenHandler)this.handler).getCookProgress() * 24.0f);
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, this.burnProgressTexture, 24, 16, 0, 0, k + 79, l + 34, n, 16);
    }
}

