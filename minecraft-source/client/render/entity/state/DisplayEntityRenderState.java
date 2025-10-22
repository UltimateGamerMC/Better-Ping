package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.decoration.DisplayEntity;
import org.jetbrains.annotations.Nullable;

@Environment(value=EnvType.CLIENT)
public abstract class DisplayEntityRenderState
extends EntityRenderState {
    @Nullable
    public DisplayEntity.RenderState displayRenderState;
    public float lerpProgress;
    public float yaw;
    public float pitch;
    public float cameraYaw;
    public float cameraPitch;

    public abstract boolean canRender();
}

