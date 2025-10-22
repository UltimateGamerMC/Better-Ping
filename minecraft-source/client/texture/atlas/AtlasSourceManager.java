/*
 * External method calls:
 *   Lnet/minecraft/util/Identifier;ofVanilla(Ljava/lang/String;)Lnet/minecraft/util/Identifier;
 */
package net.minecraft.client.texture.atlas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.atlas.AtlasSource;
import net.minecraft.client.texture.atlas.DirectoryAtlasSource;
import net.minecraft.client.texture.atlas.FilterAtlasSource;
import net.minecraft.client.texture.atlas.PalettedPermutationsAtlasSource;
import net.minecraft.client.texture.atlas.SingleAtlasSource;
import net.minecraft.client.texture.atlas.UnstitchAtlasSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

@Environment(value=EnvType.CLIENT)
public class AtlasSourceManager {
    private static final Codecs.IdMapper<Identifier, MapCodec<? extends AtlasSource>> ID_MAPPER = new Codecs.IdMapper();
    public static final Codec<AtlasSource> TYPE_CODEC = ID_MAPPER.getCodec(Identifier.CODEC).dispatch(AtlasSource::getCodec, codec -> codec);
    public static final Codec<List<AtlasSource>> LIST_CODEC = ((MapCodec)TYPE_CODEC.listOf().fieldOf("sources")).codec();

    public static void bootstrap() {
        ID_MAPPER.put(Identifier.ofVanilla("single"), SingleAtlasSource.CODEC);
        ID_MAPPER.put(Identifier.ofVanilla("directory"), DirectoryAtlasSource.CODEC);
        ID_MAPPER.put(Identifier.ofVanilla("filter"), FilterAtlasSource.CODEC);
        ID_MAPPER.put(Identifier.ofVanilla("unstitch"), UnstitchAtlasSource.CODEC);
        ID_MAPPER.put(Identifier.ofVanilla("paletted_permutations"), PalettedPermutationsAtlasSource.CODEC);
    }
}

