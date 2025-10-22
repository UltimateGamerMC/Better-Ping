package net.minecraft.world.gen.feature;

import java.util.stream.Stream;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public interface FeatureConfig {
    public static final DefaultFeatureConfig DEFAULT = DefaultFeatureConfig.INSTANCE;

    default public Stream<ConfiguredFeature<?, ?>> getDecoratedFeatures() {
        return Stream.empty();
    }
}

