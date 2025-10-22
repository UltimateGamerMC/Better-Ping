package net.minecraft.client.font;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public interface GlyphMetrics {
    public float getAdvance();

    default public float getAdvance(boolean bold) {
        return this.getAdvance() + (bold ? this.getBoldOffset() : 0.0f);
    }

    default public float getBoldOffset() {
        return 1.0f;
    }

    default public float getShadowOffset() {
        return 1.0f;
    }

    public static GlyphMetrics empty(float advance) {
        return () -> advance;
    }
}

