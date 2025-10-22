package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

@Environment(value=EnvType.CLIENT)
public class FelineEntityRenderState
extends LivingEntityRenderState {
    public boolean inSneakingPose;
    public boolean sprinting;
    public boolean inSittingPose;
    public float sleepAnimationProgress;
    public float tailCurlAnimationProgress;
    public float headDownAnimationProgress;
}

