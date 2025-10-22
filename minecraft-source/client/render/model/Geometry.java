package net.minecraft.client.render.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.BakedGeometry;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelTextures;
import net.minecraft.client.render.model.SimpleModel;

@FunctionalInterface
@Environment(value=EnvType.CLIENT)
public interface Geometry {
    public static final Geometry EMPTY = (textures, baker, settings, model) -> BakedGeometry.EMPTY;

    public BakedGeometry bake(ModelTextures var1, Baker var2, ModelBakeSettings var3, SimpleModel var4);
}

