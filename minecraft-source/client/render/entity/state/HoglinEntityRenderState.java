package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

@Environment(value=EnvType.CLIENT)
public class HoglinEntityRenderState
extends LivingEntityRenderState {
    public int movementCooldownTicks;
    public boolean canConvert;
}

