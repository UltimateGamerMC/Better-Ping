package net.minecraft.client.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public class TextureAllocationException
extends RuntimeException {
    public TextureAllocationException(String message) {
        super(message);
    }
}

