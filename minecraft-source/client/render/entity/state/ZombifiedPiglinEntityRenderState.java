package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;

@Environment(value=EnvType.CLIENT)
public class ZombifiedPiglinEntityRenderState
extends BipedEntityRenderState {
    public boolean attacking;
}

