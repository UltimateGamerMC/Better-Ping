package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.passive.MooshroomEntity;

@Environment(value=EnvType.CLIENT)
public class MooshroomEntityRenderState
extends LivingEntityRenderState {
    public MooshroomEntity.Variant type = MooshroomEntity.Variant.RED;
}

