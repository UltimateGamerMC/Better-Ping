package net.fabricmc.fabric.mixin.attachment;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.impl.attachment.AttachmentPersistentState;
import net.fabricmc.fabric.impl.attachment.AttachmentTargetImpl;
import net.fabricmc.fabric.impl.attachment.AttachmentTypeImpl;
import net.fabricmc.fabric.impl.attachment.sync.AttachmentSync;
import net.fabricmc.fabric.impl.attachment.sync.AttachmentTargetInfo;
import net.fabricmc.fabric.impl.attachment.sync.s2c.AttachmentSyncPayloadS2C;

@Mixin(ServerWorld.class)
abstract class ServerWorldMixin extends World implements AttachmentTargetImpl {
	@Shadow
	@Final
	private MinecraftServer server;

	protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
		super(
				properties,
				registryRef,
				registryManager,
				dimensionEntry,
				isClient,
				debugWorld,
				seed,
				maxChainedNeighborUpdates
		);
	}

	@Inject(at = @At("TAIL"), method = "<init>")
	private void createAttachmentsPersistentState(CallbackInfo ci) {
		// Force persistent state creation
		ServerWorld world = (ServerWorld) (Object) this;
		var type = new PersistentStateType<>(
				AttachmentPersistentState.ID,
				() -> new AttachmentPersistentState(world),
				AttachmentPersistentState.codec(world),
				null // Object builder API 12.1.0 and later makes this a no-op
		);
		world.getPersistentStateManager().getOrCreate(type);
	}

	@Override
	public void fabric_syncChange(AttachmentType<?> type, AttachmentSyncPayloadS2C payload) {
		if ((Object) this instanceof ServerWorld serverWorld) {
			PlayerLookup.world(serverWorld)
					.forEach(player -> {
						if (((AttachmentTypeImpl<?>) type).syncPredicate().test(this, player)) {
							AttachmentSync.trySync(payload, player);
						}
					});
		}
	}

	@Override
	public AttachmentTargetInfo<?> fabric_getSyncTargetInfo() {
		return AttachmentTargetInfo.WorldTarget.INSTANCE;
	}

	@Override
	public DynamicRegistryManager fabric_getDynamicRegistryManager() {
		return getRegistryManager();
	}
}
