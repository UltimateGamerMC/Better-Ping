package net.minecraft.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.village.VillagerData;
import org.jetbrains.annotations.Nullable;

@Environment(value=EnvType.CLIENT)
public interface VillagerDataRenderState {
    @Nullable
    public VillagerData getVillagerData();
}

