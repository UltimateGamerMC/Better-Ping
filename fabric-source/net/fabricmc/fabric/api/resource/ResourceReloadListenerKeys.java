package net.fabricmc.fabric.api.resource;

import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.resource.v1.reloader.ResourceReloaderKeys;

/**
 * This class contains default keys for various Minecraft resource reload listeners.
 *
 * @see IdentifiableResourceReloadListener
 * @deprecated Use {@link net.fabricmc.fabric.api.resource.v1.reloader.ResourceReloaderKeys} instead.
 */
@Deprecated
public final class ResourceReloadListenerKeys {
	// client
	public static final Identifier SOUNDS = ResourceReloaderKeys.Client.SOUNDS;
	public static final Identifier FONTS = ResourceReloaderKeys.Client.FONTS;
	public static final Identifier MODELS = ResourceReloaderKeys.Client.MODELS;
	public static final Identifier LANGUAGES = ResourceReloaderKeys.Client.LANGUAGES;
	public static final Identifier TEXTURES = ResourceReloaderKeys.Client.TEXTURES;

	// server
	public static final Identifier RECIPES = ResourceReloaderKeys.Server.RECIPES;
	public static final Identifier ADVANCEMENTS = ResourceReloaderKeys.Server.ADVANCEMENTS;
	public static final Identifier FUNCTIONS = ResourceReloaderKeys.Server.FUNCTIONS;

	private ResourceReloadListenerKeys() { }
}
