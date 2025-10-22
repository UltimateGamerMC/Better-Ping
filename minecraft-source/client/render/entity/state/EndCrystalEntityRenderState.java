package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

@Environment(value=EnvType.CLIENT)
public class EndCrystalEntityRenderState
extends EntityRenderState {
    public boolean baseVisible = true;
    @Nullable
    public Vec3d beamOffset;
}

