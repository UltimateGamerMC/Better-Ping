package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;

@Environment(value=EnvType.CLIENT)
public class SkeletonEntityRenderState
extends BipedEntityRenderState {
    public boolean attacking;
    public boolean shaking;
    public boolean holdingBow;
}

