/*
 * External method calls:
 *   Lnet/minecraft/util/StringIdentifiable;createCodec(Ljava/util/function/Supplier;)Lnet/minecraft/util/StringIdentifiable$EnumCodec;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/client/font/FontType;method_36876()[Lnet/minecraft/client/font/FontType;
 */
package net.minecraft.client.font;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.BitmapFont;
import net.minecraft.client.font.FontLoader;
import net.minecraft.client.font.ReferenceFont;
import net.minecraft.client.font.SpaceFont;
import net.minecraft.client.font.TrueTypeFontLoader;
import net.minecraft.client.font.UnihexFont;
import net.minecraft.util.StringIdentifiable;

@Environment(value=EnvType.CLIENT)
public enum FontType implements StringIdentifiable
{
    BITMAP("bitmap", BitmapFont.Loader.CODEC),
    TTF("ttf", TrueTypeFontLoader.CODEC),
    SPACE("space", SpaceFont.Loader.CODEC),
    UNIHEX("unihex", UnihexFont.Loader.CODEC),
    REFERENCE("reference", ReferenceFont.CODEC);

    public static final Codec<FontType> CODEC;
    private final String id;
    private final MapCodec<? extends FontLoader> loaderCodec;

    private FontType(String id, MapCodec<? extends FontLoader> loaderCodec) {
        this.id = id;
        this.loaderCodec = loaderCodec;
    }

    @Override
    public String asString() {
        return this.id;
    }

    public MapCodec<? extends FontLoader> getLoaderCodec() {
        return this.loaderCodec;
    }

    static {
        CODEC = StringIdentifiable.createCodec(FontType::values);
    }
}

