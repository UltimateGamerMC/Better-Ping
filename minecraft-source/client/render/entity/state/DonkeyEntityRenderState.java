package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingHorseEntityRenderState;

@Environment(value=EnvType.CLIENT)
public class DonkeyEntityRenderState
extends LivingHorseEntityRenderState {
    public boolean hasChest;
}

