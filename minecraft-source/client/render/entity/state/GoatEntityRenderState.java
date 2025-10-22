package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

@Environment(value=EnvType.CLIENT)
public class GoatEntityRenderState
extends LivingEntityRenderState {
    public boolean hasLeftHorn = true;
    public boolean hasRightHorn = true;
    public float headPitch;
}

