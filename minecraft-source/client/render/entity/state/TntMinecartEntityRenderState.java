package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.MinecartEntityRenderState;

@Environment(value=EnvType.CLIENT)
public class TntMinecartEntityRenderState
extends MinecartEntityRenderState {
    public float fuseTicks = -1.0f;
}

