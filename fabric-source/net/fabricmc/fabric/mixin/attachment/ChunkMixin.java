package net.fabricmc.fabric.mixin.attachment;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;

import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.impl.attachment.AttachmentEntrypoint;
import net.fabricmc.fabric.impl.attachment.AttachmentTargetImpl;
import net.fabricmc.fabric.impl.attachment.sync.AttachmentTargetInfo;

@Mixin(Chunk.class)
abstract class ChunkMixin implements AttachmentTargetImpl {
	@Shadow
	@Final
	protected ChunkPos pos;

	@Shadow
	public abstract ChunkStatus getStatus();

	@Shadow
	public abstract ChunkPos getPos();

	@Shadow
	public abstract void markNeedsSaving();

	@Override
	public AttachmentTargetInfo<?> fabric_getSyncTargetInfo() {
		return new AttachmentTargetInfo.ChunkTarget(this.pos);
	}

	@Override
	public void fabric_markChanged(AttachmentType<?> type) {
		markNeedsSaving();

		if (type.isPersistent() && this.getStatus().equals(ChunkStatus.EMPTY)) {
			AttachmentEntrypoint.LOGGER.warn(
					"Attaching persistent attachment {} to chunk {} with chunk status EMPTY. Attachment might be discarded.",
					type.identifier(),
					this.getPos()
			);
		}
	}

	@Override
	public boolean fabric_shouldTryToSync() {
		// ProtoChunk or EmptyChunk
		return false;
	}

	@Override
	public DynamicRegistryManager fabric_getDynamicRegistryManager() {
		// Should never happen as this is only used for sync
		throw new UnsupportedOperationException("Chunk does not have a DynamicRegistryManager.");
	}
}
