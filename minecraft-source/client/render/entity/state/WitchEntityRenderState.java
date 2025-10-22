package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.ItemHolderEntityRenderState;

@Environment(value=EnvType.CLIENT)
public class WitchEntityRenderState
extends ItemHolderEntityRenderState {
    public int id;
    public boolean holdingItem;
    public boolean holdingPotion;
}

