package net.fabricmc.fabric.mixin.resource.v1;

import java.util.List;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.resource.SimpleResourceReload;

import net.fabricmc.fabric.impl.resource.v1.FabricLifecycledResourceManager;
import net.fabricmc.fabric.impl.resource.v1.ResourceLoaderImpl;

@Mixin(SimpleResourceReload.class)
public class SimpleResourceReloadMixin {
	@ModifyArg(
			method = "start(Lnet/minecraft/resource/ResourceManager;Ljava/util/List;Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Z)Lnet/minecraft/resource/ResourceReload;",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/resource/SimpleResourceReload;create(Lnet/minecraft/resource/ResourceManager;Ljava/util/List;Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;)Lnet/minecraft/resource/ResourceReload;"
			)
	)
	private static List<ResourceReloader> sortSimple(List<ResourceReloader> reloaders, @Local(argsOnly = true) ResourceManager resourceManager) {
		if (resourceManager instanceof FabricLifecycledResourceManager flrm) {
			return ResourceLoaderImpl.sort(flrm.fabric$getResourceType(), reloaders);
		}

		return reloaders;
	}

	@ModifyArg(
			method = "start(Lnet/minecraft/resource/ResourceManager;Ljava/util/List;Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Z)Lnet/minecraft/resource/ResourceReload;",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/resource/ProfiledResourceReload;start(Lnet/minecraft/resource/ResourceManager;Ljava/util/List;Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;)Lnet/minecraft/resource/ResourceReload;"
			)
	)
	private static List<ResourceReloader> sortProfiled(List<ResourceReloader> reloaders, @Local(argsOnly = true) ResourceManager resourceManager) {
		if (resourceManager instanceof FabricLifecycledResourceManager flrm) {
			return ResourceLoaderImpl.sort(flrm.fabric$getResourceType(), reloaders);
		}

		return reloaders;
	}
}
