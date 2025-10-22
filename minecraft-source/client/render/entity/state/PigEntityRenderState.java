package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.passive.PigVariant;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@Environment(value=EnvType.CLIENT)
public class PigEntityRenderState
extends LivingEntityRenderState {
    public ItemStack saddleStack = ItemStack.EMPTY;
    @Nullable
    public PigVariant variant;
}

