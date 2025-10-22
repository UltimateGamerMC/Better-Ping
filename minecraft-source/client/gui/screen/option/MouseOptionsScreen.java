/*
 * External method calls:
 *   Lnet/minecraft/client/gui/widget/OptionListWidget;addAll([Lnet/minecraft/client/option/SimpleOption;)V
 *   Lnet/minecraft/text/Text;translatable(Ljava/lang/String;)Lnet/minecraft/text/MutableText;
 */
package net.minecraft.client.gui.screen.option;

import java.util.Arrays;
import java.util.stream.Stream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

@Environment(value=EnvType.CLIENT)
public class MouseOptionsScreen
extends GameOptionsScreen {
    private static final Text TITLE = Text.translatable("options.mouse_settings.title");

    private static SimpleOption<?>[] getOptions(GameOptions gameOptions) {
        return new SimpleOption[]{gameOptions.getMouseSensitivity(), gameOptions.getTouchscreen(), gameOptions.getMouseWheelSensitivity(), gameOptions.getDiscreteMouseScroll(), gameOptions.getInvertMouseX(), gameOptions.getInvertMouseY(), gameOptions.getAllowCursorChanges()};
    }

    public MouseOptionsScreen(Screen parent, GameOptions gameOptions) {
        super(parent, gameOptions, TITLE);
    }

    @Override
    protected void addOptions() {
        if (InputUtil.isRawMouseMotionSupported()) {
            this.body.addAll((SimpleOption[])Stream.concat(Arrays.stream(MouseOptionsScreen.getOptions(this.gameOptions)), Stream.of(this.gameOptions.getRawMouseInput())).toArray(SimpleOption[]::new));
        } else {
            this.body.addAll(MouseOptionsScreen.getOptions(this.gameOptions));
        }
    }
}

