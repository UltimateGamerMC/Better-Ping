package net.fabricmc.fabric.mixin.biome.modification;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.MinecraftServer;

import net.fabricmc.fabric.impl.biome.modification.BiomeModificationImpl;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
	@Shadow
	public abstract DynamicRegistryManager.Immutable getRegistryManager();

	@Inject(method = "<init>", at = @At(value = "RETURN"))
	private void finalizeWorldGen(CallbackInfo ci) {
		BiomeModificationImpl.INSTANCE.finalizeWorldGen(getRegistryManager());
	}
}
