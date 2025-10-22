package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

@Environment(value=EnvType.CLIENT)
public class RavagerEntityRenderState
extends LivingEntityRenderState {
    public float stunTick;
    public float attackTick;
    public float roarTick;
}

