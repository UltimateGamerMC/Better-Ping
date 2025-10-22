package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.item.ItemStack;

@Environment(value=EnvType.CLIENT)
public class LivingHorseEntityRenderState
extends LivingEntityRenderState {
    public ItemStack saddleStack = ItemStack.EMPTY;
    public boolean hasPassengers;
    public boolean waggingTail;
    public float eatingGrassAnimationProgress;
    public float angryAnimationProgress;
    public float eatingAnimationProgress;
}

