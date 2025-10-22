package net.minecraft.client.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(value=EnvType.CLIENT)
public record ButtonTextures(Identifier enabled, Identifier disabled, Identifier enabledFocused, Identifier disabledFocused) {
    public ButtonTextures(Identifier texture) {
        this(texture, texture, texture, texture);
    }

    public ButtonTextures(Identifier unfocused, Identifier focused) {
        this(unfocused, unfocused, focused, focused);
    }

    public ButtonTextures(Identifier enabled, Identifier disabled, Identifier focused) {
        this(enabled, disabled, focused, disabled);
    }

    public Identifier get(boolean enabled, boolean focused) {
        if (enabled) {
            return focused ? this.enabledFocused : this.enabled;
        }
        return focused ? this.disabledFocused : this.disabled;
    }
}

