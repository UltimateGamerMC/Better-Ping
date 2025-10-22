/*
 * External method calls:
 *   Lnet/minecraft/util/Identifier;ofVanilla(Ljava/lang/String;)Lnet/minecraft/util/Identifier;
 *   Lnet/minecraft/registry/Registry;register(Lnet/minecraft/registry/Registry;Lnet/minecraft/util/Identifier;Ljava/lang/Object;)Ljava/lang/Object;
 */
package net.minecraft.dialog;

import com.mojang.serialization.MapCodec;
import net.minecraft.dialog.input.BooleanInputControl;
import net.minecraft.dialog.input.InputControl;
import net.minecraft.dialog.input.NumberRangeInputControl;
import net.minecraft.dialog.input.SingleOptionInputControl;
import net.minecraft.dialog.input.TextInputControl;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class InputControlTypes {
    public static MapCodec<? extends InputControl> registerAndGetDefault(Registry<MapCodec<? extends InputControl>> registry) {
        Registry.register(registry, Identifier.ofVanilla("boolean"), BooleanInputControl.CODEC);
        Registry.register(registry, Identifier.ofVanilla("number_range"), NumberRangeInputControl.CODEC);
        Registry.register(registry, Identifier.ofVanilla("single_option"), SingleOptionInputControl.CODEC);
        return Registry.register(registry, Identifier.ofVanilla("text"), TextInputControl.CODEC);
    }
}

