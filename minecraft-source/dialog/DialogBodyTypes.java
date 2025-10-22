/*
 * External method calls:
 *   Lnet/minecraft/util/Identifier;ofVanilla(Ljava/lang/String;)Lnet/minecraft/util/Identifier;
 *   Lnet/minecraft/registry/Registry;register(Lnet/minecraft/registry/Registry;Lnet/minecraft/util/Identifier;Ljava/lang/Object;)Ljava/lang/Object;
 */
package net.minecraft.dialog;

import com.mojang.serialization.MapCodec;
import net.minecraft.dialog.body.DialogBody;
import net.minecraft.dialog.body.ItemDialogBody;
import net.minecraft.dialog.body.PlainMessageDialogBody;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DialogBodyTypes {
    public static MapCodec<? extends DialogBody> registerAndGetDefault(Registry<MapCodec<? extends DialogBody>> registry) {
        Registry.register(registry, Identifier.ofVanilla("item"), ItemDialogBody.CODEC);
        return Registry.register(registry, Identifier.ofVanilla("plain_message"), PlainMessageDialogBody.CODEC);
    }
}

