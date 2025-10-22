package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.DisplayEntityRenderState;
import net.minecraft.entity.decoration.DisplayEntity;
import org.jetbrains.annotations.Nullable;

@Environment(value=EnvType.CLIENT)
public class TextDisplayEntityRenderState
extends DisplayEntityRenderState {
    @Nullable
    public DisplayEntity.TextDisplayEntity.Data data;
    @Nullable
    public DisplayEntity.TextDisplayEntity.TextLines textLines;

    @Override
    public boolean canRender() {
        return this.data != null;
    }
}

