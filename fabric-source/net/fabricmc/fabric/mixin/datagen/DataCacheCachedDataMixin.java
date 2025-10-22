package net.fabricmc.fabric.mixin.datagen;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;

import com.google.common.collect.ImmutableSet;
import com.google.common.hash.HashCode;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.data.DataCache$CachedData")
public abstract class DataCacheCachedDataMixin {
	@ModifyExpressionValue(method = "write", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap;entrySet()Lcom/google/common/collect/ImmutableSet;"))
	private ImmutableSet<Map.Entry<Path, HashCode>> sortPaths(ImmutableSet<Map.Entry<Path, HashCode>> original) {
		return original.stream()
				.sorted(Map.Entry.comparingByKey(Comparator.comparing(k -> normalizePath(k.toString()))))
				.collect(ImmutableSet.toImmutableSet());
	}

	@ModifyExpressionValue(method = "write", at = @At(value = "INVOKE", target = "Ljava/nio/file/Path;toString()Ljava/lang/String;"))
	private String pathToString(String original) {
		return normalizePath(original);
	}

	@Unique
	private static String normalizePath(String path) {
		return path.replace('\\', '/');
	}
}
