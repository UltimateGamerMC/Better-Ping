package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.ItemStackEntityRenderState;

@Environment(value=EnvType.CLIENT)
public class ItemEntityRenderState
extends ItemStackEntityRenderState {
    public float uniqueOffset;
}

