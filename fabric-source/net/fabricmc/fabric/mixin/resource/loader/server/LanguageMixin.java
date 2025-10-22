package net.fabricmc.fabric.mixin.resource.loader.server;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.BiConsumer;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.util.Language;

import net.fabricmc.fabric.impl.resource.loader.ServerLanguageUtil;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

@Mixin(Language.class)
class LanguageMixin {
	@Shadow
	@Final
	private static Logger LOGGER;

	@Redirect(method = "create", at = @At(value = "INVOKE", target = "Ljava/util/Map;copyOf(Ljava/util/Map;)Ljava/util/Map;"))
	private static Map<String, String> create(Map<String, String> map) {
		for (Path path : ServerLanguageUtil.getModLanguageFiles()) {
			loadFromPath(path, map::put);
		}

		return ImmutableMap.copyOf(map);
	}

	@Redirect(method = "load(Ljava/util/function/BiConsumer;Ljava/lang/String;)V", at = @At(value = "INVOKE", target = "Ljava/lang/Class;getResourceAsStream(Ljava/lang/String;)Ljava/io/InputStream;"))
	private static InputStream readCorrectVanillaResource(Class instance, String path) throws IOException {
		ModContainer mod = FabricLoader.getInstance().getModContainer("minecraft").orElseThrow();
		Path langPath = mod.findPath(path).orElse(null);

		if (langPath == null) {
			throw new IOException("Could not read %s from minecraft ModContainer".formatted(path));
		} else {
			return Files.newInputStream(langPath);
		}
	}

	@Unique
	private static void loadFromPath(Path path, BiConsumer<String, String> entryConsumer) {
		try (InputStream stream = Files.newInputStream(path)) {
			LOGGER.debug("Loading translations from {}", path);
			load(stream, entryConsumer);
		} catch (JsonParseException | IOException e) {
			LOGGER.error("Couldn't read strings from {}", path, e);
		}
	}

	@Shadow
	public static void load(InputStream inputStream, BiConsumer<String, String> entryConsumer) {
	}
}
