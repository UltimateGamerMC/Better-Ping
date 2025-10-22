/*
 * External method calls:
 *   Lnet/minecraft/text/TextCodecs;dispatchingCodec(Lnet/minecraft/util/dynamic/Codecs$IdMapper;Ljava/util/function/Function;Ljava/lang/String;)Lcom/mojang/serialization/MapCodec;
 */
package net.minecraft.text.object;

import com.mojang.serialization.MapCodec;
import net.minecraft.text.TextCodecs;
import net.minecraft.text.object.AtlasTextObjectContents;
import net.minecraft.text.object.PlayerTextObjectContents;
import net.minecraft.text.object.TextObjectContents;
import net.minecraft.util.dynamic.Codecs;

public class TextObjectContentTypes {
    private static final Codecs.IdMapper<String, MapCodec<? extends TextObjectContents>> ID_MAPPER = new Codecs.IdMapper();
    public static final MapCodec<TextObjectContents> CODEC = TextCodecs.dispatchingCodec(ID_MAPPER, TextObjectContents::getCodec, "object");

    static {
        ID_MAPPER.put("atlas", AtlasTextObjectContents.CODEC);
        ID_MAPPER.put("player", PlayerTextObjectContents.CODEC);
    }
}

