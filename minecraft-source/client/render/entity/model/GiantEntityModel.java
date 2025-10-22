package net.minecraft.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AbstractZombieModel;
import net.minecraft.client.render.entity.state.ZombieEntityRenderState;

@Environment(value=EnvType.CLIENT)
public class GiantEntityModel
extends AbstractZombieModel<ZombieEntityRenderState> {
    public GiantEntityModel(ModelPart arg) {
        super(arg);
    }
}

