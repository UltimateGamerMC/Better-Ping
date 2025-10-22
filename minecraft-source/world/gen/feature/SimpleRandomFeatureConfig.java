/*
 * External method calls:
 *   Lnet/minecraft/registry/entry/RegistryEntryList;stream()Ljava/util/stream/Stream;
 *   Lnet/minecraft/util/dynamic/Codecs;nonEmptyEntryList(Lcom/mojang/serialization/Codec;)Lcom/mojang/serialization/Codec;
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.stream.Stream;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;

public class SimpleRandomFeatureConfig
implements FeatureConfig {
    public static final Codec<SimpleRandomFeatureConfig> CODEC = ((MapCodec)Codecs.nonEmptyEntryList(PlacedFeature.LIST_CODEC).fieldOf("features")).xmap(SimpleRandomFeatureConfig::new, config -> config.features).codec();
    public final RegistryEntryList<PlacedFeature> features;

    public SimpleRandomFeatureConfig(RegistryEntryList<PlacedFeature> features) {
        this.features = features;
    }

    @Override
    public Stream<ConfiguredFeature<?, ?>> getDecoratedFeatures() {
        return this.features.stream().flatMap(feature -> ((PlacedFeature)feature.value()).getDecoratedFeatures());
    }
}

