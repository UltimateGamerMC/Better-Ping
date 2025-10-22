package net.minecraft.client.render.item.property.bool;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.property.PropertyTester;

@Environment(value=EnvType.CLIENT)
public interface BooleanProperty
extends PropertyTester {
    public MapCodec<? extends BooleanProperty> getCodec();
}

