/*
 * External method calls:
 *   Lnet/minecraft/client/render/item/model/ItemModel;update(Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/item/ItemModelManager;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/util/HeldItemContext;I)V
 *
 * Internal private/static methods:
 *   Lnet/minecraft/client/item/ItemModelManager;clearAndUpdate(Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/world/World;Lnet/minecraft/util/HeldItemContext;I)V
 *   Lnet/minecraft/client/item/ItemModelManager;update(Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/world/World;Lnet/minecraft/util/HeldItemContext;I)V
 */
package net.minecraft.client.item;

import java.util.function.Function;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ItemAsset;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HeldItemContext;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@Environment(value=EnvType.CLIENT)
public class ItemModelManager {
    private final Function<Identifier, ItemModel> modelGetter = bakedModelManager::getItemModel;
    private final Function<Identifier, ItemAsset.Properties> propertiesGetter = bakedModelManager::getItemProperties;

    public ItemModelManager(BakedModelManager bakedModelManager) {
    }

    public void updateForLivingEntity(ItemRenderState renderState, ItemStack stack, ItemDisplayContext displayContext, LivingEntity entity) {
        this.clearAndUpdate(renderState, stack, displayContext, entity.getEntityWorld(), entity, entity.getId() + displayContext.ordinal());
    }

    public void updateForNonLivingEntity(ItemRenderState renderState, ItemStack stack, ItemDisplayContext displayContext, Entity entity) {
        this.clearAndUpdate(renderState, stack, displayContext, entity.getEntityWorld(), null, entity.getId());
    }

    public void clearAndUpdate(ItemRenderState renderState, ItemStack stack, ItemDisplayContext displayContext, @Nullable World world, @Nullable HeldItemContext arg5, int seed) {
        renderState.clear();
        if (!stack.isEmpty()) {
            renderState.displayContext = displayContext;
            this.update(renderState, stack, displayContext, world, arg5, seed);
        }
    }

    public void update(ItemRenderState renderState, ItemStack stack, ItemDisplayContext displayContext, @Nullable World world, @Nullable HeldItemContext arg5, int seed) {
        ClientWorld lv2;
        Identifier lv = stack.get(DataComponentTypes.ITEM_MODEL);
        if (lv == null) {
            return;
        }
        renderState.setOversizedInGui(this.propertiesGetter.apply(lv).oversizedInGui());
        this.modelGetter.apply(lv).update(renderState, stack, this, displayContext, world instanceof ClientWorld ? (lv2 = (ClientWorld)world) : null, arg5, seed);
    }

    public boolean hasHandAnimationOnSwap(ItemStack stack) {
        Identifier lv = stack.get(DataComponentTypes.ITEM_MODEL);
        if (lv == null) {
            return true;
        }
        return this.propertiesGetter.apply(lv).handAnimationOnSwap();
    }
}

