package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;

@Environment(value=EnvType.CLIENT)
public class ArrowEntityRenderState
extends ProjectileEntityRenderState {
    public boolean tipped;
}

